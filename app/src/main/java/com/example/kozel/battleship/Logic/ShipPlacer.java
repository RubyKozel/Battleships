package com.example.kozel.battleship.Logic;

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
                    placements[i] = tryToPlaceHorz(availablePlaces);
                } else {
                    placements[i] = tryToPlaceVert(availablePlaces);
                }
            }
        }
        return placements;
    }

    private int[] tryToPlaceHorz(int[][] available) {
        int[] shipSizes = board.getDifficulty().getShipSizes();
        int[] placement;
        int randomTileRow = (int) (Math.random() * board.getBoardSize());
        int randomTileCol, randomSize;
        do {
            randomTileCol = (int) (Math.random() * board.getBoardSize());
            randomSize = (int) (Math.random() * (shipSizes[1] - shipSizes[0] + 1)) + shipSizes[0];
        } while (randomTileCol + randomSize > board.getBoardSize());
        placement = new int[randomSize];

        for (int i = 0; i < randomSize; i++) {
            if (available[randomTileRow][randomTileCol + i] != 0) {
                placement = null;
                break;
            }
        }
        if (placement != null) {
            for (int i = 0; i < randomSize; i++) {
                available[randomTileRow][randomTileCol + i] = 1;
                placement[i] = randomTileRow * board.getBoardSize() + randomTileCol + i;
            }
        }
        return placement;
    }

    private int[] tryToPlaceVert(int[][] available) {
        int[] shipSizes = board.getDifficulty().getShipSizes();
        int[] placement;
        int randomTileCol = (int) (Math.random() * board.getBoardSize());
        int randomTileRow, randomSize;
        do {
            randomTileRow = (int) (Math.random() * board.getBoardSize());
            randomSize = (int) (Math.random() * (shipSizes[1] - shipSizes[0] + 1)) + shipSizes[0];
        } while (randomTileRow + randomSize > board.getBoardSize());

        placement = new int[randomSize];

        for (int i = 0; i < randomSize; i++) {
            if (available[randomTileRow + i][randomTileCol] != 0) {
                placement = null;
                break;
            }
        }
        if (placement != null) {
            for (int i = 0; i < randomSize; i++) {
                available[randomTileRow + i][randomTileCol] = 1;
                placement[i] = (randomTileRow + i) * board.getBoardSize() + randomTileCol;
            }
        }

        return placement;
    }
}
