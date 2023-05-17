package com.example.hl_3.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hl_3.MainActivity;
import com.example.hl_3.R;
import com.example.hl_3.adapters.CustomArrayAdapter;
import com.example.hl_3.utilities.TaskListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class TaskFragment extends Fragment {

    private View view;
    private ListView doneTasksList;
    private CustomArrayAdapter adapter_task;

    public List<TaskListItem>  listItemMainFr;
    private TaskListItem listItemFr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doneTasksList = getView().findViewById(R.id.list_task);
        listItemMainFr = new ArrayList<>();
        for (int i = 0; i < MainActivity.arrayName.size(); i++) {
            listItemFr = new TaskListItem();
            listItemFr.setNameTask(MainActivity.arrayName.get(i));
            listItemFr.setAmountTask(MainActivity.arrayAmount.get(i));
            listItemMainFr.add(listItemFr);
        }

        adapter_task = new CustomArrayAdapter(getContext(), R.layout.list_item, listItemMainFr, getLayoutInflater());
        doneTasksList.setAdapter(adapter_task);
        adapter_task.notifyDataSetChanged();

    }

}
/*

    public void onClickSave(View view)
    {
        /*
        SharedPreferences.Editor edit = preferences.edit();
        String name = editTaskName.getText().toString();
        edit.putString(save_task_name, name);
        edit.putString(save_task_amount, editTaskAmount.getText().toString());
        edit.apply();


            //add_task = findViewById(R.id.button_add);
            //listItemMainFr = (List<TaskListItem>) preferences.getAll();
            //listItemMainFr.add((TaskListItem) preferences.getAll());
            getName = editTaskName.getText().toString();
            getAmount = editTaskAmount.getText().toString();

            listItemFr = new TaskListItem();
            listItemFr.setNameTask(getName);
            listItemFr.setAmountTask(getAmount);
            //listItemFr.setNameTask(preferences.getString(save_task_name, "empty"));
            //listItemFr.setAmountTask(preferences.getString(save_task_amount, "ewfwef"));
            listItemMainFr.add(listItemFr);
            adapter_task.notifyDataSetChanged();
    }




*/
/*
       list = findViewById(R.id.listView);
        array = getResources().getStringArray(R.array.profile_array);
        string_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array)));
        list.setAdapter(string_adapter);

 */
        //array = getResources().getStringArray(R.array.task_names);

        //arrayList = new ArrayList<>(Arrays.asList(array));
        //arrayList.add(Arrays.toString(array));
        //adapter_task = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayList);
