package mattia.archeometra.co2tracker.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mattia.archeometra.co2tracker.dto.Co2ReadingDTO;
import mattia.archeometra.co2tracker.entity.City;
import mattia.archeometra.co2tracker.entity.Co2Reading;
import mattia.archeometra.co2tracker.entity.District;
import mattia.archeometra.co2tracker.exception.CityNotFoundException;
import mattia.archeometra.co2tracker.exception.DistrictNameNotFoundException;
import mattia.archeometra.co2tracker.exception.TrackerInternalServerError;
import mattia.archeometra.co2tracker.mapper.Co2ReadingMapper;
import mattia.archeometra.co2tracker.repository.Co2ReadingRespository;
import mattia.archeometra.co2tracker.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    public Co2ReadingDTO createReading(Co2ReadingDTO trackerDTO, String cityName) throws CityNotFoundException, DistrictNameNotFoundException, TrackerInternalServerError {

        //Recupero la entity città dato il nome
        City city = cityService.findCityByName(cityName);

        //Recupero il distretto dato il nome e la città
        District district = districtService.findDistrictByNameAndCity(trackerDTO.getDistrictName(), city);

        //Creo l'oggetto Co2Reading dato il livello di co2 e il distretto relativo al reading
        Co2Reading newCo2Reading = createCo2Reading(trackerDTO.getCo2Level(), district);
        Co2Reading savedCo2Reading;

        log.info("Trying to save new reading for City: {}, District: {}", cityName, district.getName());

        try {
            /*
                Mi assicuro che i metodi save non restituiscano un valore nullo
                dopo il salvataggio, catchando l'IllegalArgumentException
            */

            //Salvo la nuova lettura di Co2
            savedCo2Reading = co2ReadingRespository.save(newCo2Reading);

            //Valorizzo la bidirezionalità della relazione aggiornando la lista di letture del distretto
            district.getSensorReadings().add(savedCo2Reading);
            districtRepository.save(district);

        } catch (IllegalArgumentException ex) {
            throw new TrackerInternalServerError("Something went wrong while trying to persist the informations on the Database");
        }

        //Trasformo la entity restituita dal DB in DTO
        return co2ReadingMapper.co2ReadingToDTO(savedCo2Reading);

    }

    public List<Co2ReadingDTO> getReadings(String districtName, String cityName) throws CityNotFoundException, DistrictNameNotFoundException {

        //Recupero la entity città dato il nome
        City city = cityService.findCityByName(cityName);

        //Recupero il distretto dato il nome e la città
        District district = districtService.findDistrictByNameAndCity(districtName, city);

        //Trasformo la lista di entity restituita da DB in una lista di DTO
        return co2ReadingMapper.co2ReadingListToDTO(district.getSensorReadings());

    }


    public Co2Reading createCo2Reading(Double co2Level, District district) {

        Co2Reading newCo2Reading = new Co2Reading();

        newCo2Reading.setCo2Level(co2Level);
        newCo2Reading.setTimestamp(LocalDateTime.now());
        newCo2Reading.setDistrict(district);

        return newCo2Reading;
    }
}
