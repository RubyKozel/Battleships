package com.example.kozel.battleship.Logic;

import java.util.ArrayList;

public class BattleshipController {
    private Board humanBoard;
    private Board computerBoard;
    private Player human = new Player();
    private ComputerPlayer computer = new ComputerPlayer();

    public BattleshipController(Difficulty difficulty) {
        Thread t1 = new Thread(() -> humanBoard = new Board(difficulty, true));
        Thread t2 = new Thread(() -> computerBoard = new Board(difficulty, false));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        human.setTurn(true);
    }

    private void switchTurn() {
        human.setTurn(!human.isTurn());
        computer.setTurn(!computer.isTurn());
    }

    public boolean humanPlay(int position) {
        if (human.isTurn()) {
            if (computerBoard.markTile(position)) {
                switchTurn();
                return true;
            }
        }
        return false;
    }

    public int computerPlay() {
        if (computer.isTurn()) {
            ArrayList<Integer> list = humanBoard.getNotChosenTiles();
            int tile = list.remove(computer.think(list.size()));
            humanBoard.markTile(tile);
            switchTurn();
            return tile;
        }
        return -1;
    }


    public boolean checkIfSomeoneWon(int shipcount) {
        return shipcount==0;
    }

    public Board getComputerBoard() {
        return computerBoard;
    }

    public Board getHumanBoard() {
        return humanBoard;
    }

    public Player getHuman() {
        return human;
    }
}