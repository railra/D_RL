package com.example.hl_3.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hl_3.DatabaseHelper;
import com.example.hl_3.Login;
import com.example.hl_3.MainActivity;
import com.example.hl_3.R;
import com.example.hl_3.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    private TextView scoreAll, playerName;
    private ImageButton bLogOut;
    DatabaseHelper databaseHelper;
    int arrayLength;
    private DatabaseReference mDataBase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayLength = MainActivity.arrayAmount.size();
        mDataBase = FirebaseDatabase.getInstance().getReference("User");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String uEmail = currentUser.getEmail();
        databaseHelper = new DatabaseHelper(getContext());
        playerName = getView().findViewById(R.id.player_name);
        scoreAll = getView().findViewById(R.id.score_all);
        bLogOut = getView().findViewById(R.id.bLogOut);

        ValueEventListener vListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot ds : snapshot.getChildren())
                {

                    User user = ds.getValue(User.class);
                    assert user != null;
                    if(user.email.equals(uEmail)){
                        playerName.setText(user.name);
                        scoreAll.setText(String.valueOf(user.score));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        };
        mDataBase.addValueEventListener(vListener);

        bLogOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
            }
        });
    }


}