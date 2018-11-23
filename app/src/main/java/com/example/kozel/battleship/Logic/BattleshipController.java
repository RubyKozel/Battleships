package com.example.kozel.battleship.Logic;

enum Difficulty {
    EASY, MEDIUM, HARD;

    public int getSize() {
        switch (this) {
            case EASY:
                return 6;
            case MEDIUM:
                return 8;
            case HARD:
                return 12;
            default:
                return 0;
        }
    }

    public int getShipCount() {
        switch (this) {
            case EASY:
                return 5;
            case MEDIUM:
                return 6;
            case HARD:
                return 8;
            default:
                return 0;
        }
    }
}

public class BattleshipController {
    private Board humanBoard;
    private Board computerBoard;
    private Player human = new Player(1);
    private Player computer = new ComputerPlayer(2);
}

