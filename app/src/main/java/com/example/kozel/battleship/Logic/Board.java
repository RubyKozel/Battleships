package com.example.kozel.battleship.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {

    private Map<Ship, Set<Tile>> shipMap = new HashMap<>();
    private Map<Tile, Ship> tileToShip = new HashMap<>();
    private int shipCount;
    private Difficulty difficulty;
    private Tile[] theBoard;
    private ArrayList<Integer> notChosenTiles;

    public Board(Difficulty difficulty, boolean visibility) {
        this.difficulty = difficulty;
        this.shipCount = difficulty.getShipCount();
        this.theBoard = new Tile[difficulty.getSize() * difficulty.getSize()];
        this.notChosenTiles = new ArrayList<>();
        for (int i = 0; i < theBoard.length; i++) {
            notChosenTiles.add(i);
            theBoard[i] = new Tile();
        }
        placeShips();
        setShipVisibility(visibility);

    }

    private void placeShips() {
        Thread t = new Thread(() -> {
            Integer[][] placements = difficulty.getShipsPlacements();
            for (int i = 0; i < shipCount; i++) {
                Ship s = new Ship(this, placements[i]);
                shipMap.put(s, new HashSet<>(getTilesForPlacements(placements[i])));
                for (int j : placements[i]) tileToShip.put(theBoard[j], s);
            }
        });

        t.start();
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
        return tileToShip.get(theBoard[tile]);
    }

    private boolean isShipDestroyed(Ship s) {
        for (Tile t : shipMap.get(s))
            if (t.getState() != TileState.HIT) return false;
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
        new Thread(() -> {
            for (Set<Tile> set : shipMap.values())
                for (Tile t : set)
                    t.setState(shipVisibility ? TileState.VISIBLE : TileState.INVISIBLE);
        }).start();
    }

    public ArrayList<Integer> getNotChosenTiles() {
        return notChosenTiles;
    }

    private List<Tile> getTilesForPlacements(Integer[] placements) {
        List<Tile> tileList = new ArrayList<>();
        for (int i : placements) tileList.add(theBoard[i]);
        return tileList;
    }

}


