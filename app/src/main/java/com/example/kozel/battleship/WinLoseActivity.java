package com.example.kozel.battleship;

import android.content.Intent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.kozel.battleship.Logic.Difficulty;


public class WinLoseActivity extends AppCompatActivity {

    private ImageView status_game;
    private int status;
    private Difficulty difficulty;

    private Bundle b2;
    private Intent intent1;
    private Intent intent2;

    public final static String DIFFICULTY_KEY = "DIFFICULTY";
    public final static String PLAY_KEY = "PLAY_KEY";
    public final static String BUNDLE_KEY = "BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_lose);

        intent1 = new Intent(WinLoseActivity.this, MainActivity.class);
        intent2=  new Intent(WinLoseActivity.this, BoardsActivity.class);
        b2 = new Bundle();

        Bundle b = getIntent().getBundleExtra(BoardsActivity.BUNDLE_KEY);

        if (b != null) {
            difficulty = Difficulty.values()[b.getInt(MainActivity.DIFFICULTY_KEY)];
            status = b.getInt(BoardsActivity.WIN_LOSE_KEY);
        }

        status_game = findViewById(R.id.status_game);
        status_game.setBackgroundResource(status);
    }

    public void onPlayAgainClick(View view)
    {
        b2.putInt(PLAY_KEY,difficulty.ordinal());
        intent2.putExtra(BUNDLE_KEY, b2);

        startActivity(intent2);
    }

    public void onMainMenuClick(View view)
    {
        startActivity(intent1);
    }
}
