package com.example.kozel.battleship.Logic;

public class Tile {

    private TileState state;
    private boolean isEmpty;

    public Tile() {
        state = TileState.INVISIBLE;
        isEmpty = true;
    }

    public void setState(TileState state) {
        this.state = state;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public TileState getState() {
        return state;
    }

    public boolean isNotAlreadyChosen() {
        return this.getState() != TileState.MISS
                && this.getState() != TileState.HIT
                && this.getState() != TileState.DESTROYED;
    }
}

