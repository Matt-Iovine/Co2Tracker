package mattia.archeometra.co2tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mattia.archeometra.co2tracker.entity.Co2Reading;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DistrictDTO {

    private Long id;
    private String name;
    private List<Co2Reading> sensorReadings;
}
