package com.example.kozel.battleship.Logic;

import android.util.Log;

public class ComputerPlayer extends Player {

    public ComputerPlayer() {
    }

    public void think() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException r) {
            r.printStackTrace();
        }
    }
}
