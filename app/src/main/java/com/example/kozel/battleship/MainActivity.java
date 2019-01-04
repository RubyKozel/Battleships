package com.example.kozel.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.kozel.battleship.Logic.Difficulty;

public class MainActivity extends AppCompatActivity implements
        ChooseDifficultyFragment.onButtonClickedListener,
        AppStartFragment.onButtonClickedListener,
        HighScoresFragment.onButtonClickedListener {

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
                .commit();

        intent = new Intent(MainActivity.this, BoardsActivity.class);
        b = new Bundle();
    }

    @Override
    public void onButtonEasy() {
        b.putInt(DIFFICULTY_KEY, Difficulty.EASY.ordinal());
        intent.putExtra(BUNDLE_KEY, b);

        getPreferences(MODE_PRIVATE)
                .edit()
                .putInt(DIFFICULTY_KEY, Difficulty.EASY.ordinal())
                .apply();

        startActivity(intent);
    }

    @Override
    public void onButtonMedium() {
        b.putInt(DIFFICULTY_KEY, Difficulty.MEDIUM.ordinal());
        intent.putExtra(BUNDLE_KEY, b);

        getPreferences(MODE_PRIVATE)
                .edit()
                .putInt(DIFFICULTY_KEY, Difficulty.MEDIUM.ordinal())
                .apply();

        startActivity(intent);
    }

    @Override
    public void onButtonHard() {
        b.putInt(DIFFICULTY_KEY, Difficulty.HARD.ordinal());
        intent.putExtra(BUNDLE_KEY, b);

        getPreferences(MODE_PRIVATE)
                .edit()
                .putInt(DIFFICULTY_KEY, Difficulty.HARD.ordinal())
                .apply();

        startActivity(intent);
    }

    @Override
    public void onButtonGameStart() {
        int difficulty = getPreferences(MODE_PRIVATE)
                .getInt(DIFFICULTY_KEY, -1);
        if (difficulty != -1) {
            switch (difficulty) {
                case 0:
                    onButtonEasy();
                    break;
                case 1:
                    onButtonMedium();
                    break;
                case 2:
                    onButtonHard();
                    break;
                default:
                    break;
            }
        }
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
    public void onButtonChooseDifficulty() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ChooseDifficultyFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onButtonEasyHighScore() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HighScoreTable())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onButtonMediumHighScore() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HighScoreTable())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onButtonHardHighScore() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HighScoreTable())
                .addToBackStack(null)
                .commit();
    }
}