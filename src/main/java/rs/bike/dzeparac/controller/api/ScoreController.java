package rs.bike.dzeparac.controller.api;

import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Score;
import rs.bike.dzeparac.repository.ScoreRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/log/scores")
public class ScoreController {

    private final ScoreRepository scoreRepo;

    public ScoreController(ScoreRepository scoreRepo) {
        this.scoreRepo = scoreRepo;
    }

    @GetMapping
    public List<Score> getAllScores() {
        return scoreRepo.findAll();
    }

    @GetMapping("/child/{childId}/range")
    public List<Score> getByChildAndWeek(@PathVariable Long childId,
                                         @RequestParam LocalDate start,
                                         @RequestParam LocalDate end) {
        return scoreRepo.findByChildIdAndDateBetween(childId, start, end);
    }

    @PostMapping
    public Score createScore(@RequestBody Score score) {
        return scoreRepo.save(score);
    }

    @DeleteMapping("/{id}")
    public void deleteScore(@PathVariable Long id) {
        scoreRepo.deleteById(id);
    }
}