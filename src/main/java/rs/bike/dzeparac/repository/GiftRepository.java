package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.bike.dzeparac.model.Gift;

import java.time.LocalDate;
import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {

    List<Gift> findByChildIdAndDateBetween(Long childId, LocalDate start, LocalDate end);
}