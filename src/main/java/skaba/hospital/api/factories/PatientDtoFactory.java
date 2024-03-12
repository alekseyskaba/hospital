package skaba.hospital.api.factories;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import skaba.hospital.api.dto.PatientDTO;
import skaba.hospital.store.entities.PatientEntity;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientDtoFactory {

    DiseaseDtoFactory diseaseDtoFactory;

    public PatientDTO makePatientDto(PatientEntity entity){

        return PatientDTO.builder()
                .patientId(entity.getPatientId())
                .phoneNumber(entity.getPhoneNumber())
                .birthDate(entity.getBirthDate())
                .firstName(entity.getFirstName())
                .secondName(entity.getSecondName())
                .lastName(entity.getLastName())
                .diseases(
                        entity
                                .getDiseases()
                                .stream()
                                .map(diseaseDtoFactory::makeDiseaseDto)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
