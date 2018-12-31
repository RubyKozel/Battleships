package com.example.kozel.battleship;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppStartFragment extends Fragment {

    interface onButtonClickedListener {
        void onButtonGameStart();
        void onButtonHighScores();
    }

    private onButtonClickedListener mListener;

    public AppStartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_start_fragment, container, false);

        view.findViewById(R.id.start_game_button).setOnClickListener((v)-> {
            if(AppStartFragment.this.mListener != null) AppStartFragment.this.mListener.onButtonGameStart();
        });

        view.findViewById(R.id.high_scores).setOnClickListener((v)-> {
            if(AppStartFragment.this.mListener != null) AppStartFragment.this.mListener.onButtonHighScores();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onButtonClickedListener listener = (onButtonClickedListener) context;
            if (listener != null) this.registerListener(listener);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onButtonClickedListener");
        }
    }

    public void registerListener(onButtonClickedListener listener) {
        this.mListener = listener;
    }
}
