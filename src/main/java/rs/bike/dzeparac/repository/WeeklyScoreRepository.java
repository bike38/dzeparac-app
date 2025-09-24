package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.WeeklyScore;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyScoreRepository extends JpaRepository<WeeklyScore, Long> {

    // Za matricu ocena po aktivnosti
    List<WeeklyScore> findByChildAndActivityAndSchoolYear(Child child, Activity activity, int schoolYear);

    // Za sve ocene deteta u godini
    List<WeeklyScore> findByChildAndSchoolYear(Child child, int schoolYear);

    // Za snimanje jedne ocene
    Optional<WeeklyScore> findByChildAndActivityAndWeekIndexAndSchoolYear(
            Child child, Activity activity, int weekIndex, int schoolYear);

    // Za džeparac po opsegu nedelja (koristi childId direktno)
    List<WeeklyScore> findByChild_IdAndWeekIndexBetween(Long childId, int startWeek, int endWeek);

    // Ako koristiš i školsku godinu u tom kontekstu
    List<WeeklyScore> findByChild_IdAndWeekIndexBetweenAndSchoolYear(Long childId, int startWeek, int endWeek, int schoolYear);
}
