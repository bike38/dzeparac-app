package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.repository.ActivityRepository;


@Controller
@RequestMapping("/web/activity")
public class ActivityWebController {

    private final ActivityRepository activityRepository;

    public ActivityWebController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping("/new")
    public String showForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Activity activity = activityRepository.findById(id).orElse(null);
            if (activity != null) {
                model.addAttribute("editId", activity.getId());
                model.addAttribute("name", activity.getName());
                model.addAttribute("maxScore", activity.getMaxScore());
            }
        }
        model.addAttribute("activities", activityRepository.findAll());
        return "activity-form";
    }

    @PostMapping("/save")
    public String save(@RequestParam(required = false) Long id,
                       @RequestParam String name,
                       @RequestParam int maxScore) {
        Activity activity = (id != null)
                ? activityRepository.findById(id).orElse(new Activity())
                : new Activity();
        activity.setName(name);
        activity.setMaxScore(maxScore);
        activityRepository.save(activity);
        return "redirect:/web/activity/new";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        activityRepository.deleteById(id);
        return "redirect:/web/activity/new";
    }
}