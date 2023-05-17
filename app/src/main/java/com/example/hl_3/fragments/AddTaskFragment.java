package com.example.hl_3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hl_3.MainActivity;
import com.example.hl_3.R;
import com.example.hl_3.utilities.TaskListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddTaskFragment extends Fragment {

    private EditText editTaskName;
    private EditText editTaskAmount;
    private FloatingActionButton saveBtn;
    private TaskFragment taskFragment = new TaskFragment();
    private List<TaskListItem> listItemMain;

    private TaskListItem listItemFr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.add_list_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTaskName = getView().findViewById(R.id.editTextTaskName);
        editTaskAmount = getView().findViewById(R.id.editTextTaskAmount);
        saveBtn = getView().findViewById(R.id.button_save);
        listItemFr = new TaskListItem();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItemFr.setNameTask(editTaskName.getText().toString());
                listItemFr.setAmountTask(Integer.parseInt(editTaskAmount.getText().toString()));
                MainActivity.arrayName.add(editTaskName.getText().toString());
                MainActivity.arrayAmount.add(Integer.valueOf(editTaskAmount.getText().toString()));
            }
        });

    }
}
