package com.example.kozel.battleship.Logic;

public class Player {
    private boolean turn;

    public Player() {}

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
