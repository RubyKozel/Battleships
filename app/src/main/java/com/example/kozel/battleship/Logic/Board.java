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
     * A list that tracks the total ships left in the board
     */
    private ArrayList<Ship> shipsOnBoard;

    /**
     * A list tracking the destroyed ships in the game
     */
    private ArrayList<Ship> destroyedShips;

    /**
     * Saves the last tile marked status
     */
    private TileState lastTileState;

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
        this.destroyedShips = new ArrayList<>();
        this.shipsOnBoard = new ArrayList<>();
        this.tileToShip = new HashMap<>();

        initBoard();
        placeShips(visibility);
    }

    private void initBoard() {
        for (int i = 0; i < theBoard.length; i++) {
            notChosenTiles.add(i);
            theBoard[i] = new Tile();
        }
    }

    private void placeShips(boolean visibility) {
        int[][] placements = new ShipPlacer(this).getShipsPlacements();
        for (int i = 0; i < shipCount; i++)
            place(placements[i], visibility);
    }

    public void replaceShips(boolean visibility) {
        int[][] newPlacements = new ShipPlacer(this).resetPlacements(shipsOnBoard, destroyedShips);

        // removing old ship placements
        for (Ship s : shipsOnBoard)
            for (int i : s.getShipPlacement())
                tileToShip.remove(theBoard[i]);

        notChosenTiles.clear();

        for (int i = 0; i < theBoard.length; i++) {
            notChosenTiles.add(i);
        }

        //resetting the board, copying the old
        Tile[] oldBoard = new Tile[theBoard.length];
        for (int i = 0; i < theBoard.length; i++) {
            oldBoard[i] = new Tile(theBoard[i]);
            if (theBoard[i].getState() == TileState.DESTROYED) {
                notChosenTiles.remove(notChosenTiles.indexOf(i));
                continue;
            }
            theBoard[i] = new Tile();
        }

        //replacing ships
        for (int i = destroyedShips.size(); i < shipCount + destroyedShips.size(); i++)
            replace(newPlacements[i], shipsOnBoard.get(i - destroyedShips.size()), oldBoard, visibility);
    }

    private void place(int[] newPlacement, boolean visibility) {
        shipAmounts[newPlacement.length - 1]++;
        Ship s = new Ship(theBoard, newPlacement);
        shipsOnBoard.add(s);
        for (int j : newPlacement) {
            tileToShip.put(theBoard[j], s);
            theBoard[j].setState(visibility ? TileState.VISIBLE : TileState.INVISIBLE);
        }
    }

    private void replace(int[] newPlacement, Ship oldShip, Tile[] oldBoard, boolean visibility) {
        int[] oldPlacement = oldShip.getShipPlacement();
        oldShip.setShipPlacement(newPlacement);
        for (int i = 0; i < newPlacement.length; i++) {
            tileToShip.put(theBoard[newPlacement[i]], oldShip);
            if (oldBoard[oldPlacement[i]].getState() == TileState.HIT) {
                notChosenTiles.remove(notChosenTiles.indexOf(newPlacement[i]));
                theBoard[newPlacement[i]].setState(TileState.HIT);
            } else {
                theBoard[newPlacement[i]].setState(visibility ? TileState.VISIBLE : TileState.INVISIBLE);
            }
        }
    }

    public void hitRandomShips() {
        int numOfShipsToHit;
        if (shipsOnBoard.size() == 1)
            numOfShipsToHit = 1;
        else
            numOfShipsToHit = (int) (Math.random() * shipsOnBoard.size());
        for (int i = 0; i < numOfShipsToHit; i++) {
            for (int j : shipsOnBoard.get(i).getShipPlacement()) {
                if (theBoard[j].isNotAlreadyChosen()) {
                    markTile(j);
                    notChosenTiles.remove(notChosenTiles.indexOf(j));
                    break;
                }
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
                lastTileState = TileState.MISS;
            } else {
                theBoard[tile].setState(TileState.HIT);
                lastTileState = TileState.HIT;
                Ship shipInTile = getShipInTile(tile);
                shipInTile.setPartsLeft(shipInTile.getPartsLeft() - 1);
                if (shipInTile.isDestroyed()) {
                    shipCount--;
                    shipAmounts[shipInTile.getSize() - 1]--;
                    shipInTile.destroyShip();
                    shipsOnBoard.remove(shipInTile);
                    destroyedShips.add(shipInTile);
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

    public ArrayList<Integer> getNotChosenTiles() {
        return notChosenTiles;
    }

    ArrayList<Ship> getDestroyedShips() {
        return destroyedShips;
    }

    public TileState getLastTileState() { return lastTileState; }
}


