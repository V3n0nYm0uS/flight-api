package fr.unilasalle.flight.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unilasalle.flight.api.beans.Passenger;
import fr.unilasalle.flight.api.repositories.PassengerRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PassengerTest {
    @Inject
    PassengerRepository repository;

    @Test
    @Order(1)
    public void testGetPassengers() {
        // Appel à l'API pour récupérer la liste des passagers
        var passengers = repository.listAll();
        List<Integer> ids = new ArrayList<>();
        List<String> firstnames = new ArrayList<>();
        List<String> surnames = new ArrayList<>();
        List<String> email_addresses = new ArrayList<>();
        for(Passenger passenger:passengers){
            ids.add(passenger.getId().intValue());
            firstnames.add(passenger.getFirstname());
            surnames.add(passenger.getSurname());
            email_addresses.add(passenger.getEmail_address());
        }

        given()
                .when().get("/Passenger")
                .then()
                .statusCode(200)
        // Ajoutez des assertions supplémentaires en fonction de votre logique métier
                .body("id", containsInAnyOrder(ids.toArray()))
                .body("firstname", containsInAnyOrder(firstnames.toArray()))
                .body("surname", containsInAnyOrder(surnames.toArray()))
                .body("email_address", containsInAnyOrder(email_addresses.toArray()));
    }

    @Test
    @Order(2)
    public void testGetPassengerById() {
        // Supposons que l'ID 1 existe dans la base de données
        Passenger passenger = repository.findById(1L);

        // Appel à l'API pour récupérer le passager par ID
        given()
                .contentType("application/json")
                .pathParam("passenger_id", passenger.getId())
                .when().get("/Passenger/{passenger_id}")
                .then()
                .statusCode(200)
                .body("id", is(passenger.getId().intValue()))
                .body("firstname", is(passenger.getFirstname()))
                .body("surname", is(passenger.getSurname()))
                .body("email_address", is(passenger.getEmail_address()));
    }

    @Test
    @Order(3)
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

        passenger.setId(101L);

        given()
                .contentType("application/json")
                .pathParam("passenger_id", passenger.getId())
                .when().get("/Passenger/{passenger_id}")
                .then()
                .statusCode(200)
                .body("id", is(passenger.getId().intValue()))
                .body("firstname", is(passenger.getFirstname()))
                .body("surname", is(passenger.getSurname()))
                .body("email_address", is(passenger.getEmail_address()));
    }


    @Test
    @Order(4)
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
                .body("firstname", is(updatedPassenger.getFirstname()))
                .body("surname", is(updatedPassenger.getSurname()))
                .body("email_address", is(updatedPassenger.getEmail_address()));

        given()
                .contentType("application/json")
                .pathParam("passenger_id", existingPassengerId)
                .when().get("/Passenger/{passenger_id}")
                .then()
                .statusCode(200)
                .body("id", is(existingPassengerId.intValue()))
                .body("firstname", is(updatedPassenger.getFirstname()))
                .body("surname", is(updatedPassenger.getSurname()))
                .body("email_address", is(updatedPassenger.getEmail_address()));

    }

    @Test
    @Order(6)
    public void testDeletePassenger() {
        // Appel à l'API pour supprimer le passager par ID
        given()
                .when().delete("/Passenger/51")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    public void testUniqueEmail(){
        List<Passenger> passengers = repository.listAll();

        Passenger passenger = passengers.get(0);
        passenger.setEmail_address(passengers.get(1).getEmail_address());

        given()
                .contentType("application/json")
                .body(passenger)
                .pathParam("passenger_id", passenger.getId().intValue())
                .when().put("/Passenger/{passenger_id}")
                .then()
                .statusCode(409);
    }
}
