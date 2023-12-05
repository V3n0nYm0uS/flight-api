package fr.unilasalle.flight.api.repositories;

import fr.unilasalle.flight.api.beans.Flight;
import fr.unilasalle.flight.api.beans.Reservation;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class ReservationRepository implements PanacheRepositoryBase<Reservation, Long> {
    public List<Reservation> findByFlightId(Long flightIdParameter) { return find("flight_id", flightIdParameter).list();}
}
