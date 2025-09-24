package rs.bike.dzeparac.service;

import org.springframework.stereotype.Service;
import rs.bike.dzeparac.model.Allowance;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.WeeklyScore;
import rs.bike.dzeparac.repository.AllowanceRepository;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.service.WeeklyScoreService;

import java.util.List;

@Service
public class AllowanceService {

    private final WeeklyScoreService weeklyScoreService;
    private final ChildRepository childRepository;
    private final AllowanceRepository allowanceRepository;

    public AllowanceService(WeeklyScoreService weeklyScoreService,
                            ChildRepository childRepository,
                            AllowanceRepository allowanceRepository) {
        this.weeklyScoreService = weeklyScoreService;
        this.childRepository = childRepository;
        this.allowanceRepository = allowanceRepository;
    }

    public int calculateWeeklyTotal(Long childId, int weekIndex) {
        List<WeeklyScore> scores = weeklyScoreService.getScoresInRange(childId, weekIndex, weekIndex);
        return scores.stream().mapToInt(WeeklyScore::getScore).sum();
    }

    public int calculateAllowance(int subtotal, int baseAmount) {
        return subtotal >= 100 ? baseAmount : (subtotal * baseAmount / 100);
    }

    public int calculateWeeklyAllowance(Long childId, int weekIndex, int baseAmount) {
        int subtotal = calculateWeeklyTotal(childId, weekIndex);
        return calculateAllowance(subtotal, baseAmount);
    }

    public void lockWeek(Long childId, int weekIndex, int schoolYear) {
        weeklyScoreService.lockWeek(childId, weekIndex, schoolYear);
    }

    public Child getChild(Long childId) {
        return childRepository.findById(childId).orElseThrow();
    }

    public void saveAllowance(Child child, int weekIndex, int amount) {
        Allowance allowance = new Allowance();
        allowance.setChild(child);
        allowance.setWeekIndex(weekIndex);
        allowance.setAmount(amount);
        allowance.setSchoolYear(2025); // ili dinamički
        allowanceRepository.save(allowance);
    }
}
