package com.example.kozel.battleship.Logic;

public enum Difficulty {
    EASY, MEDIUM, HARD;

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

    public int[][] getShipsPlacements() {
        switch (this) {
            case EASY: {
                return new int[][]{
                        {0, 1, 2, 3},
                        {8, 9, 10, 11},
                        {12, 18, 24, 30},
                        {20, 26, 32},
                        {27, 28, 29}
                };
            }
            case MEDIUM: {
                return new int[][]{
                        {3, 4},
                        {14, 22},
                        {16, 17, 18},
                        {35, 36, 37},
                        {41, 49},
                        {53, 61}
                };
            }
            case HARD: {
                return new int[][]{
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
                return new int[0][0];
        }
    }
}