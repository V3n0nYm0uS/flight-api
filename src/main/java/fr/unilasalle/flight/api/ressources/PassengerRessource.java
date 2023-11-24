package fr.unilasalle.flight.api.ressources;


import fr.unilasalle.flight.api.beans.Passenger;
import fr.unilasalle.flight.api.repositories.PassengerRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
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
            return Response.status(400).entity(
                    new GenericRessource.ErrorWrapper(violations)).build();

        }

        try{
            repository.persistAndFlush(passenger);
            return Response.ok().status(201).build();
        } catch (PersistenceException e){
            return Response.serverError().entity(
                    new ErrorWrapper("Error while creating the passenger"))
                    .build();
        }
    }
}
