package fr.unilasalle.flight.api.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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
    @Column(nullable = false, unique = true)
    private String email_address;

    @OneToMany(mappedBy="passenger", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
    private List<Reservation> reservations;
}
