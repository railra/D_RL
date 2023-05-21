package com.example.hl_3.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hl_3.Login;
import com.example.hl_3.R;
import com.example.hl_3.adapters.RatingAdapter;
import com.example.hl_3.models.User;
import com.example.hl_3.utilities.UserListItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RatingFragment extends Fragment {


    private RatingAdapter adapter_rating;
    public List<UserListItem> listItemMainFr;
    private UserListItem listItemFr;
    String userEmail;
    private DatabaseReference mDataBase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBase = FirebaseDatabase.getInstance().getReference("User");

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rating, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listItemMainFr = new ArrayList<>();
        ListView doneUsersList = getView().findViewById(R.id.list_user);
        adapter_rating = new RatingAdapter(getContext(), R.layout.user_list_item, listItemMainFr, getLayoutInflater());
        doneUsersList.setAdapter(adapter_rating);
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                userEmail = currentUser.getEmail();
                if(listItemMainFr.size() > 0)listItemMainFr.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {

                    User user = ds.getValue(User.class);
                    assert user != null;
                    if(user.status.equals("banned") && user.email.equals(userEmail)){
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(getActivity(), Login.class);
                        startActivity(i);
                    }
                    listItemFr = new UserListItem();
                    listItemFr.setNameUser(user.name);
                    listItemFr.setAmountUser(user.score);
                    listItemMainFr.add(listItemFr);


                }
                adapter_rating.notifyDataSetChanged();
                Collections.sort(listItemMainFr);
                Collections.reverse(listItemMainFr);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }
}