package com.example.kozel.battleship.Logic;

public class Tile {

    private TileState state;
    private boolean isEmpty;

    /**
     * A bean class represents a single tile in the board.</br>
     * Each tile has a state and isEmpty status
     */
    Tile() {
        state = TileState.INVISIBLE;
        isEmpty = true;
    }

    void setState(TileState state) {
        this.state = state;
    }

    boolean isEmpty() { return isEmpty; }

    void setNotEmpty() {
        this.isEmpty = false;
    }

    public TileState getState() {
        return state;
    }

    /**
     * Checks if this tile as already been chosen by the player
     *
     * @return - true if and only if this TileState is not MISS, HIT or DESTROYED
     */
    boolean isNotAlreadyChosen() {
        return this.getState() != TileState.MISS
                && this.getState() != TileState.HIT
                && this.getState() != TileState.DESTROYED;
    }
}

