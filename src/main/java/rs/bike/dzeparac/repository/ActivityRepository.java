package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.ActivityType;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByType(ActivityType type);

    List<Activity> findByActiveThisWeekTrue();

    List<Activity> findByTypeAndActiveThisWeekTrue(ActivityType type);
}