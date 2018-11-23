package com.example.kozel.battleship.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Board {

    private List<Ship> shipList = new ArrayList<>();
    private int shipCount;
    private Difficulty difficulty;
    private Tile[] theBoard;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.shipCount = difficulty.getShipCount();
        this.theBoard = new Tile[difficulty.getSize() * difficulty.getSize()];
        for (Tile t : theBoard) t.setEmpty(true);
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
        for(Ship s : shipList)
            if(!s.isDestroyed())
                return false;
        return true;
    }

    public boolean markTile(int tile, Tile.TileState state) {
        if(theBoard[tile].getState() == Tile.TileState.NOT_FIRED){
            theBoard[tile].setState(state);
            return true;
        }
        return false;
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
}


