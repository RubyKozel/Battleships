package com.example.kozel.battleship;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.kozel.battleship.Logic.Board;
import com.example.kozel.battleship.Logic.Difficulty;
import com.example.kozel.battleship.Logic.TileState;

public class TileAdapter extends BaseAdapter {

    private Board board;
    private Context context;
    private Difficulty difficulty;

    TileAdapter(Context context, Board board) {
        this.board = board;
        this.context = context;
        this.difficulty = board.getDifficulty();
    }

    @Override
    public int getCount() {
        return difficulty.getSize() * difficulty.getSize();
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
            tileView.setLayoutParams(new GridView.LayoutParams(difficulty.getTileSize()[0], difficulty.getTileSize()[1]));
        } else {
            tileView = (TileView) convertView;
        }


        TileState state = board.getTile(position).getState();
        if (state == TileState.VISIBLE) tileView.getText().setText(state.getStateImage());
        tileView.setBackgroundResource(
                state == TileState.HIT ? R.drawable.griditem_bg_hit :
                        state == TileState.MISS ? R.drawable.griditem_bg_miss :
                                state == TileState.DESTROYED ? R.drawable.griditem_bg_destroyed :
                                        R.drawable.griditem_bg_not_fired);
        return tileView;
    }
}
