package rs.bike.dzeparac.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class HomeWebController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/scores")
    public String scores() {
        return "scores";
    }
}