package rs.bike.dzeparac.service;

import org.springframework.stereotype.Service;
import rs.bike.dzeparac.dto.ScoreMatrixRow;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.WeeklyScore;
import rs.bike.dzeparac.repository.ActivityRepository;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.repository.WeeklyScoreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreMatrixService {

    private final ActivityRepository activityRepository;
    private final WeeklyScoreRepository weeklyScoreRepository;
    private final ChildRepository childRepository;

    public ScoreMatrixService(ActivityRepository activityRepository,
                              WeeklyScoreRepository weeklyScoreRepository,
                              ChildRepository childRepository) {
        this.activityRepository = activityRepository;
        this.weeklyScoreRepository = weeklyScoreRepository;
        this.childRepository = childRepository;
    }

    public List<ScoreMatrixRow> getMatrixForChildAndYear(Child child, int schoolYear) {
        List<Activity> activities = activityRepository.findAll();
        List<WeeklyScore> scores = weeklyScoreRepository.findByChildAndSchoolYear(child, schoolYear);

        List<ScoreMatrixRow> matrix = new ArrayList<>();

        for (Activity activity : activities) {
            ScoreMatrixRow row = new ScoreMatrixRow(activity);
            for (WeeklyScore score : scores) {
                if (score.getActivity().getId().equals(activity.getId())) {
                    row.setScoreForWeek(score.getWeekIndex(), score.getScore());
                }
            }
            matrix.add(row);
        }

        return matrix;
    }

    public void updateScore(Long childId, Long activityId, int weekIndex, int score, int year) {
        Child child = childRepository.findById(childId).orElseThrow();
        Activity activity = activityRepository.findById(activityId).orElseThrow();

        WeeklyScore existing = weeklyScoreRepository
                .findByChildAndActivityAndSchoolYear(child, activity, year)
                .stream()
                .filter(ws -> ws.getWeekIndex() == weekIndex)
                .findFirst()
                .orElse(null);

        if (existing != null && !existing.isLocked()) {
            existing.setScore(score);
            weeklyScoreRepository.save(existing);
        } else if (existing == null) {
            WeeklyScore newScore = new WeeklyScore(activity, child, weekIndex, score, year, false);
            weeklyScoreRepository.save(newScore);
        }
    }

    public void lockWeek(Long childId, int weekIndex, int year) {
        Child child = childRepository.findById(childId).orElseThrow();
        List<WeeklyScore> scores = weeklyScoreRepository.findByChildAndSchoolYear(child, year);

        for (WeeklyScore score : scores) {
            if (score.getWeekIndex() == weekIndex) {
                score.setLocked(true);
            }
        }
        weeklyScoreRepository.saveAll(scores);
    }
}