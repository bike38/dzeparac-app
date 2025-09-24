package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.dto.ScoreRow;
import rs.bike.dzeparac.model.ActivityType;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.service.ScoreMatrixService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/web")
public class HomeWebController {

    private final ChildRepository childRepository;
    private final ScoreMatrixService scoreMatrixService;

    public HomeWebController(ChildRepository childRepository, ScoreMatrixService scoreMatrixService) {
        this.childRepository = childRepository;
        this.scoreMatrixService = scoreMatrixService;
    }

    @GetMapping("/index")
    public String index(@RequestParam(required = false) Long childId, Model model) {
        List<Child> children = childRepository.findAll();
        Child selectedChild = null;

        if (childId != null) {
            selectedChild = childRepository.findById(childId).orElse(null);
        } else if (!children.isEmpty()) {
            selectedChild = children.get(0);
        }

        model.addAttribute("children", children);
        model.addAttribute("selectedChildId", selectedChild != null ? selectedChild.getId() : null);

        if (selectedChild == null) {
            model.addAttribute("scoreMatrix", Collections.emptyList());
            return "index";
        }

        int year = 2025;
        int baseAmount = 500;

        List<ScoreRow> allRows = scoreMatrixService.getMatrixForChildAndYear(selectedChild, year);

        List<ScoreRow> osnovne = scoreMatrixService.filterByType(allRows, ActivityType.OSNOVNA);
        List<ScoreRow> dodatne = scoreMatrixService.filterByType(allRows, ActivityType.DODATNA);
        List<ScoreRow> timske = scoreMatrixService.filterByType(allRows, ActivityType.TIMSKA);

        List<Integer> subtotalOsnovne = scoreMatrixService.sumByWeek(osnovne);
        List<Integer> subtotalDodatne = scoreMatrixService.sumByWeek(dodatne);
        List<Integer> subtotalTimske = scoreMatrixService.sumByWeek(timske);

        List<Integer> ukupnaOcena = scoreMatrixService.sumTotal(List.of(subtotalOsnovne, subtotalDodatne, subtotalTimske));
        List<Integer> nedeljniDzeparac = scoreMatrixService.calculateAllowance(ukupnaOcena, baseAmount);

        model.addAttribute("osnovneAktivnosti", osnovne);
        model.addAttribute("dodatneAktivnosti", dodatne);
        model.addAttribute("timskeAktivnosti", timske);

        model.addAttribute("subtotalOsnovne", subtotalOsnovne);
        model.addAttribute("subtotalDodatne", subtotalDodatne);
        model.addAttribute("subtotalTimske", subtotalTimske);

        model.addAttribute("ukupnaOcena", ukupnaOcena);
        model.addAttribute("nedeljniDzeparac", nedeljniDzeparac);

        // Dummy finansijski podaci za prikaz
        model.addAttribute("startingBalance", 1000);
        model.addAttribute("totalAllowance", nedeljniDzeparac.stream().mapToInt(Integer::intValue).sum());
        model.addAttribute("totalRewards", 0);
        model.addAttribute("totalPenalties", 0);
        model.addAttribute("totalGifts", 0);
        model.addAttribute("allowanceBase", baseAmount);

        return "index";
    }
}