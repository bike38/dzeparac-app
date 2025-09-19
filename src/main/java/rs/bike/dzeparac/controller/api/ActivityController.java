package rs.bike.dzeparac.controller.api;

import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.model.ActivityType;
import rs.bike.dzeparac.repository.ActivityRepository;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityRepository activityRepo;

    public ActivityController(ActivityRepository activityRepo) {
        this.activityRepo = activityRepo;
    }

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityRepo.findAll();
    }

    @GetMapping("/active")
    public List<Activity> getActiveThisWeek() {
        return activityRepo.findByActiveThisWeekTrue();
    }

    @GetMapping("/type/{type}")
    public List<Activity> getByType(@PathVariable ActivityType type) {
        return activityRepo.findByType(type);
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityRepo.save(activity);
    }

    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable Long id, @RequestBody Activity updated) {
        return activityRepo.findById(id).map(activity -> {
            activity.setName(updated.getName());
            activity.setDescription(updated.getDescription());
            activity.setMaxPoints(updated.getMaxPoints());
            activity.setType(updated.getType());
            activity.setActiveThisWeek(updated.getActiveThisWeek());
            return activityRepo.save(activity);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Long id) {
        activityRepo.deleteById(id);
    }
}