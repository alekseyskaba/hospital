package skaba.hospital.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "patient_id")
    Long patientId;

    String phoneNumber;

    String firstName;

    String secondName;

    String lastName;

    String birthDate;

    @Builder.Default
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<DiseaseEntity> diseases = new ArrayList<>();
}
