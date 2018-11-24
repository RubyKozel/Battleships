package com.example.kozel.battleship.Logic;

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