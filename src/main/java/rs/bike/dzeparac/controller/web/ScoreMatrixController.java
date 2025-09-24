package rs.bike.dzeparac.controller.web;

import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.service.ScoreMatrixService;

@RestController
@RequestMapping("/web/matrix")
public class ScoreMatrixController {

    private final ScoreMatrixService scoreMatrixService;

    public ScoreMatrixController(ScoreMatrixService scoreMatrixService) {
        this.scoreMatrixService = scoreMatrixService;
    }

    @PostMapping("/save-score")
    public String saveScore(@RequestParam Long childId,
                            @RequestParam Long activityId,
                            @RequestParam int weekIndex,
                            @RequestParam int score,
                            @RequestParam(defaultValue = "2025") int year) {
        scoreMatrixService.updateScore(childId, activityId, weekIndex, score, year);
        return "OK";
    }
}
