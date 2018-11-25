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
        if (human.isTurn()) {
            computer.setTurn(true);
            human.setTurn(false);
        } else {
            computer.setTurn(false);
            human.setTurn(true);
        }

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

    public void computerPlay() {
        if (computer.isTurn()) {
            computer.think();
            new Thread(() -> {
                ArrayList<Integer> list = humanBoard.getNotChosenTiles();
                int tile = humanBoard.getNotChosenTiles().remove((int) (Math.random() * list.size()));
                if (humanBoard.markTile(tile))
                    switchTurn();
            }).start();
        }
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

