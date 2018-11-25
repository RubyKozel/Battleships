package com.example.kozel.battleship.Logic;

import java.util.ArrayList;

public class BattleshipController {
    private Board humanBoard;
    private Board computerBoard;
    private Player human = new Player();
    private ComputerPlayer computer = new ComputerPlayer();

    public BattleshipController(Difficulty difficulty) {
        humanBoard = new Board(difficulty, true);
        computerBoard = new Board(difficulty, false);
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

    public void computerPlay() {
        if (computer.isTurn()) {
            ArrayList<Integer> list = humanBoard.getNotChosenTiles();
            humanBoard.markTile(list.remove(computer.think(list.size())));
            switchTurn();
        }
    }


    public void checkIfSomeoneWon() {
        //TODO - SLAVA
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

