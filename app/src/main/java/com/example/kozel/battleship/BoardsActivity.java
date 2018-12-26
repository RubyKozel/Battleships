package com.example.kozel.battleship;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kozel.battleship.Logic.BattleshipController;
import com.example.kozel.battleship.Logic.Board;
import com.example.kozel.battleship.Logic.Difficulty;
import com.example.kozel.battleship.Logic.TileState;


public class BoardsActivity extends AppCompatActivity {

    private BattleshipController controller;
    private Difficulty difficulty;
    private GridView humanView;
    private GridView computerView;
    private LinearLayout computersShipsLeft;
    private LinearLayout humanShipsLeft;
    private AnimationHandler animationHandler;
    private Handler handler;
    private Bundle anotherBundle;
    private Intent intent;

    public final static String WIN_LOSE_KEY = "WIN_LOSE";
    public final static String DIFFICULTY_KEY = "DIFFICULTY";
    public final static String BUNDLE_KEY = "BUNDLE";
    public final static int win = R.drawable.you_win;
    public final static int lose = R.drawable.game_over;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        handler = new Handler();

        humanView = findViewById(R.id.human_grid);
        computerView = findViewById(R.id.computer_grid);
        computersShipsLeft = findViewById(R.id.shipsCountLayoutComputer);
        humanShipsLeft = findViewById(R.id.shipsCountLayoutHuman);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        computersShipsLeft.setBackgroundResource(R.drawable.border);
        humanShipsLeft.setBackgroundResource(R.drawable.border);

        animationHandler = new AnimationHandler(humanView, computerView, computersShipsLeft, humanShipsLeft, progressBar, findViewById(R.id.turnView));

        intent = new Intent(BoardsActivity.this, WinLoseActivity.class);
        anotherBundle = new Bundle();

        Bundle b = getIntent().getBundleExtra(MainActivity.BUNDLE_KEY);
        Bundle b2 = getIntent().getBundleExtra(MainActivity.BUNDLE_KEY);

        if (b != null) {
            difficulty = Difficulty.values()[b.getInt(MainActivity.DIFFICULTY_KEY)];
            controller = new BattleshipController(difficulty);
        } else {
            difficulty = Difficulty.values()[b2.getInt(WinLoseActivity.DIFFICULTY_KEY)];
            controller = new BattleshipController(difficulty);
        }

        humanView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                humanView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                humanView.setAdapter(new TileAdapter(getApplicationContext(),
                        controller.getHumanBoard(), humanView.getWidth(), humanView.getHeight()));
                humanView.setNumColumns(difficulty.getSize());
                refreshShipAmount(humanShipsLeft, controller.getHumanBoard().getShipAmounts());
            }
        });

        computerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                computerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                computerView.setAdapter(new TileAdapter(getApplicationContext(),
                        controller.getComputerBoard(), computerView.getWidth(), computerView.getHeight()));
                computerView.setNumColumns(difficulty.getSize());
                refreshShipAmount(computersShipsLeft, controller.getComputerBoard().getShipAmounts());
            }
        });

        humanView.setVisibility(View.INVISIBLE);
        humanShipsLeft.setVisibility(View.INVISIBLE);


        computerView.setOnItemClickListener((parent, view, position, id) -> {
            if (controller.getHuman().isTurn()) {
                invokeHumanPlay(position, computerView.getChildAt(position));
                checkWinning(controller.getComputerBoard().getShipCount(), win);
                invokeComputerPlay();
            }
        });
    }

    private void invokeHumanPlay(int position, View view) {
        TileView v = (TileView) view;
        v.getImage().getBackground();
        if (controller.humanPlay(position)) {
            TileState t = controller.getComputerBoard().getTile(position).getState();
            animationHandler.animateTileOf(computerView, t, view);
            refreshShipAmount(computersShipsLeft, controller.getComputerBoard().getShipAmounts());
            animationHandler.toggleHumanVisibility();
        }
    }

    private void invokeComputerPlay() {
        new Thread(()-> {
            int position = controller.computerPlay();
            runOnUiThread(() -> {
                animationHandler.toggleProgressBarInvisible();
                TileState t = controller.getHumanBoard().getTile(position).getState();
                animationHandler.animateTileOf(humanView, t, humanView.getChildAt(position));
                refreshShipAmount(humanShipsLeft, controller.getHumanBoard().getShipAmounts());
                animationHandler.toggleComputerVisibility();
            });
        }).start();

        handler.postDelayed(() -> checkWinning(controller.getHumanBoard().getShipCount(), lose), 4500);
    }

    private void checkWinning(int shipCount, int status) {
        if (controller.checkIfSomeoneWon(shipCount)) {
            anotherBundle.putInt(WIN_LOSE_KEY, status);
            anotherBundle.putInt(DIFFICULTY_KEY, difficulty.ordinal());
            intent.putExtra(BUNDLE_KEY, anotherBundle);
            startActivity(intent);
            finish();
        }
    }

    public void onTest(View view) {
        controller.getHumanBoard().replaceShips(true);
        controller.getHumanBoard().hitRandomShips();
        ((TileAdapter) humanView.getAdapter()).notifyDataSetChanged();
        checkWinning(controller.getHumanBoard().getShipCount(), lose);
    }

    private void refreshShipAmount(LinearLayout shipsLeft, int[] shipAmounts) {
        int count = shipsLeft.getChildCount();
        for (int i = 0; i < count; i++) {
            ((TextView) shipsLeft.getChildAt(i)).setText(getResources().getString(R.string.sized, shipAmounts[count - i - 1]));
        }
    }
}