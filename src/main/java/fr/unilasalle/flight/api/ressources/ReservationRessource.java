package fr.unilasalle.flight.api.ressources;


import fr.unilasalle.flight.api.beans.Reservation;
import fr.unilasalle.flight.api.repositories.ReservationRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Reservation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationRessource extends GenericRessource{
    @Inject
    ReservationRepository repository;

    @Inject
    Validator validator;

    @GET
    public Response getReservations(){
        var list = repository.listAll();
        return getOr404(list);
    }

    @POST
    @Transactional
    public Response createReservation(Reservation reservation){
        var violations = validator.validate(reservation);

        if(!violations.isEmpty()){
            return Response.status(400).entity(
                    new GenericRessource.ErrorWrapper(violations)).build();
        }

        try{
            repository.persistAndFlush(reservation);
            return Response.ok(reservation).status(201).build();
        } catch (PersistenceException e){
            return Response.serverError().entity(
                    new ErrorWrapper("Error while creating the reservation"))
                    .build();
        }
    }

    @Path("/{reservation_id}")
    @GET
    public Response getReservationById(@PathParam("reservation_id") Long reservation_id){
        try{
            var reservation = repository.findById(reservation_id);
            return getOr404(reservation);
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

    @Path("/{reservation_id}")
    @DELETE
    @Transactional
    public Response deleteReservationById(@PathParam("reservation_id") Long reservation_id){
        try{
            var deleted = repository.deleteById(reservation_id);
            if(deleted){
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("/{reservation_id}")
    @PUT
    @Transactional
    public Response updateReservationById(@PathParam("reservation_id") Long reservation_id, @Valid Reservation request){
        var reservation = repository.findById(reservation_id);
        if (reservation == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Vérification des contraintes
        var violations = validator.validate(request);
        if (!violations.isEmpty()){
            return Response.status(400).entity(
                    new GenericRessource.ErrorWrapper(violations)).build();
        }
        // MAJ de l'objet
        reservation.setFlight(request.getFlight());
        reservation.setPassenger(request.getPassenger());
        return Response.ok(reservation).build();
    }
}
