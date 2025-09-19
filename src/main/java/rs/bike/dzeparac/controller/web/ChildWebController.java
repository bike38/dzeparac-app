package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.Activity;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.repository.ActivityRepository;


@Controller
@RequestMapping("/web/child")
public class ChildWebController {

    private final ChildRepository childRepository;

    public ChildWebController(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("content", "~{child-form :: content}");
        return "layout";
    }

    @PostMapping("/save")
    public String save(@RequestParam String name,
                       @RequestParam(required = false) boolean active) {
        Child child = new Child();
        child.setName(name);
        child.setActive(active);
        childRepository.save(child);
        return "redirect:/web/child/new";
    }
}