package com.example.kozel.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kozel.battleship.Logic.BattleshipController;
import com.example.kozel.battleship.Logic.Difficulty;

public class BoardsActivity extends AppCompatActivity {

    private BattleshipController controller;
    private Difficulty difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

        GridView humanView = findViewById(R.id.human_grid);
        GridView computerView = findViewById(R.id.computer_grid);

        Intent intent = getIntent();

        Bundle b = intent.getBundleExtra(MainActivity.BUNDLE_KEY);

        if (b != null) {
            difficulty = Difficulty.values()[b.getInt(MainActivity.DIFFICULTY_KEY)];
            controller = new BattleshipController(difficulty);
        }

        humanView.setNumColumns(difficulty.getSize());
        humanView.setAdapter(new TileAdapter(getApplicationContext(), controller.getHumanBoard()));

        computerView.setNumColumns(difficulty.getSize());
        computerView.setAdapter(new TileAdapter(getApplicationContext(), controller.getComputerBoard()));

        computerView.setOnItemClickListener((parent, view, position, id) -> {
            if (controller.getHuman().isTurn()) {

                // Human turn
                new Thread(() -> {
                    if (controller.humanPlay(position)) {
                        runOnUiThread(() -> {
                            ((TileAdapter) computerView.getAdapter()).notifyDataSetChanged();
                            ((TextView) findViewById(R.id.turnView)).setText(R.string.computer_turn_display);
                            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                        });
                    }
                }).start();

                // Computer turn
                new Thread(() -> {
                    controller.computerPlay();
                    runOnUiThread(() -> {
                        ((TileAdapter) humanView.getAdapter()).notifyDataSetChanged();
                        ((TextView) findViewById(R.id.turnView)).setText(R.string.human_turn_display);
                        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    });
                }).start();
            }
        });
    }
}
