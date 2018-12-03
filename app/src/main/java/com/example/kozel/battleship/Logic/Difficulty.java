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
                return 5;
            case MEDIUM:
                return 7;
            case HARD:
                return 10;
            default:
                return 0;
        }
    }

    public int[] getShipSizes() {
        switch (this) {
            case EASY:
                return new int[]{3, 4};
            case MEDIUM:
                return new int[]{2, 3};
            case HARD:
                return new int[]{1, 3};
            default:
                return new int[0];
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
                return 4;
            case MEDIUM:
                return 5;
            case HARD:
                return 7;
            default:
                return 0;
        }
    }
}