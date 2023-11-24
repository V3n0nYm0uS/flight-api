package fr.unilasalle.flight.api.beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="reservations")

@Getter
@Setter
@NoArgsConstructor
public class Reservation extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name="reservations_sequence_in_java_code",
        sequenceName="reservations_sequence_in_database", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="reservations_sequence_in_database")
    private Long id;
}
