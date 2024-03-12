package skaba.hospital.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import skaba.hospital.api.controllers.helpers.ControllersHelper;
import skaba.hospital.api.dto.AckDto;
import skaba.hospital.api.dto.PatientDto;
import skaba.hospital.api.exceptions.BadRequestException;
import skaba.hospital.api.factories.PatientDtoFactory;
import skaba.hospital.store.entities.PatientEntity;
import skaba.hospital.store.repositories.PatientRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@Transactional
public class PatientController {

    ControllersHelper controllersHelper;

    PatientRepository patientRepository;

    PatientDtoFactory patientDtoFactory;

    public static final String ADD_PATIENT = "/api/patients";
    public static final String GET_PATIENT = "/api/patients/{patient_id}";
    public static final String GET_PATIENTS = "/api/patients";
    public static final String DELETE_PATIENT = "/api/patients/{patient_id}";


    @PutMapping(ADD_PATIENT)
    public PatientDto addPatient(
            @RequestParam String firstName,
            @RequestParam String secondName,
            @RequestParam String lastName,
            @RequestParam String phoneNumber,
            @RequestParam String birthDate
    ){

        if(firstName.trim().isEmpty()||secondName.trim().isEmpty()||lastName.trim().isEmpty()){
            throw new BadRequestException("Имя, отчество или фамилия не могут быть пустыми.");
        }

        patientRepository
                .findByFirstNameAndSecondNameAndLastName(firstName, secondName, lastName)
                .ifPresent(patient -> {
                    throw new BadRequestException(String.format("Пациент %s %s %s уже существует.", firstName, secondName, lastName));
                });

        PatientEntity patient = patientRepository.saveAndFlush(
            PatientEntity.builder()
                    .firstName(firstName)
                    .secondName(secondName)
                    .lastName(lastName)
                    .phoneNumber(phoneNumber)
                    .birthDate(birthDate)
                    .build()
        );

        return patientDtoFactory.makePatientDto(patient);

    }

    @GetMapping(GET_PATIENT)
    public PatientDto getPatient(
            @PathVariable("patient_id") Long id
    ){

        PatientEntity patient = controllersHelper.getPatientByIdOrThrowException(id);

        return patientDtoFactory.makePatientDto(patient);
    }

    @DeleteMapping(DELETE_PATIENT)
    public AckDto deletePatient(@PathVariable("patient_id") Long id){

        controllersHelper.getPatientByIdOrThrowException(id);

        patientRepository.deleteById(id);

        return AckDto.makeDefault(true);
    }

    @GetMapping(GET_PATIENTS)
    public List<PatientDto> getAllPatients(){
        return patientRepository
                .streamAllBy()
                .map(patientDtoFactory::makePatientDto)
                .collect(Collectors.toList());
    }

}
