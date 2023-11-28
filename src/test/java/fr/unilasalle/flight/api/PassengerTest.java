package fr.unilasalle.flight.api;

import fr.unilasalle.flight.api.beans.Passenger;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PassengerTest {


    @Test
    public void testGetPassengers() {
        // Appel à l'API pour récupérer la liste des passagers
        given()
                .when().get("/Plane")
                .then()
                .statusCode(200);
        // Ajoutez des assertions supplémentaires en fonction de votre logique métier
    }

    @Test
    public void testCreatePassenger() {
        // Création d'un objet Passenger de test
        Passenger passenger = new Passenger();
        passenger.setFirstname("John");
        passenger.setSurname("Doe");
        passenger.setEmail_address("john.doe@example.com");

        // Appel à l'API pour créer le passager
        given()
                .contentType("application/json")
                .body(passenger)
                .when().post("/Passenger")
                .then()
                .statusCode(201);
    }

    @Test
    public void testGetPassengerById() {
        // Supposons que l'ID 1 existe dans la base de données
        Long existingPassengerId = 1L;

        // Appel à l'API pour récupérer le passager par ID
        given()
                .when().get("/Passenger/{passenger_id}", existingPassengerId)
                .then()
                .statusCode(200)
                .body("id", is(existingPassengerId.intValue()));
    }


    @Test
    public void testUpdatePassengerById() {
        // Supposons que l'ID 1 existe dans la base de données
        Long existingPassengerId = 1L;

        // Création d'un objet Passenger de test pour la mise à jour
        Passenger updatedPassenger = new Passenger();
        updatedPassenger.setFirstname("UpdatedFirstName");
        updatedPassenger.setSurname("updatedSurname");
        updatedPassenger.setEmail_address("updatedEmail");
        updatedPassenger.setId(1L);

        // Appel à l'API pour mettre à jour le passager par ID
        given()
                .contentType("application/json")
                .body(updatedPassenger)
                .when().put("/Passenger/{passenger_id}", existingPassengerId)
                .then()
                .statusCode(200)
                .body("id", is(existingPassengerId.intValue()))
                .body("firstname", is("UpdatedFirstName"))
                .body("surname", is("updatedSurname"))
                .body("email_address", is("updatedEmail"));

    }

    @Test
    public void testDeletePassenger() {
        // Appel à l'API pour supprimer le passager par ID
        given()
                .when().delete("/Passenger/1")
                .then()
                .statusCode(204);
    }
}
