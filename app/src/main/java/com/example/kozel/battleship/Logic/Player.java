package com.example.kozel.battleship.Logic;

public class Player {
    private int playerNumber;
    private boolean turn;

    public Player() {}

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
