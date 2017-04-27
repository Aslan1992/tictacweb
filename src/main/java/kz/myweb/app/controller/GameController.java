package kz.myweb.app.controller;

import kz.myweb.app.domain.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GameController {

    private static List<Game> createdGames = new ArrayList<>();
    private static final String IN_PROGRESS = "in progress";
    private static final String WAITING = "waiting";
    private Map<String, String> roles = new HashMap<>();

    public static List<Game> getCreatedGames() {
        return createdGames;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newGame() {
        return "createGame";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processNewGame(@RequestParam String name, Model model) {
        createdGames.add(new Game(name, WAITING));
        model.addAttribute("gameName", name);
        model.addAttribute("player", "creator");
        return "game";
    }

    @RequestMapping(value = "/connectToGame", method = RequestMethod.GET)
    public String connectToGame(@RequestParam String name, Model model) {
        createdGames.stream()
                .filter(game -> game.getName().equalsIgnoreCase(name))
                .forEach(game -> game.setStatus(IN_PROGRESS));
        model.addAttribute("player", "incomer");
        return "game";
    }

    @RequestMapping(value = "/currentGameProcessInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentGameProcessInfo(@RequestParam String name) {
        String response = "";
        for (Game game : createdGames) {
            if (name.equalsIgnoreCase(game.getName())) {
                response = "Game: " + game.getName() + "; Status: " + game.getStatus();
            }
        }
        return response;
    }

    @RequestMapping("/whoMadeStep")
    @ResponseBody
    public String whoMadeStep(@RequestParam String name) {
        return roles.get(name);
    }

    @RequestMapping(value = "/toServer", method = RequestMethod.POST)
    @ResponseBody
    public String toServer(@RequestBody Game gameObjectFromPage, @RequestParam String playerRole) {
        for (Game game : createdGames) {
            if (gameObjectFromPage.getName().equalsIgnoreCase(game.getName())) {
                game.setState(gameObjectFromPage.getState());
                game.checkForGameover();
                roles.put(game.getName(), playerRole);

            }
        }
        return "success";
    }

    @RequestMapping(value = "/fromServer", method = RequestMethod.GET)
    @ResponseBody
    public Game fromServer(@RequestParam String name) {
        for (Game game : createdGames) {
            if(name.equalsIgnoreCase(game.getName())) {
                if (game.getState() == null) {
                    game.setState(getEmptyArray());
                }
                return game;
            }
        }
        return null;
    }

    private String[][] getEmptyArray() {
        String [][] a = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = "";
            }
        }
        return a;
    }
}