package rs.bike.dzeparac.service;

import org.springframework.stereotype.Service;
import rs.bike.dzeparac.dto.ScoreRow;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.WeeklyScore;
import rs.bike.dzeparac.repository.ActivityRepository;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.repository.WeeklyScoreRepository;

import java.util.*;

@Service
public class ScoreMatrixService {

    private final WeeklyScoreRepository weeklyScoreRepository;
    private final ActivityRepository activityRepository;
    private final ChildRepository childRepository;

    public ScoreMatrixService(WeeklyScoreRepository weeklyScoreRepository,
                              ActivityRepository activityRepository,
                              ChildRepository childRepository) {
        this.weeklyScoreRepository = weeklyScoreRepository;
        this.activityRepository = activityRepository;
        this.childRepository = childRepository;
    }

    public List<ScoreRow> getMatrix(Child child, int year) {
        List<Activity> activities = activityRepository.findAll();
        List<WeeklyScore> scores = weeklyScoreRepository.findByChildAndSchoolYear(child, year);

        Map<Long, Map<Integer, WeeklyScore>> scoreMap = new HashMap<>();
        for (WeeklyScore ws : scores) {
            scoreMap
                    .computeIfAbsent(ws.getActivity().getId(), k -> new HashMap<>())
                    .put(ws.getWeekIndex(), ws);
        }

        List<ScoreRow> matrix = new ArrayList<>();
        for (Activity activity : activities) {
            List<Integer> weeklyScores = new ArrayList<>();
            for (int i = 0; i < 52; i++) {
                WeeklyScore ws = scoreMap.getOrDefault(activity.getId(), Collections.emptyMap()).get(i);
                int value = (ws != null) ? ws.getScore() : 0;
                weeklyScores.add(value);
            }
            matrix.add(new ScoreRow(activity, weeklyScores));
        }

        return matrix;
    }

    public void updateScore(Long childId, Long activityId, int weekIndex, int score, int year) {
        Child child = childRepository.findById(childId).orElseThrow();
        Activity activity = activityRepository.findById(activityId).orElseThrow();

        List<WeeklyScore> existing = weeklyScoreRepository.findByChildAndActivityAndSchoolYear(child, activity, year);
        WeeklyScore target = existing.stream()
                .filter(ws -> ws.getWeekIndex() == weekIndex)
                .findFirst()
                .orElse(null);

        if (target == null) {
            target = new WeeklyScore(activity, child, weekIndex, score, year, false);
        } else {
            target.setScore(score);
        }

        weeklyScoreRepository.save(target);
    }

    public void lockWeek(Long childId, int weekIndex, int year) {
        Child child = childRepository.findById(childId).orElseThrow();
        List<WeeklyScore> scores = weeklyScoreRepository.findByChildAndSchoolYear(child, year);

        for (WeeklyScore ws : scores) {
            if (ws.getWeekIndex() == weekIndex) {
                ws.setLocked(true);
                weeklyScoreRepository.save(ws);
            }
        }
    }


}