package com.example.hl_3.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hl_3.DatabaseHelper;
import com.example.hl_3.MainActivity;
import com.example.hl_3.R;


public class ProfileFragment extends Fragment {

    private TextView scoreForDay, scoreAll, playerName;
    DatabaseHelper databaseHelper;
    int arrayLength;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayLength = MainActivity.arrayAmount.size();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String username="";
        databaseHelper = new DatabaseHelper(getContext());
        playerName = getView().findViewById(R.id.player_name);
        scoreForDay = getView().findViewById(R.id.score_today);
        scoreAll= getView().findViewById(R.id.score_all);

        scoreForDay.setText(String.valueOf(scoreSum()));
        scoreAll.setText(String.valueOf(scoreSum()));


    }

    private int scoreSum(){
        int scoreSum = 0;
        for (int i = 0; i < arrayLength; i++) {
            scoreSum += MainActivity.arrayAmount.get(i);
        }
        return scoreSum;
    }
}