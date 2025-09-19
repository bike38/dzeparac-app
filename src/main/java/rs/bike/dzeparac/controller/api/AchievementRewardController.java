package rs.bike.dzeparac.controller.api;

import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.AchievementReward;
import rs.bike.dzeparac.repository.AchievementRewardRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class AchievementRewardController {

    private final AchievementRewardRepository rewardRepo;

    public AchievementRewardController(AchievementRewardRepository rewardRepo) {
        this.rewardRepo = rewardRepo;
    }

    @GetMapping
    public List<AchievementReward> getAllRewards() {
        return rewardRepo.findAll();
    }

    @GetMapping("/child/{childId}/range")
    public List<AchievementReward> getByChildAndWeek(@PathVariable Long childId,
                                                     @RequestParam LocalDate start,
                                                     @RequestParam LocalDate end) {
        return rewardRepo.findByChildIdAndDateBetween(childId, start, end);
    }

    @PostMapping
    public AchievementReward createReward(@RequestBody AchievementReward reward) {
        return rewardRepo.save(reward);
    }

    @DeleteMapping("/{id}")
    public void deleteReward(@PathVariable Long id) {
        rewardRepo.deleteById(id);
    }
}