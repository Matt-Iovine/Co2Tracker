package mattia.archeometra.co2tracker.repository;

import mattia.archeometra.co2tracker.entity.City;
import mattia.archeometra.co2tracker.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findDistrictByNameAndCity(String districtName, City city);
}
