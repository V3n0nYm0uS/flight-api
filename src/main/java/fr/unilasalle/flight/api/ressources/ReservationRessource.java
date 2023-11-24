package fr.unilasalle.flight.api.ressources;


import fr.unilasalle.flight.api.beans.Reservation;
import fr.unilasalle.flight.api.repositories.ReservationRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
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
            return Response.ok().status(201).build();
        } catch (PersistenceException e){
            return Response.serverError().entity(
                    new ErrorWrapper("Error while creating the reservation"))
                    .build();
        }
    }
}
