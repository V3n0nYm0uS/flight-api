package fr.unilasalle.flight.api.ressources;


import fr.unilasalle.flight.api.beans.Flight;
import fr.unilasalle.flight.api.repositories.FlightRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Flight")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class FlightRessource extends GenericRessource{

    @Inject
    FlightRepository repository;

    @Inject
    Validator validator;

    @GET
    public Response getFlights(){
        var list = repository.listAll();
        return getOr404(list);
    }

    @POST
    @Transactional
    public Response createFlight(Flight flight){
        var violations = validator.validate(flight);
        if(!violations.isEmpty()){
            return Response.status(400).entity(
                    new GenericRessource.ErrorWrapper(violations))
                    .build();
        }

        try{
            repository.persistAndFlush(flight);
            return Response.ok().status(201).build();
        } catch (PersistenceException e){
            return Response.serverError().entity(
                    new ErrorWrapper("Error while creating the flight"))
                    .build();
        }
    }
}