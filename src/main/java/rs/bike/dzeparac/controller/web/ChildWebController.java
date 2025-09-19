package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.repository.ChildRepository;

@Controller
@RequestMapping("web/children")
public class ChildWebController {

    private final ChildRepository childRepo;

    public ChildWebController(ChildRepository childRepo) {
        System.out.println("ChildWebController loaded");
        this.childRepo = childRepo;
    }

    @GetMapping
    public String showChildren(Model model) {
        model.addAttribute("children", childRepo.findAll());
        return "children";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Radi!";
    }

    @PostMapping
    public String addChild(@RequestParam String name) {
        Child child = new Child();
        child.setName(name);
        childRepo.save(child);
        return "redirect:/web/children";
    }

}
