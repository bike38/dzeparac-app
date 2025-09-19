package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.bike.dzeparac.model.AchievementReward;

import java.time.LocalDate;
import java.util.List;

public interface AchievementRewardRepository extends JpaRepository<AchievementReward, Long> {

    List<AchievementReward> findByChildIdAndDateBetween(Long childId, LocalDate start, LocalDate end);
}