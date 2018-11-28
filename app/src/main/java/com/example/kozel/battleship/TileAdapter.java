package com.example.kozel.battleship;

import android.content.Context;
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
        LinearLayout tileView;
        if (convertView == null) {
            tileView = new LinearLayout(context);
            tileView.setLayoutParams(new GridView.LayoutParams(height / size - 5, width / size - 5));
        } else {
            tileView = (LinearLayout) convertView;
        }

        TileState state = board.getTile(position).getState();
        tileView.setBackgroundResource(
                state == TileState.VISIBLE ? R.drawable.gridview_bg :
                        state == TileState.HIT ? R.drawable.griditem_bg_hit :
                                state == TileState.MISS ? R.drawable.griditem_bg_miss :
                                        state == TileState.DESTROYED ? R.drawable.griditem_bg_destroyed :
                                                R.drawable.griditem_bg_not_fired);
        return tileView;
    }
}
