package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.dto.ScoreMatrixRow;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.WeeklyScore;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.repository.WeeklyScoreRepository;
import rs.bike.dzeparac.service.ScoreMatrixService;

import java.util.List;

@Controller
@RequestMapping("/matrix")
public class ScoreMatrixController {

    private final ScoreMatrixService scoreMatrixService;
    private final ChildRepository childRepository;
    private final WeeklyScoreRepository weeklyScoreRepository;

    public ScoreMatrixController(ScoreMatrixService scoreMatrixService,
                                 ChildRepository childRepository,
                                 WeeklyScoreRepository weeklyScoreRepository) {
        this.scoreMatrixService = scoreMatrixService;
        this.childRepository = childRepository;
        this.weeklyScoreRepository = weeklyScoreRepository;
    }

    @GetMapping("/{childId}/{year}")
    public String showMatrix(@PathVariable Long childId,
                             @PathVariable int year,
                             Model model) {
        Child child = childRepository.findById(childId).orElseThrow();
        List<ScoreMatrixRow> matrix = scoreMatrixService.getMatrixForChildAndYear(child, year);

        model.addAttribute("child", child);
        model.addAttribute("year", year);
        model.addAttribute("matrix", matrix);
        return "matrix";
    }

    @PostMapping("/update")
    public String updateScore(@RequestParam Long childId,
                              @RequestParam Long activityId,
                              @RequestParam int weekIndex,
                              @RequestParam int score,
                              @RequestParam int year) {
        scoreMatrixService.updateScore(childId, activityId, weekIndex, score, year);
        return "redirect:/matrix/" + childId + "/" + year;
    }

    @PostMapping("/lock")
    public String lockWeek(@RequestParam Long childId,
                           @RequestParam int weekIndex,
                           @RequestParam int year) {
        scoreMatrixService.lockWeek(childId, weekIndex, year);
        return "redirect:/matrix/" + childId + "/" + year;
    }
}