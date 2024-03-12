package skaba.hospital.store.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "disease")
public class DiseaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "disease_id")
    Long diseaseId;

    String name;

    String description;

    @ManyToOne
    @JsonBackReference
    PatientEntity patient;

    @Builder.Default
    Instant startAt = Instant.now();

    @Builder.Default
    Instant endAt = Instant.now();

}
