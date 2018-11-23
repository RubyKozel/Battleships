package com.example.kozel.battleship.Logic;

public class Tile {

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

    private TileState state = TileState.NOT_FIRED;
    private boolean isEmpty;

    public void setState(TileState state) {
        this.state = state;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public TileState getState() {
        return state;
    }
}

