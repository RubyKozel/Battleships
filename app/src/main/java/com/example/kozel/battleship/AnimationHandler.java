package com.example.kozel.battleship;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kozel.battleship.Logic.TileState;

import org.jetbrains.annotations.NotNull;


class AnimationHandler {
    private Handler handler;
    private GridView humanView;
    private GridView computerView;
    private ProgressBar progressBar;
    private LinearLayout computersShipsLeft;
    private LinearLayout humanShipsLeft;
    private TextView turnDisplayer;
    private static final int DURATION = 1500;
    private static final int DELAY = 500;
    private static final int LOW_OPAQUE = 0;
    private static final int HIGH_OPAQUE = 1;

    AnimationHandler(
            GridView humanView,
            GridView computerView,
            LinearLayout computersShipsLeft,
            LinearLayout humanShipsLeft,
            ProgressBar progressBar,
            TextView turnDisplayer) {
        this.humanView = humanView;
        this.computersShipsLeft = computersShipsLeft;
        this.computerView = computerView;
        this.humanShipsLeft = humanShipsLeft;
        this.progressBar = progressBar;
        this.turnDisplayer = turnDisplayer;
        handler = new Handler();
    }

    void toggleHumanVisibility() {
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

    void toggleComputerVisibility() {
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

    private void toggleVisibilityAnimation(@NotNull View view, int visibility) {
        view.setAlpha(visibility == View.VISIBLE ? LOW_OPAQUE : HIGH_OPAQUE);
        if (visibility == View.VISIBLE) {
            view.setVisibility(visibility);
            view.setTranslationX(-100f);
        }
        view.animate()
                .setDuration(DURATION)
                .translationXBy(100f)
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
        turnDisplayer.setAlpha(HIGH_OPAQUE);
        turnDisplayer.animate()
                .setDuration(DURATION / 2)
                .alpha(LOW_OPAQUE)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        turnDisplayer.setAlpha(0.0f);
                        turnDisplayer.setText(idOfString);
                        turnDisplayer.animate().setDuration(DURATION / 2).alpha(HIGH_OPAQUE);
                        turnDisplayer.animate().setListener(null);
                    }
                });
    }

    private void toggleProgressBarVisible() {
        progressBar.animate()
                .setDuration(DURATION)
                .alpha(HIGH_OPAQUE)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
    }

    void toggleProgressBarInvisible() {
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setAlpha(LOW_OPAQUE);
    }

    private void animate(GridView playerView, View view, int drawable, int frames) {
        Drawable d = ((TileView) view).getImage().getBackground();
        ((TileView) view).getImage().setBackgroundResource(drawable);
        AnimationDrawable animation = (AnimationDrawable) ((TileView) view).getImage().getBackground();
        animation.start();

        handler.postDelayed(() -> {
            ((TileView) view).getImage().setBackground(d);
            ((TileAdapter) playerView.getAdapter()).notifyDataSetChanged();
        }, 50 * frames);
    }

    void animateTileOf(GridView playerView, TileState t, View view) {
        if(t == TileState.HIT)
            animate(playerView, view, R.drawable.explosion, 16);
        else if (t == TileState.MISS)
            animate(playerView, view, R.drawable.water_splash, 10);
        else
            ((TileAdapter) playerView.getAdapter()).notifyDataSetChanged();
    }
}
