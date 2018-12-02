package com.example.kozel.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kozel.battleship.Logic.BattleshipController;
import com.example.kozel.battleship.Logic.Difficulty;

import java.util.ArrayList;

public class BoardsActivity extends AppCompatActivity {

    private BattleshipController controller;
    private Difficulty difficulty;
    private GridView humanView;
    private GridView computerView;
    private LinearLayout computersShipsLeft;
    private LinearLayout humanShipsLeft;
    private AnimationHandler animationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        humanView = findViewById(R.id.human_grid);
        computerView = findViewById(R.id.computer_grid);
        computersShipsLeft = findViewById(R.id.shipsCountLayoutComputer);
        humanShipsLeft = findViewById(R.id.shipsCountLayoutHuman);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        computersShipsLeft.setBackgroundResource(R.drawable.border);
        humanShipsLeft.setBackgroundResource(R.drawable.border);

        animationHandler = new AnimationHandler(humanView, computerView, computersShipsLeft, humanShipsLeft, progressBar, findViewById(R.id.turnView));

        Bundle b = getIntent().getBundleExtra(MainActivity.BUNDLE_KEY);

        if (b != null) {
            difficulty = Difficulty.values()[b.getInt(MainActivity.DIFFICULTY_KEY)];
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
                invokeHumanPlay(position);
                //TODO - check if player won, if so need to go to WinLoseActivity, if not start the below thread - SLAVA
                invokeComputerPlay();
                // TODO - check if computer won, if so need to go to WinLoseActivity - SLAVA
            }
        });
    }


    private void invokeHumanPlay(int position) {
        if (controller.humanPlay(position)) {
            ((TileAdapter) computerView.getAdapter()).notifyDataSetChanged();
            refreshShipAmount(computersShipsLeft, controller.getComputerBoard().getShipAmounts());
            animationHandler.toggleHumanVisibility();
        }
    }

    private void invokeComputerPlay() {
        new Thread(() -> {
            controller.computerPlay();
            runOnUiThread(() -> {
                animationHandler.toggleProgressBarInvisible();
                ((TileAdapter) humanView.getAdapter()).notifyDataSetChanged();
                refreshShipAmount(humanShipsLeft, controller.getHumanBoard().getShipAmounts());
                animationHandler.toggleComputerVisibility();
            });
        }).start();
    }

    private void refreshShipAmount(LinearLayout shipsLeft, int[] shipAmounts) {
        int count = shipsLeft.getChildCount();
        for (int i = 0; i < count; i++) {
            ((TextView) shipsLeft
                    .getChildAt(i))
                    .setText(getResources().getString(R.string.sized, shipAmounts[count - i - 1]));
        }
    }
}