package com.example.kozel.battleship.Logic;

enum Difficulty {
    EASY, MEDIUM, HARD;
}

public class BattleshipController {
    private Board humanBoard = new Board();
    private Board computerBoard = new Board();
    private Player human = new Player();
    private Player computer = new ComputerPlayer();
}

