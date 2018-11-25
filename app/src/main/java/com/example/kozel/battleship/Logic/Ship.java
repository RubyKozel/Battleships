package com.example.kozel.battleship.Logic;

class Ship {
    private Tile[] board;
    private Integer[] shipPlacement;
    private int partsLeft;

    /**
     * Class to represent the ship.
     *
     * @param board         - the Tile array in which the ship is placed
     * @param shipPlacement - an Integer array contains the tile numbers the ship is to be placed
     */
    Ship(Tile[] board, Integer[] shipPlacement) {
        this.board = board;
        this.shipPlacement = shipPlacement;
        for (int i : shipPlacement)
            board[i].setNotEmpty();
        this.partsLeft = shipPlacement.length;
    }

    /**
     * Indicated whether the ship is destroyed or not
     *
     * @return true if partsLeft == 0
     */
    boolean isDestroyed() {
        return partsLeft == 0;
    }

    /**
     * Destroys the ship. changing all of its tile states to <b>TileState.DESTROYED</b>
     */
    void destroyShip() {
        for (int i : shipPlacement)
            board[i].setState(TileState.DESTROYED);
    }

    void setPartsLeft(int partsLeft) {
        this.partsLeft = partsLeft;
    }

    int getPartsLeft() {
        return partsLeft;
    }
}
