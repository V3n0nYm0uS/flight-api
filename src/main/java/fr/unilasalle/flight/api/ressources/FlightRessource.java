package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Flight;
import fr.unilasalle.flight.api.repositories.FlightRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

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
        return getOr404(repository.listAll());

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
            return Response.ok(flight).status(201).build();
        } catch (PersistenceException e){
            return Response.serverError().entity(
                    new ErrorWrapper("Error while creating the flight"))
                    .build();
        }
    }

    @Path("/{flight_id}")
    @GET
    public Response getFlightById(@PathParam("flight_id") Long flight_id){
        try{
            var flight = repository.findById(flight_id);
            return getOr404(flight);
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("/{flight_id}")
    @DELETE
    @Transactional
    public Response deleteFlightById(@PathParam("flight_id") Long flight_id){
        try{
            var deleted = repository.deleteById(flight_id);
            if (deleted){
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("/{flight_id}")
    @PUT
    @Transactional
    public Response updateFlightById(@PathParam("flight_id") Long flight_id, @Valid Flight request){
        var flight = repository.findById(flight_id);
        if(flight == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Verif
        var violations = validator.validate(request);
        if (!violations.isEmpty()){
            return Response.status(400).entity(
                    new GenericRessource.ErrorWrapper(violations)).build();
        }
        flight.setDeparture_date(request.getDeparture_date());
        flight.setDeparture_time(request.getDeparture_time());
        flight.setArrival_date(request.getArrival_date());
        flight.setArrival_time(request.getArrival_time());
        flight.setDestination(request.getDestination());
        flight.setNumber(request.getNumber());
        flight.setOrigin(request.getOrigin());
        flight.setPlane(request.getPlane());
        return Response.ok(flight).build();
    }
}