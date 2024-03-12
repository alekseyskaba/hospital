package skaba.hospital.api.factories;

import org.springframework.stereotype.Component;
import skaba.hospital.api.dto.DiseaseDTO;
import skaba.hospital.store.entities.DiseaseEntity;

@Component
public class DiseaseDtoFactory {

    public DiseaseDTO makeDiseaseDto(DiseaseEntity disease){

        return DiseaseDTO.builder()
                .diseaseId(disease.getDiseaseId())
                .endAt(disease.getEndAt())
                .startAt(disease.getStartAt())
                .name(disease.getName())
                .description(disease.getDescription())
                .patientId(disease.getPatient().getPatientId())
                .build();
    }
}
