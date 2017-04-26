package kz.myweb.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

    @JsonProperty("i")
    private int i;
    @JsonProperty("j")
    private int j;

    public Item() {
    }

    public Item(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public String toString() {
        return "Item{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
