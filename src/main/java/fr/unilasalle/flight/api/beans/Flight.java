package fr.unilasalle.flight.api.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @NotBlank(message="destination must be set")
    @Column(nullable=false)
    private String destination;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message="departure_date must be set")
    @Column(nullable=false)
    private LocalDate departure_date;

    @NotNull(message="departure_time must be set")
    @Column(nullable=false)
    private LocalTime departure_time;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message="arrival_date must be set")
    @Column(nullable = false)
    private LocalDate arrival_date;

    @NotNull(message="arrival_time must be set")
    @Column(nullable=false)
    private LocalTime arrival_time;

    @ManyToOne
    @JoinColumn(name="plane_id", nullable=false) // Spécifier la colonne de clé étrangère
    private Plane plane;

}