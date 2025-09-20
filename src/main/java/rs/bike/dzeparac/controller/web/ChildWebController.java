package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.repository.ChildRepository;

@Controller
@RequestMapping("/web/child")
public class ChildWebController {

    private final ChildRepository childRepository;

    public ChildWebController(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @GetMapping("/new")
    public String showForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Child child = childRepository.findById(id).orElse(null);
            if (child != null) {
                model.addAttribute("editId", child.getId());
                model.addAttribute("name", child.getName());
                model.addAttribute("active", child.isActive());
            }
        }
        model.addAttribute("children", childRepository.findAll());
        return "child-form";
    }

    @PostMapping("/save")
    public String save(@RequestParam(required = false) Long id,
                       @RequestParam String name,
                       @RequestParam(required = false) boolean active) {
        Child child = (id != null)
                ? childRepository.findById(id).orElse(new Child())
                : new Child();
        child.setName(name);
        child.setActive(active);
        childRepository.save(child);
        return "redirect:/web/child/new";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        childRepository.deleteById(id);
        return "redirect:/web/child/new";
    }
}
