package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Child;
import rs.bike.dzeparac.model.Gift;
import rs.bike.dzeparac.repository.ChildRepository;
import rs.bike.dzeparac.repository.GiftRepository;

import java.util.List;
import java.time.LocalDate;

@Controller
@RequestMapping("web/gifts")
public class GiftWebController {

    private final GiftRepository giftRepo;
    private final ChildRepository childRepo;

    public GiftWebController(GiftRepository giftRepo, ChildRepository childRepo) {
        this.giftRepo = giftRepo;
        this.childRepo = childRepo;
    }

    @GetMapping
    public String showGiftForm(Model model) {
        model.addAttribute("gifts", giftRepo.findAll());
        model.addAttribute("children", childRepo.findAll());
        return "gifts";
    }

    @PostMapping
    public String submitGift(@RequestParam Long childId,
                             @RequestParam String source,
                             @RequestParam int amount,
                             @RequestParam(required = false) String note,
                             @RequestParam String date) {
        Child child = childRepo.findById(childId).orElse(null);
        if (child == null) return "redirect:/gifts";

        Gift gift = new Gift();
        gift.setChild(child);
        gift.setSource(source);
        gift.setAmount(amount);
        gift.setNote(note);
        gift.setDate(LocalDate.parse(date));
        giftRepo.save(gift);

        return "redirect:/gifts";
    }
}