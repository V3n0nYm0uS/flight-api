package fr.unilasalle.flight.api.beans;

import lombok.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name="flights")

@Getter
@Setter
@NoArgsConstructor
public class Flight extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name="flight_sequence_in_java_code",
            sequenceName="planes_sequence_in_database", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="planes_sequence_in_database")
    private Long id;

    @NotBlank(message="Number must be set")
    @Column(nullable=false)
    private String number;

    @NotBlank(message="Origin must be set")
    @Column(nullable=false)
    private String origin;


}