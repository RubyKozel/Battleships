package com.example.kozel.battleship.Logic;

import com.example.kozel.battleship.R;

public enum TileState {
    HIT, MISS, INVISIBLE, VISIBLE, DESTROYED;

    /**
     * Given a state, this method will return an ImageView containing the image of the chosen state
     *
     * @return - the ImageView represents the state
     */
    public int getStateImage() { // Should return Image for the state
        switch (this) {
            case HIT:
                return R.drawable.hit;
            case MISS:
                return R.drawable.miss;
            case DESTROYED:
                return R.drawable.skull_big;
            case VISIBLE:
                return 0;
            case INVISIBLE:
            default:
                return 1;
        }
    }
}