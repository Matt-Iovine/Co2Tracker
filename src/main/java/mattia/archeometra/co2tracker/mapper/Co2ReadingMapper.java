package mattia.archeometra.co2tracker.mapper;

import mattia.archeometra.co2tracker.dto.Co2ReadingDTO;
import mattia.archeometra.co2tracker.entity.Co2Reading;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Co2ReadingMapper {

    public Co2ReadingDTO co2ReadingToDTO(Co2Reading co2Reading){
        Co2ReadingDTO co2ReadingDTO = new Co2ReadingDTO();

        if(null == co2Reading){
            return co2ReadingDTO;
        }

        co2ReadingDTO.setCo2Level(co2Reading.getCo2Level());
        co2ReadingDTO.setTimestamp(co2Reading.getTimestamp());
        co2ReadingDTO.setDistrictName(co2Reading.getDistrict().getName());

        return co2ReadingDTO;
    }

    public List<Co2ReadingDTO> co2ReadingListToDTO(List<Co2Reading> co2ReadingList){

        if(co2ReadingList==null || co2ReadingList.isEmpty()){
            return Collections.emptyList();
        }

        List<Co2ReadingDTO> co2ReadingDTOList =new ArrayList<>();
        for (Co2Reading co2Reading : co2ReadingList){
            co2ReadingDTOList.add(co2ReadingToDTO(co2Reading));
        }

        return co2ReadingDTOList;
    }

}
