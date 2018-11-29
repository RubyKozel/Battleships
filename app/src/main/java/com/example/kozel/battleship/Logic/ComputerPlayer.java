package com.example.kozel.battleship.Logic;

import android.util.Log;

class ComputerPlayer extends Player {

    ComputerPlayer() {
    }

    int think(int size) {
        int num = (int) (Math.random() * size);
        try {
            Thread.sleep(4500);
        } catch (InterruptedException r) {
            r.printStackTrace();
        }
        return num;
    }
}
