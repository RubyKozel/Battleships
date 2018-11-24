package com.example.kozel.battleship.Logic;

public class Ship {
    private Board board;
    private Integer[] shipPlacement;

    public Ship(Board board, Integer[] shipPlacement) {
        this.board = board;
        this.shipPlacement = shipPlacement;
        for (int j : shipPlacement)
            board.getTheBoard()[j].setEmpty(false);
    }

    public void destroyShip() {
        for (int i : shipPlacement)
            board.getTheBoard()[i].setState(TileState.DESTROYED);
    }
}
