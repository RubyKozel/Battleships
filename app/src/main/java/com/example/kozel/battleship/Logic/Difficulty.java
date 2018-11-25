package com.example.kozel.battleship.Logic;

public enum Difficulty {
    EASY, MEDIUM, HARD;

    /**
     * Method that returns the size of the board
     *
     * @return - size of the board according to the chosen difficulty
     */
    public int getSize() {
        switch (this) {
            case EASY:
                return 6;
            case MEDIUM:
                return 8;
            case HARD:
                return 12;
            default:
                return 0;
        }
    }

    /**
     * Method that returns the sizes of the tiles, according to the difficulty chosen
     *
     * @return - width and height of a single tile
     */
    public int[] getTileSize() {
        switch (this) {
            case EASY:
                return new int[]{135, 96};
            case MEDIUM:
                return new int[]{105, 71};
            case HARD:
                return new int[]{70, 45};
            default:
                return new int[]{0, 0};
        }
    }

    /**
     * Gets the ship count for the chosen difficulty
     *
     * @return - ship amount to be places in the board
     */
    public int getShipCount() {
        switch (this) {
            case EASY:
                return 5;
            case MEDIUM:
                return 6;
            case HARD:
                return 8;
            default:
                return 0;
        }
    }

    /**
     * Returns a hard coded ship placements for human board
     *
     * @return - a matrix containing the placements
     */
    public Integer[][] getShipsPlacements() {
        switch (this) {
            case EASY: {
                return new Integer[][]{
                        {0, 1, 2, 3},
                        {8, 9, 10, 11},
                        {12, 18, 24, 30},
                        {20, 26, 32},
                        {27, 28, 29}
                };
            }
            case MEDIUM: {
                return new Integer[][]{
                        {3, 4},
                        {14, 22},
                        {16, 17, 18},
                        {35, 36, 37},
                        {41, 49},
                        {53, 61}
                };
            }
            case HARD: {
                return new Integer[][]{
                        {13, 25},
                        {40, 41, 42},
                        {58, 59},
                        {90},
                        {96, 108, 120},
                        {111},
                        {104, 116, 128},
                        {131}
                };
            }
            default:
                return new Integer[0][0];
        }
    }

    /**
     * Returns a hard coded ship placements for the computer board
     *
     * @return - a matrix containing the placements
     */
    public Integer[][] getShipsPlacementsForComputer() {
        //TODO
        return null;
    }
}