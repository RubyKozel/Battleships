package com.example.kozel.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kozel.battleship.Logic.Difficulty;

public class MainActivity extends AppCompatActivity {

    private Button start_game;
    private Button easy;
    private Button medium;
    private Button hard;

    private Bundle b;
    private Intent intent;

    public final static String DIFFICULTY_KEY = "DIFFICULTY";
    public final static String BUNDLE_KEY = "BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_game = findViewById(R.id.start_game_button);
        easy = findViewById(R.id.easy_button);
        medium = findViewById(R.id.medium_button);
        hard = findViewById(R.id.hard_button);

        easy.setVisibility(View.INVISIBLE);
        medium.setVisibility(View.INVISIBLE);
        hard.setVisibility(View.INVISIBLE);

        intent = new Intent(MainActivity.this, BoardsActivity.class);

        b = new Bundle();
    }

    public void onStartGameClick(View view) {
        runOnUiThread(() -> {
            start_game.setVisibility(View.INVISIBLE);
            easy.setVisibility(View.VISIBLE);
            medium.setVisibility(View.VISIBLE);
            hard.setVisibility(View.VISIBLE);
        });
    }

    public void onEasyClick(View view) {
        b.putInt(DIFFICULTY_KEY, Difficulty.EASY.ordinal());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }

    public void onMediumClick(View view) {
        b.putInt(DIFFICULTY_KEY, Difficulty.MEDIUM.ordinal());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }

    public void onHardClick(View view) {
        b.putInt(DIFFICULTY_KEY, Difficulty.HARD.ordinal());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
    }
}
