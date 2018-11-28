package com.example.kozel.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.LinearLayout;
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
        LinearLayout computersShipsLeft = findViewById(R.id.shipsCountLayout);

        Intent intent = getIntent();

        Bundle b = intent.getBundleExtra(MainActivity.BUNDLE_KEY);

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
            }
        });


        computerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                humanView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                computerView.setAdapter(new TileAdapter(getApplicationContext(),
                        controller.getComputerBoard(), computerView.getWidth(), computerView.getHeight()));
                computerView.setNumColumns(difficulty.getSize());
                refreshShipAmount(computersShipsLeft);
            }
        });


        computerView.setOnItemClickListener((parent, view, position, id) -> {
            if (controller.getHuman().isTurn()) {

                // Human turn
                if (controller.humanPlay(position)) {
                    ((TileAdapter) computerView.getAdapter()).notifyDataSetChanged();
                    ((TextView) findViewById(R.id.turnView)).setText(R.string.computer_turn_display);
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                    refreshShipAmount(computersShipsLeft);
                }

                //TODO - check if player won, if so need to go to WinLoseActivity, if not start the below thread - SLAVA

                // Computer turn
                new Thread(() -> {
                    controller.computerPlay();
                    runOnUiThread(() -> {
                        ((TileAdapter) humanView.getAdapter()).notifyDataSetChanged();
                        ((TextView) findViewById(R.id.turnView)).setText(R.string.human_turn_display);
                        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    });
                }).start();

                // TODO - check if computer won, if so need to go to WinLoseActivity - SLAVA
            }
        });
    }

    private void refreshShipAmount(LinearLayout computersShipsLeft) {
        int[] shipAmounts = controller.getComputerBoard().getShipAmounts();
        int count = computersShipsLeft.getChildCount();
        for (int i = 0; i < count; i++) {
            ((TextView) computersShipsLeft.getChildAt(i)).setText(getResources().getString(R.string.sized, (i + 1), shipAmounts[i]));
        }
    }
}
