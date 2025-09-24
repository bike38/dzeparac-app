package rs.bike.dzeparac.service;

import org.springframework.stereotype.Service;
import rs.bike.dzeparac.dto.ScoreRow;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.ActivityType;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.WeeklyScore;
import rs.bike.dzeparac.repository.ActivityRepository;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.repository.WeeklyScoreRepository;
import rs.bike.dzeparac.service.WeeklyScoreService;


import java.util.*;
import java.util.stream.Collectors;

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

    // 🔹 Generiši matricu ocena za dete i godinu
    public List<ScoreRow> getMatrixForChildAndYear(Child child, int year) {
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

    // 🔹 Filtriraj aktivnosti po tipu
    public List<ScoreRow> filterByType(List<ScoreRow> allRows, ActivityType type) {
        return allRows.stream()
                .filter(row -> row.getActivity().getType().equals(type))
                .collect(Collectors.toList());
    }

    // 🔹 Saberi ocene po nedeljama
    public List<Integer> sumByWeek(List<ScoreRow> rows) {
        List<Integer> subtotal = new ArrayList<>(Collections.nCopies(52, 0));
        for (ScoreRow row : rows) {
            for (int i = 0; i < row.getWeeklyScores().size(); i++) {
                subtotal.set(i, subtotal.get(i) + row.getWeeklyScores().get(i));
            }
        }
        return subtotal;
    }

    // 🔹 Saberi više subtotala u jednu ukupnu ocenu
    public List<Integer> sumTotal(List<List<Integer>> subtotals) {
        List<Integer> total = new ArrayList<>(Collections.nCopies(52, 0));
        for (List<Integer> sub : subtotals) {
            for (int i = 0; i < sub.size(); i++) {
                total.set(i, total.get(i) + sub.get(i));
            }
        }
        return total;
    }

    // 🔹 Računaj džeparac za jednu nedelju
    public Integer calculateAllowance(int score, int baseAmount) {
        return score >= 100 ? baseAmount : (score * baseAmount / 100);
    }

    // 🔹 Računaj džeparac za sve nedelje
    public List<Integer> calculateAllowance(List<Integer> scores, int baseAmount) {
        return scores.stream()
                .map(score -> calculateAllowance(score, baseAmount))
                .collect(Collectors.toList());
    }

    // 🔹 Unesi ili ažuriraj ocenu
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

    // 🔹 Zaključa nedelju
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