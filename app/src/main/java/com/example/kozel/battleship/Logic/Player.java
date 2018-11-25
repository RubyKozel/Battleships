package com.example.kozel.battleship.Logic;

public class Player {
    private boolean turn;

    /**
     * A player java bean class, keep tracks of the turn of the player
     */
    Player() {
    }

    public boolean isTurn() {
        return turn;
    }

    void setTurn(boolean turn) {
        this.turn = turn;
    }
}
