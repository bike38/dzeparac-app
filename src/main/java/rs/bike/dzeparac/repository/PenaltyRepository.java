package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.bike.dzeparac.model.Penalty;

import java.time.LocalDate;
import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    List<Penalty> findByChildIdAndDateBetween(Long childId, LocalDate start, LocalDate end);
}