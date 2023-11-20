package fr.unilasalle.flight.api.ressources;


import fr.unilasalle.flight.api.repositories.FlightRepository;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/Flight")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlightRessource {

    @Inject
    FlightRepository repository;

    @Inject
    Validator validator;
}
