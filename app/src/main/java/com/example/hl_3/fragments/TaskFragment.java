package com.example.hl_3.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hl_3.MainActivity;
import com.example.hl_3.R;
import com.example.hl_3.adapters.CustomArrayAdapter;
import com.example.hl_3.models.Task;
import com.example.hl_3.utilities.TaskListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TaskFragment extends Fragment {

    private View view;
    private ListView doneTasksList;
    private CustomArrayAdapter adapter_task;
    public List<TaskListItem>  listItemMainFr;
    private TaskListItem listItemFr;
    private DatabaseReference mDataBase;

    String uid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }
    private void init()
    {

        mDataBase = FirebaseDatabase.getInstance().getReference("Task");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listItemMainFr = new ArrayList<>();
        doneTasksList = getView().findViewById(R.id.list_taskk);
        adapter_task = new CustomArrayAdapter(getContext(), R.layout.list_item, listItemMainFr, getLayoutInflater());
        doneTasksList.setAdapter(adapter_task);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String uid = currentUser.getUid();
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(listItemMainFr.size() > 0)listItemMainFr.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {

                    Task task = ds.getValue(Task.class);
                    assert task != null;
                    if(task.user.equals(uid)){
                        listItemFr = new TaskListItem();
                        listItemFr.setNameTask(task.name);
                        listItemFr.setAmountTask(Integer.parseInt(task.amount));
                        listItemFr.setStartTask(task.start);
                        listItemFr.setEndTask(task.end);
                        listItemMainFr.add(listItemFr);
                    }
                }
                adapter_task.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }


}
