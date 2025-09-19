package rs.bike.dzeparac.service;

import org.springframework.stereotype.Service;
import rs.bike.dzeparac.model.Allowance;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.WeeklyScore;
import rs.bike.dzeparac.repository.AllowanceRepository;
import rs.bike.dzeparac.repository.WeeklyScoreRepository;

import java.util.List;

@Service
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
    private final WeeklyScoreRepository weeklyScoreRepository;

    public AllowanceService(AllowanceRepository allowanceRepository,
                            WeeklyScoreRepository weeklyScoreRepository) {
        this.allowanceRepository = allowanceRepository;
        this.weeklyScoreRepository = weeklyScoreRepository;
    }

    public int calculateTotalPoints(Long childId, int startWeek, int endWeek) {
        List<WeeklyScore> scores = weeklyScoreRepository.findByChildIdAndWeekIndexBetween(childId, startWeek, endWeek);
        return scores.stream()
                .mapToInt(WeeklyScore::getScore)
                .sum();
    }

    public int calculateMaxPoints(Long childId, int startWeek, int endWeek) {
        List<WeeklyScore> scores = weeklyScoreRepository.findByChildIdAndWeekIndexBetween(childId, startWeek, endWeek);
        return scores.stream()
                .mapToInt(ws -> ws.getActivity().getMaxScore())
                .sum();
    }

    public int calculateWeeklyAllowance(Long childId, int startWeek, int endWeek) {
        int total = calculateTotalPoints(childId, startWeek, endWeek);
        int max = calculateMaxPoints(childId, startWeek, endWeek);
        if (max == 0) return 0;
        return (int) Math.round((double) total / max * 100); // primer: procenat
    }

    public void saveAllowance(Child child, int amount, int weekIndex) {
        Allowance allowance = new Allowance();
        allowance.setChild(child);
        allowance.setAmount(amount);
        allowance.setWeekIndex(weekIndex);
        allowanceRepository.save(allowance);
    }
}