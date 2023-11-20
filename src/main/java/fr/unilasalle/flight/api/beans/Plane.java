package fr.unilasalle.flight.api.beans;
import lombok.*;

@Entity
@Table(name="planes")

@Getter
@Setter
@NoArgsConstructor
public class Plane extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name="planes_sequence_in_java_code",
            sequenceName="planes_sequence_in_database", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="planes_sequence_in_database")
    private Long id;

    @NotBlank(message="operator must be set")
    @Column(nullable=false)
    private String operator;

    @NotBlank(message="model must be set")
    @Column(nullable=false)
    private String model;

    @NotBlank(message="immatriculation must be set")
    @Size(max=6, message = "immatriculation must be 6 less lenght")
    @Column(nullable=false)
    private String immatriculation;

    @NotNull(message="capacity must be set")
    @Min(value="2", message="capacity has to be more than 2")
    @Column(nullable=false)
    private Integer capacity;
}