package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Plane;
import fr.unilasalle.flight.api.repositories.PlaneRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Plane")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class PlaneRessource extends GenericRessource{
    @Inject
    AcionRepository repository;

    @Inject
    Validator validator;


    @GET
    public Response getPlanes(){
        var list=repository.listAll();
        return getOr404(list);
    }

    @Post
    @Transactional
    public Response createPlane(Plane plane){
        var violations = validator.validate(plane);

        if(!violations.isEmpty()){
            return Response.status(400).entity(
                    new ErrorWrapper(violations)).build();
        }

        try{
            repository.persisteAndFlush(plane);
            return Response.ok().status(201).build();
        } catch (PersistenceException e){
            return Response.serverError().entity(
                    new ErrorWrapper("Error while creating the plane"))
                    .build();
        }
    }
}