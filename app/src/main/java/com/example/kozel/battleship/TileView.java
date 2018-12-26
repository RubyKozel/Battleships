package com.example.kozel.battleship;


import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TileView extends LinearLayout {

    private ImageView image;

    public TileView(Context context) {
        super(context);
        image = new ImageView(context);
        image.setBackgroundResource(R.drawable.griditem_bg_not_fired);
    //slava=0
        addView(image);
    }

    public ImageView getImage() {
        return this.image;
    }
}
