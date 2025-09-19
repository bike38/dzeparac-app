package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.*;
import rs.bike.dzeparac.repository.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("web/adjustments")
public class AdjustmentWebController {

    private final PenaltyRepository penaltyRepo;
    private final AchievementRewardRepository rewardRepo;
    private final ChildRepository childRepo;

    public AdjustmentWebController(PenaltyRepository penaltyRepo,
                                   AchievementRewardRepository rewardRepo,
                                   ChildRepository childRepo) {
        this.penaltyRepo = penaltyRepo;
        this.rewardRepo = rewardRepo;
        this.childRepo = childRepo;
    }

    @GetMapping
    public String showAdjustmentForm(Model model) {
        model.addAttribute("children", childRepo.findAll());
        model.addAttribute("penalties", penaltyRepo.findAll());
        model.addAttribute("rewards", rewardRepo.findAll());
        return "adjustments";
    }

    @PostMapping
    public String submitAdjustment(@RequestParam Long childId,
                                   @RequestParam String type,
                                   @RequestParam String description,
                                   @RequestParam int amount,
                                   @RequestParam String date) {
        Child child = childRepo.findById(childId).orElse(null);
        if (child == null) return "redirect:/adjustments";

        LocalDate parsedDate = LocalDate.parse(date);

        if ("PENALTY".equalsIgnoreCase(type)) {
            Penalty penalty = new Penalty();
            penalty.setChild(child);
            penalty.setDescription(description);
            penalty.setAmount(amount);
            penalty.setDate(parsedDate);
            penaltyRepo.save(penalty);
        } else if ("REWARD".equalsIgnoreCase(type)) {
            AchievementReward reward = new AchievementReward();
            reward.setChild(child);
            reward.setCategory(description); // assuming description maps to category
            reward.setAmount(amount);
            reward.setDate(parsedDate);
            rewardRepo.save(reward);
        }

        return "redirect:/adjustments";
    }
}