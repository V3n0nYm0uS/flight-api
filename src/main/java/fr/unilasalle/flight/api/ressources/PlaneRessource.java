package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Plane;
import fr.unilasalle.flight.api.repositories.PlaneRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Plane")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class PlaneRessource extends GenericRessource {
    @Inject
    PlaneRepository repository;

    @Inject
    Validator validator;


    @GET
    public Response getPlanes(){
        var list=repository.listAll();
        return getOr404(list);
    }

    @POST
    @Transactional
    public Response createPlane(Plane plane){
        var violations = validator.validate(plane);

        if(!violations.isEmpty()){
            return Response.status(400).entity(
                    new GenericRessource.ErrorWrapper(violations)).build();
        }

        try{
            repository.persistAndFlush(plane);
            return Response.ok().status(201).build();
        } catch (PersistenceException e){
            return Response.serverError().entity(
                    new ErrorWrapper("Error while creating the plane"))
                    .build();
        }
    }

    @Path("/{plane_id}")
    @GET
    public Response getPlaneById(@PathParam("plane_id") Long plane_id){
        try {
            var plane = repository.findById(plane_id);
            return getOr404(plane);
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("/{plane_id}")
    @DELETE
    @Transactional
    public Response deletePlaneById(@PathParam("plane_id") Long plane_id){
        try {
            var deleted = repository.deleteById(plane_id);
            if (deleted) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("/{plane_id}")
    @PUT
    @Transactional
    public Response updatePlaneById(@PathParam("plane_id") Long plane_id, @Valid Plane request){
        var plane = repository.findById(plane_id);
        if (plane == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Verification des contraintes
        var violations = validator.validate(request);
        if (!violations.isEmpty()){
            return Response.status(400).entity(
                    new GenericRessource.ErrorWrapper(violations)).build();
        }
        // MAJ de l'objet
        plane.setCapacity(request.getCapacity());
        plane.setImmatriculation(request.getImmatriculation());
        plane.setModel(request.getModel());
        plane.setOperator(request.getOperator());
        return Response.ok(plane).build();
    }
}