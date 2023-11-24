package fr.unilasalle.flight.api.beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="passengers")

@Getter
@Setter
@NoArgsConstructor
public class Passenger extends PanacheEntityBase{
    @Id
    @SequenceGenerator(name="passengers_sequence_in_java_code",
        sequenceName="passengers_sequence_in_database", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="passengers_sequence_in_database")
    private Long id;

    @NotBlank(message="surname must be set")
    @Column(nullable = false)
    private String surname;

    @NotBlank(message = "firstname must be set")
    @Column(nullable = false)
    private String firstname;

    @NotBlank(message = "email must be set")
    @Column(nullable = false)
    private String email_address;
}
