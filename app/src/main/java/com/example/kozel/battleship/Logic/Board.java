package com.example.kozel.battleship.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {

    /**
     * This map will contain all the ships in the board and their tile sets
     */
    private Map<Ship, Set<Tile>> shipMap;

    /**
     * This map will contain for each tile, that it's a ship tile, the ship in that tile (for complexity purposes)
     */
    private Map<Tile, Ship> tileToShip;

    /**
     * Counts the number of ships in this board
     */
    private int shipCount;

    /**
     * The difficulty of the board, affects the number of ships, the size of the board and their placements
     */
    private Difficulty difficulty;

    /**
     * The actual board, represented with a Tile array
     */
    private Tile[] theBoard;

    /**
     * A list that will keep track of all the not chosen tiles in the board (for complexity purposes)
     */
    private ArrayList<Integer> notChosenTiles;

    /**
     * This class repressents a board in the game. </br>
     * Given a difficulty, the class initializes a Tile[] array according to the difficulties aspects. </br>
     * It than changes the status of the tiles to visible or invisible according to the visibility parameter
     *
     * @param difficulty - the difficulty of the game
     * @param visibility - whether or not the ships in the board are visible to the player or not
     */
    Board(Difficulty difficulty, boolean visibility) {
        this.difficulty = difficulty;
        this.shipCount = difficulty.getShipCount();
        this.theBoard = new Tile[difficulty.getSize() * difficulty.getSize()];
        this.notChosenTiles = new ArrayList<>();
        this.shipMap = new HashMap<>();
        this.tileToShip = new HashMap<>();
        for (int i = 0; i < theBoard.length; i++) {
            notChosenTiles.add(i);
            theBoard[i] = new Tile();
        }
        placeShips();
        setShipVisibility(visibility);
    }

    private void placeShips() {
        Integer[][] placements = difficulty.getShipsPlacements();
        for (int i = 0; i < shipCount; i++) {
            Ship s = new Ship(getTheBoard(), placements[i]);
            shipMap.put(s, new HashSet<>(getTilesForPlacements(placements[i])));
            for (int j : placements[i]) tileToShip.put(theBoard[j], s);
        }
    }

    /**
     * Given a tile that is yet to be marked, the method will mark the tile and set its status
     * Furthermore, this method will destroy ships that all their tiles were hit
     *
     * @param tile - the tile to be marked
     * @return true if the tile was marked, false if the tile is already marked and wasn't marked
     */
    boolean markTile(int tile) {
        if (theBoard[tile].isNotAlreadyChosen()) {
            if (theBoard[tile].isEmpty()) {
                theBoard[tile].setState(TileState.MISS);
            } else {
                theBoard[tile].setState(TileState.HIT);
                Ship shipInTile = getShipInTile(tile);
                shipInTile.setPartsLeft(shipInTile.getPartsLeft() - 1);
                if (shipInTile.isDestroyed()) {
                    shipCount--;
                    shipInTile.destroyShip();
                }
            }
            return true;
        }
        return false;
    }

    private Ship getShipInTile(int tile) {
        return tileToShip.get(theBoard[tile]);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Tile getTile(int position) {
        return theBoard[position];
    }

    private Tile[] getTheBoard() {
        return theBoard;
    }

    private void setShipVisibility(boolean shipVisibility) {
        for (Set<Tile> set : shipMap.values())
            for (Tile t : set)
                t.setState(shipVisibility ? TileState.VISIBLE : TileState.INVISIBLE);
    }

    ArrayList<Integer> getNotChosenTiles() {
        return notChosenTiles;
    }

    private List<Tile> getTilesForPlacements(Integer[] placements) {
        List<Tile> tileList = new ArrayList<>();
        for (int i : placements) tileList.add(theBoard[i]);
        return tileList;
    }

}


