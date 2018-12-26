package com.example.kozel.battleship.Logic;

import java.util.ArrayList;

class ShipPlacer {

    private Board board;

    ShipPlacer(Board board) {
        this.board = board;
    }

    int[][] getShipsPlacements() {
        int[][] placements = new int[board.getDifficulty().getShipCount()][];
        int[][] availablePlaces = new int[board.getBoardSize()][board.getBoardSize()];
        for (int i = 0; i < board.getDifficulty().getShipCount(); i++) {
            placements[i] = null;
            while (placements[i] == null) {
                if (Math.random() < 0.5) {
                    placements[i] = tryToPlaceHorz(availablePlaces, getRandomSize());
                } else {
                    placements[i] = tryToPlaceVert(availablePlaces, getRandomSize());
                }
            }
        }
        return placements;
    }

    int[][] resetPlacements(ArrayList<Ship> shipsOnBoard, ArrayList<Ship> destroyedShips) {
        int[][] placements = new int[board.getDifficulty().getShipCount()][];
        int[][] availablePlaces = new int[board.getBoardSize()][board.getBoardSize()];

        for (int i = 0; i < destroyedShips.size(); i++) {
            placements[i] = destroyedShips.get(i).getShipPlacement();
            for (int j = 0; j < placements[i].length; j++) {
                availablePlaces[placements[i][j] / board.getBoardSize()][placements[i][j] % board.getBoardSize()] = 1;
            }
        }

        for(int i=0;i<shipsOnBoard.size();i++) {
            placements[i] = null;
            while (placements[i] == null) {
                if (Math.random() < 0.5) {
                    placements[i] = tryToPlaceHorz(availablePlaces, shipsOnBoard.get(i).getSize());
                } else {
                    placements[i] = tryToPlaceVert(availablePlaces, shipsOnBoard.get(i).getSize());
                }
            }
        }
        return placements;
    }

    int[][] getShipPlacements(ArrayList<Ship> destroyedShips) {
        int[][] placements = new int[board.getDifficulty().getShipCount()][];
        int[][] availablePlaces = new int[board.getBoardSize()][board.getBoardSize()];

        for (int i = 0; i < destroyedShips.size(); i++) {
            placements[i] = destroyedShips.get(i).getShipPlacement();
            for (int j = 0; j < placements[i].length; j++) {
                availablePlaces[placements[i][j] / board.getBoardSize()][placements[i][j] % board.getBoardSize()] = 1;
            }
        }

        for (int i = 0; i < board.getShipCount(); i++) {
            placements[i] = null;
            while (placements[i] == null) {
                if (Math.random() < 0.5) {
                    placements[i] = tryToPlaceHorz(availablePlaces, getRandomSize());
                } else {
                    placements[i] = tryToPlaceVert(availablePlaces, getRandomSize());
                }
            }
        }

        return placements;
    }

    private int getRandomSize() {
        int[] shipSizes = board.getDifficulty().getShipSizes();
        return (int) (Math.random() * (shipSizes[1] - shipSizes[0] + 1)) + shipSizes[0];
    }

    private int[] tryToPlaceHorz(int[][] available, int size) {
        int[] placement;
        int randomTileRow = (int) (Math.random() * board.getBoardSize());
        int randomTileCol;
        do {
            randomTileCol = (int) (Math.random() * board.getBoardSize());
        } while (randomTileCol + size > board.getBoardSize());
        placement = new int[size];

        for (int i = 0; i < size; i++) {
            if (available[randomTileRow][randomTileCol + i] != 0) {
                placement = null;
                break;
            }
        }
        if (placement != null) {
            for (int i = 0; i < size; i++) {
                available[randomTileRow][randomTileCol + i] = 1;
                placement[i] = randomTileRow * board.getBoardSize() + randomTileCol + i;
            }
        }
        return placement;
    }

    private int[] tryToPlaceVert(int[][] available, int size) {
        int[] placement;
        int randomTileCol = (int) (Math.random() * board.getBoardSize());
        int randomTileRow;
        do {
            randomTileRow = (int) (Math.random() * board.getBoardSize());
        } while (randomTileRow + size > board.getBoardSize());

        placement = new int[size];

        for (int i = 0; i < size; i++) {
            if (available[randomTileRow + i][randomTileCol] != 0) {
                placement = null;
                break;
            }
        }
        if (placement != null) {
            for (int i = 0; i < size; i++) {
                available[randomTileRow + i][randomTileCol] = 1;
                placement[i] = (randomTileRow + i) * board.getBoardSize() + randomTileCol;
            }
        }

        return placement;
    }
}
