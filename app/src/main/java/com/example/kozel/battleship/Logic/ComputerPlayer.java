package com.example.kozel.battleship.Logic;

public class ComputerPlayer extends Player {

    ComputerPlayer(int playerNumber) {
        super(playerNumber);
    }

    public void think() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException r) {
                r.printStackTrace();
            }
        });
    }
}
