package rs.bike.dzeparac.controller.api;

import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Penalty;
import rs.bike.dzeparac.repository.PenaltyRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/penalties")
public class PenaltyController {

    private final PenaltyRepository penaltyRepo;

    public PenaltyController(PenaltyRepository penaltyRepo) {
        this.penaltyRepo = penaltyRepo;
    }

    @GetMapping
    public List<Penalty> getAllPenalties() {
        return penaltyRepo.findAll();
    }

    @GetMapping("/child/{childId}/range")
    public List<Penalty> getByChildAndWeek(@PathVariable Long childId,
                                           @RequestParam LocalDate start,
                                           @RequestParam LocalDate end) {
        return penaltyRepo.findByChildIdAndDateBetween(childId, start, end);
    }

    @PostMapping
    public Penalty createPenalty(@RequestBody Penalty penalty) {
        return penaltyRepo.save(penalty);
    }

    @DeleteMapping("/{id}")
    public void deletePenalty(@PathVariable Long id) {
        penaltyRepo.deleteById(id);
    }
}