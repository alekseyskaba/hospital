package skaba.hospital.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    @NonNull
    Long patientId;

    @NonNull
    String phoneNumber;

    @NonNull
    String firstName;

    @NonNull
    String secondName;

    @NonNull
    String lastName;

    @NonNull
    String birthDate;

    @NonNull
    List<DiseaseDto> diseases;

}
