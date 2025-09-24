package rs.bike.dzeparac.service;

import org.springframework.stereotype.Service;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.WeeklyScore;
import rs.bike.dzeparac.repository.ActivityRepository;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.repository.WeeklyScoreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyScoreService {

    private final WeeklyScoreRepository weeklyScoreRepository;
    private final ChildRepository childRepository;
    private final ActivityRepository activityRepository;

    public WeeklyScoreService(WeeklyScoreRepository weeklyScoreRepository,
                              ChildRepository childRepository,
                              ActivityRepository activityRepository) {
        this.weeklyScoreRepository = weeklyScoreRepository;
        this.childRepository = childRepository;
        this.activityRepository = activityRepository;
    }

    public List<WeeklyScore> getScoresForChildAndYear(Long childId, int schoolYear) {
        Child child = childRepository.findById(childId).orElseThrow();
        return weeklyScoreRepository.findByChildAndSchoolYear(child, schoolYear);
    }

    public List<WeeklyScore> getScoresForActivity(Long childId, Long activityId, int schoolYear) {
        Child child = childRepository.findById(childId).orElseThrow();
        Activity activity = activityRepository.findById(activityId).orElseThrow();
        return weeklyScoreRepository.findByChildAndActivityAndSchoolYear(child, activity, schoolYear);
    }

    public Optional<WeeklyScore> getScore(Long childId, Long activityId, int weekIndex, int schoolYear) {
        Child child = childRepository.findById(childId).orElseThrow();
        Activity activity = activityRepository.findById(activityId).orElseThrow();
        return weeklyScoreRepository.findByChildAndActivityAndWeekIndexAndSchoolYear(child, activity, weekIndex, schoolYear);
    }

    public void saveScore(Long childId, Long activityId, int weekIndex, int score, int schoolYear) {
        Child child = childRepository.findById(childId).orElseThrow();
        Activity activity = activityRepository.findById(activityId).orElseThrow();

        Optional<WeeklyScore> existing = weeklyScoreRepository.findByChildAndActivityAndWeekIndexAndSchoolYear(
                child, activity, weekIndex, schoolYear);

        WeeklyScore ws = existing.orElseGet(() -> new WeeklyScore(activity, child, weekIndex, score, schoolYear, false));
        ws.setScore(score);
        weeklyScoreRepository.save(ws);
    }

    public void lockWeek(Long childId, int weekIndex, int schoolYear) {
        Child child = childRepository.findById(childId).orElseThrow();
        List<WeeklyScore> scores = weeklyScoreRepository.findByChildAndSchoolYear(child, schoolYear);

        for (WeeklyScore ws : scores) {
            if (ws.getWeekIndex() == weekIndex) {
                ws.setLocked(true);
                weeklyScoreRepository.save(ws);
            }
        }
    }

    public List<WeeklyScore> getScoresInRange(Long childId, int startWeek, int endWeek) {
        return weeklyScoreRepository.findByChild_IdAndWeekIndexBetween(childId, startWeek, endWeek);
    }

    public List<WeeklyScore> getScoresInRangeAndYear(Long childId, int startWeek, int endWeek, int schoolYear) {
        return weeklyScoreRepository.findByChild_IdAndWeekIndexBetweenAndSchoolYear(childId, startWeek, endWeek, schoolYear);
    }
}
