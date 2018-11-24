package com.example.kozel.battleship.Logic;

import android.content.SyncStatusObserver;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Ship> shipList = new ArrayList<>();
    private int shipCount;
    private Difficulty difficulty;
    private Tile[] theBoard;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.shipCount = difficulty.getShipCount();
        this.theBoard = new Tile[difficulty.getSize() * difficulty.getSize()];
        for (int i = 0; i < theBoard.length; i++)
            theBoard[i] = new Tile();
        placeShips();

    }

    private void placeShips() {
        int[][] placements = difficulty.getShipsPlacements();
        for (int i = 0; i < shipCount; i++) {
            int[] ship = placements[i];
            shipList.add(new Ship(ship.length, ship));
            for (int j : ship)
                theBoard[j].setEmpty(false);
        }
    }

    public boolean isAllShipsDestroyed() {
        for (Ship s : shipList)
            if (!s.isDestroyed())
                return false;
        return true;
    }

    public boolean markTile(int tile) {
        if (theBoard[tile].getState() == TileState.NOT_FIRED) {
            if (theBoard[tile].isEmpty())
                theBoard[tile].setState(TileState.MISS);
            else {
                theBoard[tile].setState(TileState.HIT);
                Ship s = getShipInTile(tile);
                if (shipIsDestroyed(s))
                    s.destroyShip();
            }
            return true;
        }
        return false;
    }

    private Ship getShipInTile(int tile) {
        for (Ship s : shipList) {
            for (int i : s.getPlacementInBoard())
                if (i == tile)
                    return s;
        }
        return null;
    }

    private boolean shipIsDestroyed(Ship s) {
        for (int i : s.getPlacementInBoard())
            if (theBoard[i].getState() == TileState.NOT_FIRED)
                return false;
        return true;
    }

    public Tile[] getTheBoard() {
        return theBoard;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<Ship> getShipList() {
        return shipList;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    public void setShipList(List<Ship> shipList) {
        this.shipList = shipList;
    }

    public void setTheBoard(Tile[] theBoard) {
        this.theBoard = theBoard;
    }

    public int getShipCount() {
        return shipCount;
    }

    public Tile getTile(int position) {
        return theBoard[position];
    }
}


