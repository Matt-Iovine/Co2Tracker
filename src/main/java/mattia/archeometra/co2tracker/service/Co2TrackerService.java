package mattia.archeometra.co2tracker.service;

import lombok.extern.slf4j.Slf4j;
import mattia.archeometra.co2tracker.dto.Co2ReadingDTO;
import mattia.archeometra.co2tracker.entity.City;
import mattia.archeometra.co2tracker.entity.Co2Reading;
import mattia.archeometra.co2tracker.entity.District;
import mattia.archeometra.co2tracker.exception.CityNotFoundException;
import mattia.archeometra.co2tracker.exception.DistrictNameNotFoundException;
import mattia.archeometra.co2tracker.exception.TrackerInternalServerError;
import mattia.archeometra.co2tracker.mapper.Co2ReadingMapper;
import mattia.archeometra.co2tracker.repository.CityRepository;
import mattia.archeometra.co2tracker.repository.Co2ReadingRespository;
import mattia.archeometra.co2tracker.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class Co2TrackerService {

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    Co2ReadingRespository co2ReadingRespository;

    @Autowired
    Co2ReadingMapper co2ReadingMapper;

    @Autowired
    CityService cityService;

    @Autowired
    DistrictService districtService;

    public Co2ReadingDTO createReading(Co2ReadingDTO trackerDTO, String cityName) throws CityNotFoundException, DistrictNameNotFoundException, TrackerInternalServerError {


        City city = cityService.findCityByName(cityName);

        District district = districtService.findDistrictByNameAndCity(trackerDTO.getDistrictName(), city);

        Co2Reading newCo2Reading = createCo2Reading(trackerDTO.getCo2Level(), district);
        Co2Reading savedCo2Reading;

        log.info("Trying to save new reading for City: {}, District: {}", cityName, district.getName());
        try{

            savedCo2Reading = co2ReadingRespository.save(newCo2Reading);

            district.getSensorReadings().add(savedCo2Reading);
            districtRepository.save(district);

        }catch (IllegalArgumentException ex){
            throw new TrackerInternalServerError("Something went wrong while trying to persist the informations on the Database");
        }



        return co2ReadingMapper.co2ReadingToDTO(savedCo2Reading);

    }

    public List<Co2ReadingDTO> getReadings(String districtName, String cityName) throws CityNotFoundException, DistrictNameNotFoundException {

        City city = cityService.findCityByName(cityName);

        District district = districtService.findDistrictByNameAndCity(districtName, city);

        return co2ReadingMapper.co2ReadingListToDTO(district.getSensorReadings());

    }


    public Co2Reading createCo2Reading(Double co2Level, District district){

        Co2Reading newCo2Reading = new Co2Reading();

        newCo2Reading.setCo2Level(co2Level);
        newCo2Reading.setTimestamp(LocalDateTime.now());
        newCo2Reading.setDistrict(district);

        return newCo2Reading;
    }
}
