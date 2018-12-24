package com.example.kozel.battleship;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HighScoresFragment extends Fragment {


    interface onButtonClickedListener {
        void onButtonEasyHighScore();
        void onButtonMediumHighScore();
        void onButtonHardHighScore();
    }

    private HighScoresFragment.onButtonClickedListener mListener;

    public HighScoresFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.highscores_fragment, container, false);

        view.findViewById(R.id.easy_button).setOnClickListener((v)-> {
            if(HighScoresFragment.this.mListener != null) HighScoresFragment.this.mListener.onButtonEasyHighScore();
        });

        view.findViewById(R.id.medium_button).setOnClickListener((v)-> {
            if(HighScoresFragment.this.mListener != null) HighScoresFragment.this.mListener.onButtonMediumHighScore();
        });

        view.findViewById(R.id.hard_button).setOnClickListener((v)-> {
            if(HighScoresFragment.this.mListener != null) HighScoresFragment.this.mListener.onButtonHardHighScore();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            HighScoresFragment.onButtonClickedListener listener = (HighScoresFragment.onButtonClickedListener) context;
            if (listener != null) this.registerListener(listener);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onButtonClickedListener");
        }
    }

    public void registerListener(HighScoresFragment.onButtonClickedListener listener) {
        this.mListener = listener;
    }
}
