package rs.bike.dzeparac.controller.api;

import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.repository.ChildRepository;

import java.util.List;

@RestController
@RequestMapping("/api/children")
public class ChildController {

    private final ChildRepository childRepo;

    public ChildController(ChildRepository childRepo) {
        this.childRepo = childRepo;
    }

    @GetMapping
    public List<Child> getAllChildren() {
        return childRepo.findAll();
    }

    @GetMapping("/{id}")
    public Child getChildById(@PathVariable Long id) {
        return childRepo.findById(id).orElse(null);
    }

    @PostMapping
    public Child createChild(@RequestBody Child child) {
        return childRepo.save(child);
    }

    @PutMapping("/{id}")
    public Child updateChild(@PathVariable Long id, @RequestBody Child updatedChild) {
        return childRepo.findById(id).map(child -> {
            child.setName(updatedChild.getName());
            return childRepo.save(child);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteChild(@PathVariable Long id) {
        childRepo.deleteById(id);
    }
}