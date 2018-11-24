package com.example.kozel.battleship.Logic;

import java.util.ArrayList;

public class BattleshipController {
    private Board humanBoard;
    private Board computerBoard;
    private Player human = new Player();
    private ComputerPlayer computer = new ComputerPlayer();

    public BattleshipController(Difficulty difficulty) {
        humanBoard = new Board(difficulty);
        humanBoard.setShipVisibility(true);
        computerBoard = new Board(difficulty);
        computerBoard.setShipVisibility(false);
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

    public boolean computerPlay() {
        if (computer.isTurn()) {
            computer.think();
            ArrayList<Integer> list = humanBoard.getNotChosenTiles();
            int tile = humanBoard.getNotChosenTiles().remove((int) (Math.random() * list.size()));
            if (humanBoard.markTile(tile)) {
                switchTurn();
                return true;
            }
        }
        return true;
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

