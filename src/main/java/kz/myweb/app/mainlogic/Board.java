package kz.myweb.app.mainlogic;

import kz.myweb.app.domain.Item;

public class Board {
    public static final int BOARD_SIZE = 3;
    private static final int CENTER = 1;
    private String[][] state;
    public static final String EMPTY_ITEM = "";
    public static final String CROSS_ITEM = "X";
    public static final String ZERO_ITEM = "O";

    public void init() {
        state = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                state[i][j] = EMPTY_ITEM;
            }
        }
    }

    public String[][] getState() {
        return state;
    }

    public void setState(String[][] state) {
        this.state = state;
    }

    public void setValue(int i, int j, String value) {
        state[i][j] = value;
    }

    public String getValue(int i, int j) {
        return state[i][j];
    }

    private Item[] findHorizontalVictory() {
        Item[] result = null;
        int j = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (!state[i][j].equals(EMPTY_ITEM)) {
                String itemValue = state[i][j];
                if (itemValue.equals(state[i][j + 1]) && itemValue.equals(state[i][j + 2])) {
                    result = new Item[BOARD_SIZE];
                    int k = 0;
                    while (k < BOARD_SIZE) {
                        result[k] = new Item(i, k);
                        k++;
                    }
                    return result;
                }
            }
        }
        return result;
    }

    private Item[] findVerticalVictory() {
        Item[] result = null;
        int i = 0;
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (!state[i][j].equals(EMPTY_ITEM)) {
                String itemValue = state[i][j];
                if (itemValue.equals(state[i + 1][j]) && itemValue.equals(state[i + 2][j])) {
                    result = new Item[BOARD_SIZE];
                    int k = 0;
                    while (k < BOARD_SIZE) {
                        result[k] = new Item(k, j);
                        k++;
                    }
                    return result;
                }
            }
        }
        return result;
    }

    private Item[] findDiagonalVictory() {
        Item[] result = null;
        if (state[CENTER][CENTER].equals(EMPTY_ITEM)) {
            return result;
        } else {
            String itemValue = state[CENTER][CENTER];
            if (itemValue.equals(state[CENTER - 1][CENTER - 1]) && itemValue.equals(state[CENTER + 1][CENTER + 1])) {
                result = new Item[BOARD_SIZE];
                result[0] = new Item(CENTER - 1, CENTER - 1);
                result[1] = new Item(CENTER, CENTER);
                result[2] = new Item(CENTER + 1, CENTER + 1);
            } else if (itemValue.equals(state[CENTER - 1][CENTER + 1]) && itemValue.equals(state[CENTER + 1][CENTER - 1])) {
                result = new Item[BOARD_SIZE];
                result[0] = new Item(CENTER - 1, CENTER + 1);
                result[1] = new Item(CENTER, CENTER);
                result[2] = new Item(CENTER + 1, CENTER - 1);

            }
        }
        return result;
    }

    public Item[] getVictoryItems() {
        Item[] hVictory = findHorizontalVictory();
        Item[] vVictory = findVerticalVictory();
        Item[] dVictory = findDiagonalVictory();
        if (hVictory != null) {
            return hVictory;
        }
        if (vVictory != null) {
            return vVictory;
        }
        if (dVictory != null) {
            return dVictory;
        }
        return null;
    }
}
