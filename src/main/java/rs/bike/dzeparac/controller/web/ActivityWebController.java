package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.ActivityType;
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
        Activity activity = (id != null)
                ? activityRepository.findById(id).orElse(new Activity())
                : new Activity();

        model.addAttribute("activity", activity);
        model.addAttribute("types", ActivityType.values());
        model.addAttribute("activities", activityRepository.findAll());

        return "activity-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Activity activity) {
        activityRepository.save(activity);
        return "redirect:/web/activity/new";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        activityRepository.deleteById(id);
        return "redirect:/web/activity/new";
    }
}