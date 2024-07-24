package mattia.archeometra.co2tracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Co2ReadingDTO {

    @Schema(description = "CO2 level of the sensor", example = "100")
    @NotBlank(message = "Co2 level cannot be empty")
    @NotNull(message = "Co2 level cannot be null")
    private Double co2Level;

    @Schema(description = "Name of the district", example = "Eixample")
    @NotBlank(message = "District name cannot be empty")
    @NotNull(message = "District name cannot be null")
    private String districtName;

    @Schema(hidden = true)
    private LocalDateTime timestamp;

}
