package mattia.archeometra.co2tracker.dto;

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

    private Double co2Level;
    private String districtName;
    private LocalDateTime timestamp;
}
