package fr.unilasalle.flight.api.beans;
import lombok.*;

@Entity
@Table(name="planes")

@Getter
@Setter
@NoArgsConstructor
public class Avion extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name="planes_sequence_in_java_code",
            sequenceName="planes_sequence_in_database", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="planes_sequence_in_database")
    private Long id;

    @NotBlank(message="le fabricant ne dois pas être vide")
    @Column(nullable=false)
    private String operator;

    @NotBlank(message="le model ne dois pas être vide")
    @Column(nullable=false)
    private String model;

    @NotBlank(message="L'immatriculation ne doit pas être vide")
    @Size(max=6, message = "L'immatriculation doit faire au maxiumum 6 caractères")
    @Column(nullable=false)
    private String immatriculation;

    @NotNull(message="La capacité doit être fournie")
    @Min(value="2", message="La capacité de l'avion doit être supérieure à 2")
    @Column(nullable=false)
    private Integer capacity;
}