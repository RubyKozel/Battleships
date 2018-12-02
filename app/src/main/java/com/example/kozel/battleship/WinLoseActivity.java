package com.example.kozel.battleship;

import android.content.Intent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

public class WinLoseActivity extends AppCompatActivity {

    private ImageView win;
    private ImageView lose;
    private ImageButton main_menu;
    private ImageButton play_again;
    private String status;

    private Bundle b2;
    private Intent intent;

    public final static String MAIN_KEY = "MAIN_KEY";
    public final static String WIN_LOSE = "WIN_LOSE";
    public final static String PLAY_KEY = "PLAY_KEY";
    public final static String BUNDLE_KEY = "BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_lose);

        win = findViewById(R.id.win);
        lose = findViewById(R.id.lose);
        play_again = findViewById(R.id.play_again);
        main_menu = findViewById(R.id.main_menu);

        intent = new Intent(WinLoseActivity.this, MainActivity.class);
        b2 = new Bundle();

        Bundle b = getIntent().getBundleExtra(BoardsActivity.BUNDLE_KEY);

        if (b != null) {
            status = b.getString(BoardsActivity.BUNDLE_KEY);
        }

        if (status.equals("win")){
            win.setVisibility(View.VISIBLE);
            lose.setVisibility(View.INVISIBLE);
        }
        else{
            win.setVisibility(View.INVISIBLE);
            lose.setVisibility(View.VISIBLE);
        }
    }

    public void onPlayAgainClick(View view)
    {
        b2.putInt(PLAY_KEY,1);
        intent.putExtra(BUNDLE_KEY, b2);

        startActivity(intent);
    }

    public void onMainMenu(View view)
    {
        b2.putInt(PLAY_KEY,1);
        intent.putExtra(BUNDLE_KEY, b2);

        startActivity(intent);
    }
}
