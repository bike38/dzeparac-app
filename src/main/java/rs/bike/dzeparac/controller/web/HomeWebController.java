package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class HomeWebController {

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("contentFragment", "index :: contentFragment");
        return "layout";
    }

    @GetMapping("/scores")
    public String scores(Model model) {
        model.addAttribute("contentFragment", "scores :: contentFragment");
        return "layout";
    }
}