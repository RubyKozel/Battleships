package com.example.kozel.battleship.Logic;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public enum TileState {
        HIT, MISS, NOT_FIRED;

        public String getStateImage() { // Should return Image for the state
            switch (this) {
                case HIT:
                    return "HIT pic";
                case MISS:
                    return "MISS pic";
                case NOT_FIRED:
                    return "";
                default:
                    return "";
            }
        }
    }

    private List<Ship> shipList = new ArrayList<>();
    private int shipCount;
    private Difficulty difficulty;
    private int size;
    private Tile[] theBoard;

    public Board(Difficulty difficulty) {
        this.shipCount = difficulty.getShipCount();
        this.size = difficulty.getSize();
        this.theBoard = new Tile[size * size];

    }

}


