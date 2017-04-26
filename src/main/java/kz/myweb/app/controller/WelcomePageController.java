package kz.myweb.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomePageController {

    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("greeting", "Welcome to tic tac toe game");
        model.addAttribute("tagline", "Game for 2 players. You can create your own game or join to someone");
        model.addAttribute("createdGames", GameController.getCreatedGames());
        return "welcome";
    }
}
