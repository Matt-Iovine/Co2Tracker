package mattia.archeometra.co2tracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private Double co2Level;

    @Schema(description = "Name of the district", example = "Eixample")
    private String districtName;

    @Schema(hidden = true)
    private LocalDateTime timestamp;
}
