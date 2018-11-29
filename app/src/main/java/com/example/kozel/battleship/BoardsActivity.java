package com.example.kozel.battleship;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Handler;
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

public class BoardsActivity extends AppCompatActivity {

    private BattleshipController controller;
    private Difficulty difficulty;
    private Handler handler;
    private GridView humanView;
    private GridView computerView;
    private ProgressBar progressBar;
    private LinearLayout computersShipsLeft;
    private LinearLayout humanShipsLeft;
    public static final int DURATION = 1500;
    public static final int DELAY = 500;
    public static final int LOW_OPAQUE = 0;
    public static final int HIGH_OPAQUE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        handler = new Handler();

        progressBar = findViewById(R.id.progressBar);
        humanView = findViewById(R.id.human_grid);
        computerView = findViewById(R.id.computer_grid);
        computersShipsLeft = findViewById(R.id.shipsCountLayoutComputer);
        humanShipsLeft = findViewById(R.id.shipsCountLayoutHuman);

        computersShipsLeft.setBackgroundResource(R.drawable.border);
        humanShipsLeft.setBackgroundResource(R.drawable.border);
        progressBar.setVisibility(View.INVISIBLE);

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
            toggleHumanVisibility();
        }
    }

    private void invokeComputerPlay() {
        new Thread(() -> {
            controller.computerPlay();
            runOnUiThread(() -> {
                toggleProgressBarInvisible();
                ((TileAdapter) humanView.getAdapter()).notifyDataSetChanged();
                refreshShipAmount(humanShipsLeft, controller.getHumanBoard().getShipAmounts());
                toggleComputerVisibility();
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

    private void toggleHumanVisibility() {
        handler.postDelayed(() -> {
            toggleTurnDisplayAnimation(R.string.computer_turn_display);
            toggleVisibilityAnimation(computerView, View.INVISIBLE);
            toggleVisibilityAnimation(computersShipsLeft, View.INVISIBLE);
            toggleProgressBarVisible();
        }, DELAY);

        handler.postDelayed(() -> {
            toggleVisibilityAnimation(humanView, View.VISIBLE);
            toggleVisibilityAnimation(humanShipsLeft, View.VISIBLE);
        }, DURATION);
    }

    private void toggleComputerVisibility() {
        handler.postDelayed(() -> {
            toggleTurnDisplayAnimation(R.string.human_turn_display);
            toggleVisibilityAnimation(humanView, View.INVISIBLE);
            toggleVisibilityAnimation(humanShipsLeft, View.INVISIBLE);
        }, DELAY);

        handler.postDelayed(() -> {
            toggleVisibilityAnimation(computerView, View.VISIBLE);
            toggleVisibilityAnimation(computersShipsLeft, View.VISIBLE);
        }, DURATION);
    }

    private void toggleVisibilityAnimation(View view, int visibility) {
        view.setAlpha(visibility == View.VISIBLE ? LOW_OPAQUE : HIGH_OPAQUE);
        if (visibility == View.VISIBLE)
            view.setVisibility(visibility);
        view.animate()
                .setDuration(DURATION)
                .alpha(visibility == View.VISIBLE ? HIGH_OPAQUE : LOW_OPAQUE)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(visibility);
                    }
                });
    }

    private void toggleTurnDisplayAnimation(int idOfString) {
        TextView v1 = findViewById(R.id.turnView);
        v1.setAlpha(HIGH_OPAQUE);
        v1.animate().setDuration(DURATION / 2).alpha(LOW_OPAQUE).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v1.setAlpha(0.0f);
                v1.setText(idOfString);
                v1.animate().setDuration(DURATION / 2).alpha(HIGH_OPAQUE);
                v1.animate().setListener(null);
            }
        });
    }

    private void toggleProgressBarVisible() {
        progressBar.animate().setDuration(DURATION).alpha(HIGH_OPAQUE).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void toggleProgressBarInvisible() {
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setAlpha(LOW_OPAQUE);
    }
}