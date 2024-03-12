package skaba.hospital.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import skaba.hospital.store.entities.PatientEntity;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseDTO {

    @NonNull
    Long diseaseId;

    @NonNull
    Long patientId;

    @NonNull
    String name;

    @NonNull
    String description;

    @NonNull
    @JsonProperty("start_at")
    Instant startAt;

    @NonNull
    @JsonProperty("end_at")
    Instant endAt;
}
