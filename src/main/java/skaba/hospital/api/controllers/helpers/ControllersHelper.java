package skaba.hospital.api.controllers.helpers;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import skaba.hospital.api.exceptions.BadRequestException;
import skaba.hospital.store.entities.DiseaseEntity;
import skaba.hospital.store.entities.PatientEntity;
import skaba.hospital.store.repositories.PatientRepository;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional
public class ControllersHelper {
    PatientRepository patientRepository;
    public PatientEntity getPatientByIdOrThrowException(Long id){
        return patientRepository
                .findByPatientId(id)
                .orElseThrow(() ->
                        new BadRequestException("Такого пациента нет")
                );
    }

    public PatientEntity getPatientEntity(List<DiseaseEntity> diseaseEntityList, PatientEntity patient){
        return PatientEntity.builder()
                .diseases(diseaseEntityList)
                .phoneNumber(patient.getPhoneNumber())
                .secondName(patient.getSecondName())
                .firstName(patient.getFirstName())
                .birthDate(patient.getBirthDate())
                .patientId(patient.getPatientId())
                .lastName(patient.getLastName())
                .build();
    }
}
