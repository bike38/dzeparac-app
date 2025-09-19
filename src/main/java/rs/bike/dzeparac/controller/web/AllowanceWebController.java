package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.service.AllowanceService;

@Controller
@RequestMapping("/allowance")
public class AllowanceWebController {

    private final AllowanceService allowanceService;
    private final ChildRepository childRepository;

    public AllowanceWebController(AllowanceService allowanceService,
                                  ChildRepository childRepository) {
        this.allowanceService = allowanceService;
        this.childRepository = childRepository;
    }

    @GetMapping("/{childId}")
    public String showAllowance(@PathVariable Long childId, Model model) {
        Child child = childRepository.findById(childId).orElseThrow();
        model.addAttribute("child", child);
        return "allowance";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam Long childId,
                            @RequestParam int startWeek,
                            @RequestParam int endWeek,
                            Model model) {
        Child child = childRepository.findById(childId).orElseThrow();
        int amount = allowanceService.calculateWeeklyAllowance(childId, startWeek, endWeek);
        model.addAttribute("child", child);
        model.addAttribute("startWeek", startWeek);
        model.addAttribute("endWeek", endWeek);
        model.addAttribute("amount", amount);
        return "allowance";
    }

    @PostMapping("/save")
    public String save(@RequestParam Long childId,
                       @RequestParam int amount,
                       @RequestParam int weekIndex) {
        Child child = childRepository.findById(childId).orElseThrow();
        allowanceService.saveAllowance(child, amount, weekIndex);
        return "redirect:/allowance/" + childId;
    }
}
