package com.example.kozel.battleship.Logic;

public class Ship {
    private int numOfTiles;
    private boolean isDestroyed = false;
    private int[] placementInBoard;

    public Ship(int numOfTiles, int[] placementInBoard) {
        this.numOfTiles = numOfTiles;
        this.placementInBoard = placementInBoard;
    }

    public void setNumOfTiles(int numOfTiles) {
        this.numOfTiles = numOfTiles;
    }

    public int getNumOfTiles() {
        return numOfTiles;
    }

    public void destroyShip() {
        this.isDestroyed = true;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public int[] getPlacementInBoard() {
        return placementInBoard;
    }

    public void setPlacementInBoard(int[] placementInBoard) {
        this.placementInBoard = placementInBoard;
    }
}
