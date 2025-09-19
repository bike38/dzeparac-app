package rs.bike.dzeparac.controller.api;

import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.service.AllowanceService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/allowance")
public class AllowanceController {

    private final AllowanceService allowanceService;

    public AllowanceController(AllowanceService allowanceService) {
        this.allowanceService = allowanceService;
    }

    @GetMapping("/{childId}")
    public int getWeeklyAllowance(@PathVariable Long childId,
                                  @RequestParam LocalDate start,
                                  @RequestParam LocalDate end) {
        return allowanceService.calculateWeeklyAllowance(childId, start, end);
    }
}