package fr.unilasalle.flight.api.ressources;


import fr.unilasalle.flight.api.beans.Passenger;
import fr.unilasalle.flight.api.repositories.PassengerRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/Passenger")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PassengerRessource extends GenericRessource{
    @Inject
    PassengerRepository repository;

    @Inject
    Validator validator;

    @GET
    public Response getPassengers(){
        var list = repository.listAll();
        return getOr404(list);
    }

    @POST
    @Transactional
    public Response createPassenger(Passenger passenger){
        var violations = validator.validate(passenger);
        if(!violations.isEmpty()){
            return Response.status(500).entity(
                    new GenericRessource.ErrorWrapper(violations)).build();
        }

        try{
            repository.persistAndFlush(passenger);
            return Response.ok(passenger).status(201).build();
        } catch (PersistenceException e){
            return Response.status(409).entity(e.getMessage()).build();
        }
    }

    @Path("/{passenger_id}")
    @GET
    public Response getPassengerById(@PathParam("passenger_id") Long passenger_id){
        try{
            var passenger = repository.findById(passenger_id);
            return getOr404(passenger);
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("/{passenger_id}")
    @DELETE
    @Transactional
    public Response deletePassengerById(@PathParam("passenger_id") Long passenger_id){
        try{
            var deleted = repository.deleteById(passenger_id);
            if (deleted) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("/{passenger_id}")
    @PUT
    @Transactional
    public Response updatePassengerById(@PathParam("passenger_id") Long passenger_id, @Valid Passenger request){
        var passenger = repository.findById(passenger_id);
        if (passenger == null){
            return Response.status(404).build();
        }
        // Vérification des contraintes
        var violations = validator.validate(request);
        if (!violations.isEmpty()){
            return Response.status(500).entity(violations).build();
        }
        // MAJ DE L'objet
        try {
            passenger.setFirstname(request.getFirstname());
            passenger.setSurname(request.getSurname());
            passenger.setEmail_address(request.getEmail_address());
            passenger.persistAndFlush();
            return Response.ok(passenger).build();
        } catch (Exception e) {
            return Response.status(409).entity(e.getMessage()).build();
        }
    }
}
