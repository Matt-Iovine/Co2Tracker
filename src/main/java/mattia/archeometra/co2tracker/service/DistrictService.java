package mattia.archeometra.co2tracker.service;

import lombok.extern.slf4j.Slf4j;
import mattia.archeometra.co2tracker.entity.City;
import mattia.archeometra.co2tracker.entity.District;
import mattia.archeometra.co2tracker.exception.DistrictNameNotFoundException;
import mattia.archeometra.co2tracker.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DistrictService {
    @Autowired
    DistrictRepository districtRepository;

    public District findDistrictByNameAndCity(String districtName, City city) throws DistrictNameNotFoundException {

        log.info("Trying to retrieve district {} for city {}" ,districtName, city.getName());

        //Controllo con isEmpty() che l'Optional contenga il distretto che stavo cercando altrimenti lancio Eccezione
        Optional<District> districtOpt = districtRepository.findDistrictByNameAndCity(districtName, city);

        if (districtOpt.isEmpty()) {
            log.error("District with name {} not found for city {}", city.getName(), districtName);
            throw new DistrictNameNotFoundException("District name doesn't exist!");
        }

        log.info("District found!");

        return districtOpt.get();

    }
}
