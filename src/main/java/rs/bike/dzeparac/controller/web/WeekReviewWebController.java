package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.WeeklyScore;
import rs.bike.dzeparac.repository.WeeklyScoreRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/week-review")
public class WeekReviewWebController {

    private final WeeklyScoreRepository weeklyScoreRepository;

    public WeekReviewWebController(WeeklyScoreRepository weeklyScoreRepository) {
        this.weeklyScoreRepository = weeklyScoreRepository;
    }

    @GetMapping("/{childId}/{startWeek}/{endWeek}")
    public String reviewWeek(@PathVariable Long childId,
                             @PathVariable int startWeek,
                             @PathVariable int endWeek,
                             Model model) {
        List<WeeklyScore> scores = weeklyScoreRepository.findByChildIdAndWeekIndexBetween(childId, startWeek, endWeek);
        model.addAttribute("scores", scores);
        return "week-review";
    }
}