package com.example.hl_3.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hl_3.R;
import com.example.hl_3.models.Task;
import com.example.hl_3.models.User;
import com.example.hl_3.utilities.TaskListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AddTaskFragment extends Fragment {

    private EditText editTaskName, editTaskAmount, editStartTime, editEndTime;
    private FloatingActionButton saveBtn;
    private Button bDeleteTask;
    private TaskFragment taskFragment = new TaskFragment();
    private List<TaskListItem> listItemMain;
    private DatabaseReference taskDataBase, userDataBase;
    private String TASK_KEY = "Task", s1;
    String firstAmount;
    int sum;
    private boolean updateData = true;

    private static final DecimalFormat FORMATTER = new DecimalFormat("00");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_list_item, container, false);

    }
    private void init()
    {
        editTaskName = getView().findViewById(R.id.editTextTaskName);
        editTaskAmount = getView().findViewById(R.id.editTextTaskAmount);
        editStartTime = getView().findViewById(R.id.editStartTime);
        editEndTime = getView().findViewById(R.id.editEndTime);
        saveBtn = getView().findViewById(R.id.button_save);
        bDeleteTask = getView().findViewById(R.id.bDeleteTask);
        Bundle args = getArguments();
        if (args != null) {
            s1 = args.getString("task_id");
            editTaskName.setText(args.getString("task_name"));
            firstAmount = args.getString("task_amount");
            editStartTime.setText(args.getString("task_start"));
            editEndTime.setText(args.getString("task_end"));
            editTaskAmount.setText(firstAmount);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String userId = currentUser.getUid();
        String userEmail = currentUser.getEmail();

        SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
        userDataBase = FirebaseDatabase.getInstance().getReference("User");
        taskDataBase = FirebaseDatabase.getInstance().getReference("Task");
        bDeleteTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(s1 != null){
                    taskDataBase.child(s1).removeValue();
                    TaskFragment taskView = new TaskFragment();
                    FragmentTransaction taskTrans = getParentFragmentManager().beginTransaction();
                    taskTrans.replace(R.id.container, taskView);
                    taskTrans.commit();
                    s1 = null;
                    firstAmount = null;
                }
                else{
                    TaskFragment taskView = new TaskFragment();
                    FragmentTransaction taskTrans = getParentFragmentManager().beginTransaction();
                    taskTrans.replace(R.id.container, taskView);
                    taskTrans.commit();
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTaskName.getText().toString();
                int amount = Integer.parseInt(editTaskAmount.getText().toString().isEmpty()?"0":editTaskAmount.getText().toString());
                String start = editStartTime.getText().toString();
                String end = editEndTime.getText().toString();
                if(!name.isEmpty() && amount != 0 && !start.isEmpty() && !end.isEmpty()){
                    if(s1 != null){
                        Task newTask = new Task(s1, name, amount, start, end, userId);
                        taskDataBase.child(s1).setValue(newTask);
                    }
                    else{
                        String id = taskDataBase.push().getKey();
                        Task newTask = new Task(id, name, amount, start, end, userId);
                        taskDataBase.child(id).setValue(newTask);
                    }
                    updateData = true;
                    ValueEventListener v1Listener = new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            for(DataSnapshot ds : snapshot.getChildren())
                            {

                                User user = ds.getValue(User.class);
                                assert user != null;
                                if(user.email.equals(userEmail)){
                                    if(updateData == true){
                                        updateData = false;
                                        sum = amount;
                                        if(s1!=null){
                                            sum = amount - Integer.parseInt(firstAmount);
                                        }
                                        userDataBase.child(user.email.substring(0, user.email.indexOf("@"))).child("score").setValue(user.score+sum);
                                    }

                                }
                            }
                            s1 = null;
                            firstAmount = null;

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {
                        }
                    };
                    userDataBase.addValueEventListener(v1Listener);
                    TaskFragment taskView = new TaskFragment();
                    FragmentTransaction taskTrans = getParentFragmentManager().beginTransaction();
                    taskTrans.replace(R.id.container, taskView);
                    taskTrans.commit();
                }
                else{
                    Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        EditText startTimeEdit = getView().findViewById(R.id.editStartTime);
        EditText endTimeEdit = getView().findViewById(R.id.editEndTime);

        startTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            startTimeEdit.setText(FORMATTER.format(hourOfDay) + ":" + FORMATTER.format(minute));
                        }
                    }, 0 , 0, true);
                    timePickerDialog.show();
            }
        });



        endTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == endTimeEdit){


                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            endTimeEdit.setText(FORMATTER.format(hourOfDay)+ ":" + FORMATTER.format(minute));
                        }
                    },0,0, true);
                    timePickerDialog.show();
                }
            }
        });
    }

}
