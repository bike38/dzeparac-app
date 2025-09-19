package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.service.AllowanceService;

import java.time.LocalDate;

@Controller
@RequestMapping("web/allowance")
public class AllowanceWebController {

    private final AllowanceService allowanceService;

    public AllowanceWebController(AllowanceService allowanceService) {
        this.allowanceService = allowanceService;
    }

    @GetMapping
    public String showAllowanceForm(@RequestParam(required = false) Long childId,
                                    @RequestParam(required = false) LocalDate start,
                                    @RequestParam(required = false) LocalDate end,
                                    Model model) {
        if (childId != null && start != null && end != null) {
            int amount = allowanceService.calculateWeeklyAllowance(childId, start, end);
            model.addAttribute("amount", amount);
        }
        return "allowance";
    }
}