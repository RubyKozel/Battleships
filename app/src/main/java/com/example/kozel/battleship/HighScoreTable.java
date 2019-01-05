package com.example.kozel.battleship;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class HighScoreTable extends Fragment {

    DatabaseHelper myDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myDb = DatabaseHelper.getInstance(getContext());

        int[] SCORE_ID_ARRAY = new int[]{
                R.id.score_1,
                R.id.score_2,
                R.id.score_3,
                R.id.score_4,
                R.id.score_5,
                R.id.score_6,
                R.id.score_7,
                R.id.score_8,
                R.id.score_9,
                R.id.score_10
        };

        int[] NAME_ID_ARRAY = new int[]{
                R.id.name_1,
                R.id.name_2,
                R.id.name_3,
                R.id.name_4,
                R.id.name_5,
                R.id.name_6,
                R.id.name_7,
                R.id.name_8,
                R.id.name_9,
                R.id.name_10
        };

        Cursor cur = myDb.getAllData(this.getArguments().getString(MainActivity.DIFFICULTY_KEY));
        View view = inflater.inflate(R.layout.highscore, container, false);
        int i = 0;
        if (cur != null && cur.getCount() > 0) {
            while (cur.moveToNext()) {
                ((TextView) view.findViewById(NAME_ID_ARRAY[i])).setText(cur.getString(cur.getColumnIndex(DatabaseHelper.KEY_NAME)));
                ((TextView) view.findViewById(NAME_ID_ARRAY[i])).setGravity(Gravity.CENTER);
                ((TextView) view.findViewById(SCORE_ID_ARRAY[i])).setText(
                        String.format(Locale.ENGLISH, "%d", cur.getInt(cur.getColumnIndex(DatabaseHelper.KEY_SCORE))));
                ((TextView) view.findViewById(SCORE_ID_ARRAY[i])).setGravity(Gravity.CENTER);
                i++;
            }
        }

        return view;
    }
}