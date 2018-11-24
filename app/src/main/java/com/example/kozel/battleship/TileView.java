package com.example.kozel.battleship;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kozel.battleship.Logic.Difficulty;

public class TileView extends LinearLayout {

    private TextView text;

    public TileView(Context context) {
        super(context);

        this.setOrientation(VERTICAL);

        text = new TextView(context);

        text.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        text.setGravity(Gravity.CENTER_VERTICAL);
        text.setTextSize(50);
        text.setTextColor(Color.BLACK);

        setBackgroundColor(Color.LTGRAY);

        addView(text);

    }

    public TextView getText() {
        return text;
    }
}
