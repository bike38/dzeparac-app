package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.bike.dzeparac.model.Score;

import java.time.LocalDate;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByChildIdAndDateBetween(Long childId, LocalDate start, LocalDate end);

    List<Score> findByActivityIdAndDateBetween(Long activityId, LocalDate start, LocalDate end);
}