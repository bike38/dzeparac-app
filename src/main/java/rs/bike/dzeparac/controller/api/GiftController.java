package rs.bike.dzeparac.controller.api;

import org.springframework.web.bind.annotation.*;
import rs.bike.dzeparac.model.Gift;
import rs.bike.dzeparac.repository.GiftRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/gifts")
public class GiftController {

    private final GiftRepository giftRepo;

    public GiftController(GiftRepository giftRepo) {
        this.giftRepo = giftRepo;
    }

    @GetMapping
    public List<Gift> getAllGifts() {
        return giftRepo.findAll();
    }

    @GetMapping("/child/{childId}/range")
    public List<Gift> getByChildAndWeek(@PathVariable Long childId,
                                        @RequestParam LocalDate start,
                                        @RequestParam LocalDate end) {
        return giftRepo.findByChildIdAndDateBetween(childId, start, end);
    }

    @PostMapping
    public Gift createGift(@RequestBody Gift gift) {
        return giftRepo.save(gift);
    }

    @DeleteMapping("/{id}")
    public void deleteGift(@PathVariable Long id) {
        giftRepo.deleteById(id);
    }
}