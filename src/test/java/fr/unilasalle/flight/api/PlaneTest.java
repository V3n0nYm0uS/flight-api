package fr.unilasalle.flight.api;

import fr.unilasalle.flight.api.beans.Plane;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PlaneTest {

    @Test
    public void testCreatePlane() {
        // Création d'un objet Plane de test
        Plane plane = new Plane();
        plane.setOperator("Volotea");
        plane.setId(201L);
        plane.setCapacity(20);
        plane.setModel("A380");
        plane.setImmatriculation("F-AZE");

        // Appel à l'API pour créer un avion
        given()
                .contentType("application/json")
                .body(plane)
                .when().post("/Plane")
                .then()
                .statusCode(201)
                .body("id", is(201L))
                .body("operator", is(plane.getOperator()))
                .body("model", is(plane.getModel()))
                .body("immatriculation", is(plane.getImmatriculation()))
                .body("capacity", is(plane.getCapacity()));
    }

    @Test
    public void testGetPlaneById() {
        // Supposons que l'ID 1 existe dans la base de données
        Long existingPassengerId = 1L;

        // Appel à l'API pour récupérer le passager par ID
        given()
                .when().get("/Plane/1")
                .then()
                .statusCode(200)
                .body("id", is(1L))
                .body("operator", is("AirbusIndustrie"))
                .body("model", is("A380"))
                .body("immatriculation", is("F-ABCD"))
                .body("capacity", is(10));
    }

    @Test
    public void testGetPlanes() {
        // Appel à l'API pour récupérer la liste des passagers
        given()
                .when().get("/Plane")
                .then()
                .statusCode(200);
        // Ajoutez des assertions supplémentaires en fonction de votre logique métier
    }

    @Test
    public void testUpdatePlaneById() {
        // Supposons que l'ID 1 existe dans la base de données
        Long existingPlaneId = 1L;

        // Création d'un objet Plane de test pour la mise à jour
        Plane updatedPlane = new Plane();
        updatedPlane.setId(1L);
        updatedPlane.setOperator("AirbusIndustrie");
        updatedPlane.setModel("A320");
        updatedPlane.setImmatriculation("FZA");
        updatedPlane.setCapacity(20);

        // Appel à l'API pour mettre à jour le passager par ID
        given()
                .contentType("application/json")
                .body(updatedPlane)
                .when().put("/Plane/1")
                .then()
                .statusCode(200)
                .body("id", is(updatedPlane.getId()))
                .body("operator", is(updatedPlane.getOperator()))
                .body("model", is(updatedPlane.getModel()))
                .body("immatriculation", is(updatedPlane.getImmatriculation()))
                .body("capacity", is(updatedPlane.getCapacity()));


    }

    @Test
    public void testDeletePlane() {
        // Appel à l'API pour supprimer le passager par ID
        given()
                .when().delete("/Plane/1")
                .then()
                .statusCode(204);
    }
}
