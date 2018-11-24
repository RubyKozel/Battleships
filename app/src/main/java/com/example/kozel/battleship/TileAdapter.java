package com.example.kozel.battleship;

import android.content.Context;
import android.graphics.Color;
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

    public TileAdapter(Context context, Board board) {
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

        tileView.getText().setText(board.getTile(position).getState().getStateImage());

        if (board.getTile(position).getState() == TileState.HIT) {
            tileView.setBackgroundColor(Color.RED);
            tileView.setBackgroundResource(R.drawable.griditem_bg);
        } else if (board.getTile(position).getState() == TileState.MISS) {
            tileView.setBackgroundColor(Color.GRAY);
            tileView.setBackgroundResource(R.drawable.griditem_bg);
        } else {
            tileView.setBackgroundColor(Color.WHITE);
            tileView.setBackgroundResource(R.drawable.griditem_bg);
        }

        return tileView;
    }
}
