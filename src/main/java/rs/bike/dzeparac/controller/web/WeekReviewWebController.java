package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.*;
import rs.bike.dzeparac.repository.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web/weeks")
public class WeekReviewWebController {

    private final ScoreRepository scoreRepo;
    private final ActivityRepository activityRepo;
    private final ChildRepository childRepo;

    public WeekReviewWebController(ScoreRepository scoreRepo,
                                   ActivityRepository activityRepo,
                                   ChildRepository childRepo) {
        this.scoreRepo = scoreRepo;
        this.activityRepo = activityRepo;
        this.childRepo = childRepo;
    }

    @GetMapping
    public String showWeekReview(@RequestParam(required = false) Long childId,
                                 @RequestParam(defaultValue = "4") int weeks,
                                 Model model) {

        List<Child> children = childRepo.findAll();
        model.addAttribute("children", children);

        if (childId == null && !children.isEmpty()) {
            childId = children.get(0).getId();
        }

        Child selectedChild = childRepo.findById(childId).orElse(null);
        model.addAttribute("selectedChild", selectedChild);

        if (selectedChild == null) {
            model.addAttribute("weeklyScores", null);
            model.addAttribute("weeklyAllowanceTotals", null);
            return "week-review";
        }

        LocalDate today = LocalDate.now();
        List<Activity> allActivities = activityRepo.findAll();
        Map<LocalDate, Map<Activity, Score>> weeklyScores = new LinkedHashMap<>();
        Map<LocalDate, Integer> weeklyAllowanceTotals = new LinkedHashMap<>();

        for (int i = 0; i < weeks; i++) {
            LocalDate weekStart = today.minusWeeks(i).with(DayOfWeek.MONDAY);
            LocalDate weekEnd = weekStart.plusDays(6);

            List<Score> scores = scoreRepo.findByChildIdAndDateBetween(childId, weekStart, weekEnd);
            Map<Activity, Score> activityMap = scores.stream()
                    .collect(Collectors.toMap(Score::getActivity, s -> s));

            int total = scores.stream()
                    .mapToInt(s ->
                            (s.getValue() != null ? s.getValue() : 0) +
                                    (s.getReward() != null ? s.getReward().getRequiredPoints() : 0)
                                    +
                                    (s.getPenalty() != null ? -s.getPenalty().getAmount() : 0) +
                                    (s.getGift() != null ? s.getGift().getAmount() : 0)

                    ).sum();

            weeklyScores.put(weekStart, activityMap);
            weeklyAllowanceTotals.put(weekStart, total);
        }

        model.addAttribute("activities", allActivities);
        model.addAttribute("weeklyScores", weeklyScores);
        model.addAttribute("weeklyAllowanceTotals", weeklyAllowanceTotals);

        return "week-review";
    }
}