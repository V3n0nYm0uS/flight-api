package fr.unilasalle.flight.api.repositories;

import fr.unilasalle.flight.api.beans.Passenger;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class PassengerRepository implements PanacheRepositoryBase<Passenger, Long> {
    public List<Passenger> findByPassenger(Passenger passenger) { return find("email = ?1 and firstname = ?2 and surname = ?3", passenger.getEmail_address(),passenger.getFirstname(),passenger.getSurname()).list();
}}
