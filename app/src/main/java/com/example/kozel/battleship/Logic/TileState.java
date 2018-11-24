package com.example.kozel.battleship.Logic;

public enum TileState {
    HIT, MISS, INVISIBLE, VISIBLE, DESTROYED;

    public String getStateImage() { // Should return Image for the state
        switch (this) {
            case HIT:
                return "HIT";
            case MISS:
                return "MISS";
            case VISIBLE:
                return "X";
            case INVISIBLE:
            case DESTROYED:
            default:
                return "";
        }
    }
}