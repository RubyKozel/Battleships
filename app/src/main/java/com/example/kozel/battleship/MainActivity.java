package com.example.kozel.battleship;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.kozel.battleship.Logic.Difficulty;

public class MainActivity extends AppCompatActivity implements
        ChooseDifficultyFragment.onButtonClickedListener,
        AppStartFragment.onButtonClickedListener,
        HighScoresFragment.onButtonClickedListener {

    private ImageButton start_game;
    private ImageButton easy;
    private ImageButton medium;
    private ImageButton hard;

    private Bundle b;
    private Intent intent;

    public final static String DIFFICULTY_KEY = "DIFFICULTY";
    public final static String BUNDLE_KEY = "BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AppStartFragment())
                .addToBackStack(null)
                .commit();

        intent = new Intent(MainActivity.this, BoardsActivity.class);

        b = new Bundle();
    }

    @Override
    public void onButtonEasy() {
        b.putInt(DIFFICULTY_KEY, Difficulty.EASY.ordinal());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
        finish();
    }

    @Override
    public void onButtonMedium() {
        b.putInt(DIFFICULTY_KEY, Difficulty.MEDIUM.ordinal());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
        finish();
    }

    @Override
    public void onButtonHard() {
        b.putInt(DIFFICULTY_KEY, Difficulty.HARD.ordinal());
        intent.putExtra(BUNDLE_KEY, b);

        startActivity(intent);
        finish();
    }

    @Override
    public void onButtonGameStart() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ChooseDifficultyFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onButtonHighScores() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HighScoresFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onButtonEasyHighScore() {

    }

    @Override
    public void onButtonMediumHighScore() {

    }

    @Override
    public void onButtonHardHighScore() {

    }
}
