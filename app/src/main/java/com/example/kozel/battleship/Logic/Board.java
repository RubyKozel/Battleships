package com.example.kozel.battleship.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    /**
     * Integer indicating the size of the board in one dimension
     */
    private int boardSize;

    /**
     * This map will contain for each tile, that it's a ship tile, the ship in that tile (for complexity purposes)
     */
    private Map<Tile, Ship> tileToShip;

    /**
     * This array will contain the amount of ships in each size
     * arr[0] = ships in size 1, arr[1] = ships in size 2 ... and so on
     */
    private int[] shipAmounts = new int[4];

    /**
     * Counts the number of ships in this board
     */
    private int shipCount;

    /**
     * The difficulty of the board, affects the number of ships and the size of the board
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
     * This class represents a board in the game. </br>
     * Given a difficulty, the class initializes a Tile[] array according to the difficulties aspects. </br>
     * It than changes the status of the tiles to visible or invisible according to the visibility parameter
     *
     * @param difficulty - the difficulty of the game
     * @param visibility - whether or not the ships in the board are visible to the player or not
     */
    Board(Difficulty difficulty, boolean visibility) {
        this.difficulty = difficulty;
        this.shipCount = difficulty.getShipCount();
        this.boardSize = difficulty.getSize();
        this.theBoard = new Tile[boardSize * boardSize];
        this.notChosenTiles = new ArrayList<>();
        this.tileToShip = new HashMap<>();
        for (int i = 0; i < theBoard.length; i++) {
            notChosenTiles.add(i);
            theBoard[i] = new Tile();
        }

        placeShips(visibility);
    }

    private void placeShips(boolean visibility) {
        int[][] placements = new ShipPlacer(this).getShipsPlacements();
        for (int i = 0; i < shipCount; i++) {
            shipAmounts[placements[i].length - 1]++;
            Ship s = new Ship(theBoard, placements[i]);
            for (int j : placements[i]) {
                tileToShip.put(theBoard[j], s);
                theBoard[j].setState(visibility ? TileState.VISIBLE : TileState.INVISIBLE);
            }
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
                    shipAmounts[shipInTile.getSize() - 1]--;
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

    int getBoardSize() {
        return boardSize;
    }

    public int getShipCount() {
        return shipCount;
    }

    public int[] getShipAmounts() {
        return shipAmounts;
    }

    ArrayList<Integer> getNotChosenTiles() {
        return notChosenTiles;
    }
}


