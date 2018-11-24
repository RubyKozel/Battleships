package com.example.kozel.battleship.Logic;

public class BattleshipController {
    private Board humanBoard;
    private Board computerBoard;
    private Player human = new Player(1);
    private ComputerPlayer computer = new ComputerPlayer(2);
    private int turn;
    private boolean isGameOver = false;

    public BattleshipController(Difficulty difficulty) {
        humanBoard = new Board(difficulty);
        computerBoard = new Board(difficulty);
        turn = 1; // human
    }

    public void switchTurn() {
        turn = turn == 1 ? 2 : 1;
    }

    public Board getComputerBoard() {
        return computerBoard;
    }

    public Board getHumanBoard() {
        return humanBoard;
    }
}

