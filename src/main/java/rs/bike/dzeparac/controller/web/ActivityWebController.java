package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.ActivityType;
import rs.bike.dzeparac.repository.ActivityRepository;

@Controller
@RequestMapping("web/activities")
public class ActivityWebController {

    private final ActivityRepository activityRepo;

    public ActivityWebController(ActivityRepository activityRepo) {
        this.activityRepo = activityRepo;
    }

    @GetMapping
    public String showActivities(Model model) {
        model.addAttribute("activities", activityRepo.findAll());
        return "activities";
    }

    @PostMapping
    public String addActivity(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam int maxPoints,
                              @RequestParam ActivityType type,
                              @RequestParam(required = false) boolean activeThisWeek) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setMaxPoints(maxPoints);
        activity.setType(type);
        activity.setActiveThisWeek(activeThisWeek);
        activityRepo.save(activity);
        return "redirect:web/activities";
    }
}