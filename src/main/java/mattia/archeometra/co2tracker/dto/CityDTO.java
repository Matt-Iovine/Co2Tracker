package mattia.archeometra.co2tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityDTO {

    private Long idCity;
    private String name;
    private List<DistrictDTO> districts;
}
