package com.example.kozel.battleship.Logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    private Map<Ship, Set<Integer>> shipMap = new HashMap<>();
    private int shipCount;
    private Difficulty difficulty;
    private Tile[] theBoard;
    private ArrayList<Integer> notChosenTiles;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.shipCount = difficulty.getShipCount();
        this.theBoard = new Tile[difficulty.getSize() * difficulty.getSize()];
        this.notChosenTiles = new ArrayList<>();
        for (int i = 0; i < theBoard.length; i++) {
            notChosenTiles.add(i);
            theBoard[i] = new Tile();
        }
        placeShips();

    }

    private void placeShips() {
        Integer[][] placements = difficulty.getShipsPlacements();
        for (int i = 0; i < shipCount; i++)
            shipMap.put(new Ship(this, placements[i]),
                    new HashSet<Integer>(Arrays.asList(placements[i])));

    }

    public boolean allShipsDestroyed() {
        return shipCount == 0;
    }

    public boolean markTile(int tile) {
        if (theBoard[tile].isNotAlreadyChosen()) {
            if (theBoard[tile].isEmpty()) {
                theBoard[tile].setState(TileState.MISS);
            } else {
                theBoard[tile].setState(TileState.HIT);
                if (isShipDestroyed(getShipInTile(tile))) shipCount--;
            }
            return true;
        }
        return false;
    }

    private Ship getShipInTile(int tile) {
        for (Map.Entry<Ship, Set<Integer>> entry : shipMap.entrySet()) {
            if (entry.getValue().contains(tile))
                return entry.getKey();
        }
        return null;
    }

    private boolean isShipDestroyed(Ship s) {
        for (int i : shipMap.get(s))
            if (theBoard[i].getState() != TileState.HIT) return false;
        s.destroyShip();
        return true;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Tile getTile(int position) {
        return theBoard[position];
    }

    public Tile[] getTheBoard() {
        return theBoard;
    }

    public void setShipVisibility(boolean shipVisibility) {
        for (Set<Integer> set : shipMap.values())
            for (int i : set)
                theBoard[i].setState(shipVisibility ? TileState.VISIBLE : TileState.INVISIBLE);
    }

    public ArrayList<Integer> getNotChosenTiles() {
        return notChosenTiles;
    }


}


