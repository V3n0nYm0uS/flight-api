package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Avion;
import fr.unilasalle.flight.api.repositories.AvionRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Avions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class AvionRessource extends GenericRessource{
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
    public Response createPlace(Avion avion){
        var violations = validator.validate(avion);

        if(!violations.isEmpty()){
            return Response.status(400).entity(
                    new ErrorWrapper(violations)).build();
        }

        try{
            repository.persisteAndFlush(avion);
            return Response.ok().status(201).build();
        } catch (PersistenceException e){
            return Response.serverError().entity(
                    new ErrorWrapper("Erreur lors de la création"))
                    .build();
        }
    }
}