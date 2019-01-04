package com.example.kozel.battleship;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChooseDifficultyFragment extends Fragment {

    interface onButtonClickedListener {
        void onButtonEasy();
        void onButtonMedium();
        void onButtonHard();
    }

    private onButtonClickedListener mListener;

    public ChooseDifficultyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_difficulty_fragment, container, false);

        view.findViewById(R.id.easy_button).setOnClickListener((v)-> {
            if(ChooseDifficultyFragment.this.mListener != null) ChooseDifficultyFragment.this.mListener.onButtonEasy();
        });

        view.findViewById(R.id.medium_button).setOnClickListener((v)-> {
            if(ChooseDifficultyFragment.this.mListener != null) ChooseDifficultyFragment.this.mListener.onButtonMedium();
        });

        view.findViewById(R.id.hard_button).setOnClickListener((v)-> {
            if(ChooseDifficultyFragment.this.mListener != null) ChooseDifficultyFragment.this.mListener.onButtonHard();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ChooseDifficultyFragment.onButtonClickedListener listener = (ChooseDifficultyFragment.onButtonClickedListener) context;
            if (listener != null) this.registerListener(listener);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onButtonClickedListener");
        }
    }

    public void registerListener(ChooseDifficultyFragment.onButtonClickedListener listener) {
        this.mListener = listener;
    }
}