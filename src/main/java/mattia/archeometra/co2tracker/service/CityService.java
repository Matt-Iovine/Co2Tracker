package mattia.archeometra.co2tracker.service;

import lombok.extern.slf4j.Slf4j;
import mattia.archeometra.co2tracker.entity.City;
import mattia.archeometra.co2tracker.exception.CityNotFoundException;
import mattia.archeometra.co2tracker.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;

    public City findCityByName(String cityName) throws CityNotFoundException {

        log.info("Trying to retrieve city by name: {}", cityName);

        //Controllo con isEmpty() che l'Optional contenga la città che stavo cercando altrimenti lancio Eccezione
        Optional<City> cityOpt = cityRepository.findCityByName(cityName);
        if (cityOpt.isEmpty()) {
            log.error("City with name {} not found ", cityName);
            throw new CityNotFoundException("City name not found");
        }

        log.info("City found");

        return cityOpt.get();
    }
}
