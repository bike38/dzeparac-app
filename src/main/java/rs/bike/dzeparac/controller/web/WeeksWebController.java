package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.service.ScoreMatrixService;

import java.util.Collections;

import java.util.List;

@Controller
@RequestMapping("/web/weeks")
public class WeeksWebController {

    private final ChildRepository childRepository;
    private final ScoreMatrixService scoreMatrixService;

    public WeeksWebController(ChildRepository childRepository, ScoreMatrixService scoreMatrixService) {
        this.childRepository = childRepository;
        this.scoreMatrixService = scoreMatrixService;
    }

    @GetMapping
    public String showMatrix(@RequestParam(required = false) Long childId, Model model) {
        List<Child> children = childRepository.findAll();
        Child selectedChild = null;

        if (childId != null) {
            selectedChild = childRepository.findById(childId).orElse(null);
        } else if (!children.isEmpty()) {
            selectedChild = children.get(0);
        }

        model.addAttribute("children", children);
        model.addAttribute("selectedChildId", selectedChild != null ? selectedChild.getId() : null);
        model.addAttribute("scoreMatrix", selectedChild != null ? scoreMatrixService.getMatrix(selectedChild, 2025) : Collections.emptyList());

        return "weeks";
    }
}
