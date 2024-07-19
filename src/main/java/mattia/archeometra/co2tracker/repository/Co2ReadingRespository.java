package mattia.archeometra.co2tracker.repository;

import mattia.archeometra.co2tracker.entity.Co2Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Co2ReadingRespository extends JpaRepository<Co2Reading, Long> {
}
