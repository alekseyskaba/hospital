package skaba.hospital.api.factories;

import org.springframework.stereotype.Component;
import skaba.hospital.api.dto.DiseaseDto;
import skaba.hospital.store.entities.DiseaseEntity;

@Component
public class DiseaseDtoFactory {

    public DiseaseDto makeDiseaseDto(DiseaseEntity disease){

        return DiseaseDto.builder()
                .diseaseId(disease.getDiseaseId())
                .endAt(disease.getEndAt())
                .startAt(disease.getStartAt())
                .name(disease.getName())
                .description(disease.getDescription())
                .patientId(disease.getPatient().getPatientId())
                .build();
    }
}
