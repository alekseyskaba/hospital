package skaba.hospital.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import skaba.hospital.store.entities.PatientEntity;

import java.util.Optional;
import java.util.stream.Stream;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByFirstNameAndSecondNameAndLastName(
            String firstName,
            String secondName,
            String lastName
    );

    Stream<PatientEntity> streamAllBy();

    Optional<PatientEntity> findByPatientId(Long id);
}
