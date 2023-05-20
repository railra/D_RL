package com.example.hl_3;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.hl_3.adapters.CustomArrayAdapter;
import com.example.hl_3.fragments.AddTaskFragment;
import com.example.hl_3.fragments.ClanFragment;
import com.example.hl_3.fragments.ProfileFragment;
import com.example.hl_3.fragments.RatingFragment;
import com.example.hl_3.fragments.TaskFragment;
import com.example.hl_3.utilities.TaskListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private FloatingActionButton add_task;

    public static ArrayList<String> arrayName;
    public static ArrayList<Integer> arrayAmount;

    DatabaseHelper databaseHelper;
    Login login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        arrayName = new ArrayList<>();
        arrayAmount = new ArrayList<>();
        TaskFragment taskView = new TaskFragment();
        FragmentTransaction taskTrans = getSupportFragmentManager().beginTransaction();
        taskTrans.replace(R.id.container, taskView);
        taskTrans.commit();

    }

    public void onClickProfile(View view)
    {
        ProfileFragment profileView = new ProfileFragment();
        FragmentTransaction profileTrans = getSupportFragmentManager().beginTransaction();
        profileTrans.replace(R.id.container, profileView);
        profileTrans.commit();
    }

    public void onClickTask(View view)
    {
        TaskFragment taskView = new TaskFragment();
        FragmentTransaction taskTrans = getSupportFragmentManager().beginTransaction();
        taskTrans.replace(R.id.container, taskView);
        taskTrans.commit();

    }

    public void onClickRating(View view){
        RatingFragment ratingView = new RatingFragment();
        FragmentTransaction ratingTrans = getSupportFragmentManager().beginTransaction();
        ratingTrans.replace(R.id.container, ratingView);
        ratingTrans.commit();
    }
    public void onClickClan(View view)
    {
        ClanFragment clanView = new ClanFragment();
        FragmentTransaction clanTrans = getSupportFragmentManager().beginTransaction();
        clanTrans.replace(R.id.container, clanView);
        clanTrans.commit();
    }

    public void onClickAdd(View view)
    {
        AddTaskFragment addView = new AddTaskFragment();
        FragmentTransaction addTrans = getSupportFragmentManager().beginTransaction();
        addTrans.replace(R.id.container, addView);
        addTrans.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
