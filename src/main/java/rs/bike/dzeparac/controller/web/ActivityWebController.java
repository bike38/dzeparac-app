package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.repository.ActivityRepository;


@Controller
@RequestMapping("/web/activity")
public class ActivityWebController {

    private final ActivityRepository activityRepository;

    public ActivityWebController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("content", "activity-form");
        return "layout";
    }

    @PostMapping("/save")
    public String save(@RequestParam String name,
                       @RequestParam int maxScore) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setMaxScore(maxScore);
        activityRepository.save(activity);
        return "redirect:web/activity/new";
    }
}
