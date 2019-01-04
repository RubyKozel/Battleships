package com.example.kozel.battleship;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static android.content.Context.MODE_PRIVATE;
import static com.example.kozel.battleship.MainActivity.DIFFICULTY_KEY;

public class AppStartFragment extends Fragment {

    interface onButtonClickedListener {
        void onButtonGameStart();
        void onButtonHighScores();
        void onButtonChooseDifficulty();
    }

    private onButtonClickedListener mListener;

    public AppStartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_start_fragment, container, false);

        if(getActivity().getPreferences(MODE_PRIVATE).getInt(DIFFICULTY_KEY, -1) == -1) {
            view.findViewById(R.id.start_game_button).setVisibility(View.GONE);
        }

        view.findViewById(R.id.start_game_button).setOnClickListener((v)-> {
            if(AppStartFragment.this.mListener != null) AppStartFragment.this.mListener.onButtonGameStart();
        });

        view.findViewById(R.id.high_scores).setOnClickListener((v)-> {
            if(AppStartFragment.this.mListener != null) AppStartFragment.this.mListener.onButtonHighScores();
        });

        view.findViewById(R.id.button_choose_difficulty).setOnClickListener((v)->{
            if(AppStartFragment.this.mListener != null) AppStartFragment.this.mListener.onButtonChooseDifficulty();
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