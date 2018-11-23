package com.example.kozel.battleship.Logic;

public class Ship {
    private int numOfTiles;
    private boolean isDestroyed = false;

    public Ship(int numOfTiles){
        this.numOfTiles = numOfTiles;
    }

    public int getNumOfTiles() {
        return numOfTiles;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public void destroyShip() {
        this.isDestroyed = true;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }
}
