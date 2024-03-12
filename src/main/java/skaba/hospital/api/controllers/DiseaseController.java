package skaba.hospital.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import skaba.hospital.api.controllers.helpers.ControllersHelper;
import skaba.hospital.api.dto.DiseaseDto;
import skaba.hospital.api.factories.DiseaseDtoFactory;
import skaba.hospital.store.entities.DiseaseEntity;
import skaba.hospital.store.entities.PatientEntity;
import skaba.hospital.store.repositories.DiseaseRepository;
import skaba.hospital.store.repositories.PatientRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@Transactional
public class DiseaseController {

    DiseaseRepository diseaseRepository;
    PatientRepository patientRepository;

    DiseaseDtoFactory diseaseDtoFactory;

    ControllersHelper controllersHelper;

    public static final String GET_DISEASES = "/api/patients/{patient_id}/diseases";
    public static final String ADD_DISEASE = "/api/patients/{patient_id}/diseases";
    public static final String DELETE_DISEASE = "/api/diseases/{disease_id}";
    public static final String GET_ALL_DISEASES = "/api/diseases";

    @GetMapping(GET_DISEASES)
    public List<DiseaseDto> getDiseases(@PathVariable("patient_id") Long id){

        PatientEntity patient = controllersHelper.getPatientByIdOrThrowException(id);

        return patient
                .getDiseases()
                .stream()
                .map(diseaseDtoFactory::makeDiseaseDto)
                .collect(Collectors.toList());
    }

    @GetMapping(GET_ALL_DISEASES)
    public List<DiseaseDto> getAllDiseases(){
        return diseaseRepository.streamAllBy()
                .map(diseaseDtoFactory::makeDiseaseDto)
                .collect(Collectors.toList());
    }

    @PostMapping(ADD_DISEASE)
    public DiseaseDto addDisease(
            @PathVariable("patient_id") Long patientId,
            @RequestParam String name,
            @RequestParam String description
    ){

        PatientEntity patient = controllersHelper.getPatientByIdOrThrowException(patientId);

        DiseaseEntity diseaseEntity = DiseaseEntity.builder()
                .patient(patient)
                .description(description)
                .name(name)
                .build();

        diseaseRepository.saveAndFlush(diseaseEntity);

        List<DiseaseEntity> diseaseEntityList = patient
                .getDiseases();

        diseaseEntityList.add(diseaseEntity);

        patientRepository.saveAndFlush(
                controllersHelper.getPatientEntity(diseaseEntityList, patient)
        );

        return diseaseDtoFactory.makeDiseaseDto(diseaseEntity);
    }

    @DeleteMapping(DELETE_DISEASE)
    public DiseaseDto deleteDisease(
            @PathVariable("disease_id") Long id
    ){

        DiseaseEntity diseaseEntity = diseaseRepository.getById(id);

        PatientEntity patient = controllersHelper.getPatientByIdOrThrowException(diseaseEntity.getPatient().getPatientId());

        List<DiseaseEntity> diseaseEntityList = patient
                .getDiseases();

        diseaseEntityList.remove(diseaseEntity);

        patientRepository.saveAndFlush(
                controllersHelper.getPatientEntity(diseaseEntityList, patient)
        );

        diseaseRepository.deleteById(id);

        return diseaseDtoFactory.makeDiseaseDto(diseaseEntity);
    }
}
