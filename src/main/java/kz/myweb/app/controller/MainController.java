package kz.myweb.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {

    private String[] boardState;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("greeting", "Welcome to tit tac web game");
        return "index";
    }

    @RequestMapping("/game")
    public String gamePage(Model model) {
        model.addAttribute("msg", "Game has been started");
        return "game-page";
    }

    @RequestMapping(value = "/sendBoardStateToServer", method = RequestMethod.POST, consumes = "application/json")
    public void updateGlobalState(@RequestBody String[] state) {
        System.out.println("SEE HERE");
        System.out.println(state);
    }

    @RequestMapping(value = "/checkBoardStateForUpdate", method = RequestMethod.GET)
    @ResponseBody
    public String[] check() {
        return boardState;
    }

}
