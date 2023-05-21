package com.example.hl_3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hl_3.adapters.CustomArrayAdapter;
import com.example.hl_3.models.Task;
import com.example.hl_3.utilities.TaskListItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserInf extends AppCompatActivity
{
    private ListView doneTasksList;
    private CustomArrayAdapter adapter_task;
    public List<TaskListItem> listItemMainFr;
    private TaskListItem listItemFr;
    private DatabaseReference mDataBase;
    private TextView tvName, tvScore, tvEmail;
    String ss;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        init();
        getIntentMain();
    }

    private void init()
    {
        mDataBase = FirebaseDatabase.getInstance().getReference("Task");
        listItemMainFr = new ArrayList<>();
        doneTasksList = findViewById(R.id.listUserTasks);
        adapter_task = new CustomArrayAdapter(this, R.layout.task_list_item, listItemMainFr, getLayoutInflater());
        doneTasksList.setAdapter(adapter_task);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String uid = currentUser.getUid();
        tvName = findViewById(R.id.tvName);
        tvScore = findViewById(R.id.tvScore);
        tvEmail = findViewById(R.id.tvEmail);
        getDataFromBase();
    }

    private void getIntentMain()
    {
        Intent i = getIntent();
        if (i != null)
        {
            tvName.setText(i.getStringExtra("user_name"));
            tvScore.setText(i.getStringExtra("user_score"));
            ss = i.getStringExtra("user_email");
            tvEmail.setText(ss);
        }
        getDataFromBase();
    }

    private void getDataFromBase()
    {

        ValueEventListener vListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (listItemMainFr.size() > 0) listItemMainFr.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {

                    Task task = ds.getValue(Task.class);
                    assert task != null;
                    if (task.user.equals(ss))
                    {
                        listItemFr = new TaskListItem();
                        listItemFr.setNameTask(task.name);
                        listItemFr.setIdTask(task.id);
                        listItemFr.setAmountTask(task.amount);
                        listItemFr.setStartTask(task.start);
                        listItemFr.setEndTask(task.end);
                        listItemMainFr.add(listItemFr);
                    }
                }
                adapter_task.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

}
