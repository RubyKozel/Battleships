package com.example.kozel.battleship;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.kozel.battleship.Logic.Board;
import com.example.kozel.battleship.Logic.TileState;

public class TileAdapter extends BaseAdapter {

    private Board board;
    private Context context;
    private int size;
    private int height;
    private int width;


    TileAdapter(Context context, Board board, int height, int width) {
        this.board = board;
        this.context = context;
        this.size = board.getDifficulty().getSize();
        this.height = height;
        this.width = width;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public int getCount() {
        return size * size;
    }

    @Override
    public Object getItem(int position) {
        return board.getTile(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TileView tileView;
        if (convertView == null) {
            tileView = new TileView(context);
            tileView.setLayoutParams(new GridView.LayoutParams(height / size, width / size));
            tileView.getImage().setLayoutParams(new LinearLayout.LayoutParams(height / size, width / size));
        } else {
            tileView = (TileView) convertView;
        }

        TileState state = board.getTile(position).getState();
        int src = state.getStateImage();
        if (src == 0) {
            tileView.getImage().setImageResource(0);
            tileView.getImage().setBackgroundResource(R.drawable.gridview_bg);
        } else if (src == 1) {
            tileView.getImage().setImageResource(0);
            tileView.getImage().setBackgroundResource(R.drawable.border);
        } else {
            if(state == TileState.HIT || state == TileState.DESTROYED) {
                tileView.getImage().setBackgroundResource(R.drawable.gridview_bg);
            }
            tileView.getImage().setImageResource(src);
        }
        return tileView;
    }
}
