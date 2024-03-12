package skaba.hospital.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import skaba.hospital.store.entities.DiseaseEntity;

import java.util.stream.Stream;

public interface DiseaseRepository extends JpaRepository<DiseaseEntity, Long> {

    Stream<DiseaseEntity> streamAllBy();
}
