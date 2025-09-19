package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.WeeklyScore;

import java.util.List;

public interface WeeklyScoreRepository extends JpaRepository<WeeklyScore, Long> {

    List<WeeklyScore> findByChildIdAndWeekIndexBetween(Long childId, int startWeek, int endWeek);

    List<WeeklyScore> findByChildAndSchoolYear(Child child, int schoolYear);

    List<WeeklyScore> findByChildAndActivityAndSchoolYear(Child child, Activity activity, int schoolYear);

}
