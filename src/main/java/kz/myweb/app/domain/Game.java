package kz.myweb.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.myweb.app.mainlogic.Board;

public class Game {

    private String name;

    private String status;

    @JsonProperty("state")
    private String[][] state;

    @JsonProperty("victoryItems")
    private Item[] victoryItems;

    private Board board = new Board();

    public Game() {}

    public Game(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[][] getState() {
        return state;
    }

    public void setState(String[][] state) {
        this.state = state;
    }

    public Item[] getVictoryItems() {
        return victoryItems;
    }

    public void setVictoryItems(Item[] victoryItems) {
        this.victoryItems = victoryItems;
    }

    public void checkForGameover() {
        board.setState(state);
        victoryItems = board.getVictoryItems();
    }
}
