package fr.unilasalle.flight.api;


import fr.unilasalle.flight.api.beans.Plane;
import fr.unilasalle.flight.api.repositories.PlaneRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;


import java.util.List;
import java.util.ArrayList;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlanesTest {
    @Inject
    PlaneRepository repository;

    @Test
    @Order(1)
    public void testGetPlanes(){
        //Appel à l'API pour récupérer les liste des avions
        var planes = repository.listAll();
        List<Integer> ids = new ArrayList<>();
        List<String> operators = new ArrayList<>();
        List<String> models = new ArrayList<>();
        List<String> immatriculations = new ArrayList<>();
        List<Integer> capacities = new ArrayList<>();
        for(Plane plane:planes){
            ids.add(plane.getId().intValue());
            operators.add(plane.getOperator());
            models.add(plane.getModel());
            immatriculations.add(plane.getImmatriculation());
            capacities.add(plane.getCapacity());
        }

        given()
                .when().get("/Plane")
                .then()
                .statusCode(200)
                .body("id", containsInAnyOrder(ids.toArray()))
                .body("operator", containsInAnyOrder(operators.toArray()))
                .body("model", containsInAnyOrder(models.toArray()))
                .body("immatriculation", containsInAnyOrder(immatriculations.toArray()))
                .body("capacity", containsInAnyOrder(capacities.toArray()));
    }

    @Test
    @Order(2)
    public void testGetPlaneById(){
        Plane plane = repository.findById(1L);
        given()
                .contentType("application/json")
                .pathParams("plane_id", plane.getId())
                .when().get("/Plane/{plane_id}")
                .then()
                .statusCode(200)
                .body("id", is(plane.getId().intValue()))
                .body("operator", is(plane.getOperator()))
                .body("model", is(plane.getModel()))
                .body("immatriculation", is(plane.getImmatriculation()))
                .body("capacity", is(plane.getCapacity()));
    }
}
