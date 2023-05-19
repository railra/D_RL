package com.example.hl_3.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hl_3.MainActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTaskFragment extends Fragment {

    private EditText editTaskName, editTaskAmount, editStartTime, editEndTime;
    private FloatingActionButton saveBtn;
    private TaskFragment taskFragment = new TaskFragment();
    private List<TaskListItem> listItemMain;
    private DatabaseReference taskDataBase, userDataBase;
    private String TASK_KEY = "Task";
    private int s = 1;
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
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String uid = currentUser.getUid();
        String uEmail = currentUser.getEmail();
        editTaskName = getView().findViewById(R.id.editTextTaskName);
        editTaskAmount = getView().findViewById(R.id.editTextTaskAmount);
        editStartTime = getView().findViewById(R.id.editStartTime);
        editEndTime = getView().findViewById(R.id.editEndTime);
        saveBtn = getView().findViewById(R.id.button_save);
        SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());

        listItemFr = new TaskListItem();
        userDataBase = FirebaseDatabase.getInstance().getReference("User");
        taskDataBase = FirebaseDatabase.getInstance().getReference("Task");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = taskDataBase.child("Task").getKey();
                String name = editTaskName.getText().toString();
                String amount = editTaskAmount.getText().toString();
                String start = editStartTime.getText().toString();
                String end = editEndTime.getText().toString();
                Task newTask = new Task(id, name, amount, start, end, uid);
                taskDataBase.push().setValue(newTask);
                s = 1;
                ValueEventListener v1Listener = new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        for(DataSnapshot ds : snapshot.getChildren())
                        {

                            User user = ds.getValue(User.class);
                            assert user != null;
                            if(user.email.equals(uEmail)){
                                if(s == 1){
                                    s = 0;
                                    userDataBase.child(user.email.substring(0, user.email.indexOf("@"))).child("score").setValue(user.score+Integer.parseInt(amount));
                                }

                            }
                        }

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
        });

        EditText startTimeEdit = getView().findViewById(R.id.editStartTime);
        EditText endTimeEdit = getView().findViewById(R.id.editEndTime);

        final int[] startHour = new int[1];
        final int[] startMinute = new int[1];
        startTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == startTimeEdit){
                    final Calendar calendar = Calendar.getInstance();
                    startHour[0] = calendar.get(Calendar.HOUR);
                    startMinute[0] = calendar.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            startTimeEdit.setText(hourOfDay+ ":" + minute);
                        }
                    }, startHour[0], startMinute[0], false);
                    timePickerDialog.show();
                }
            }
        });

        final int[] endHour = new int[1];
        final int[] endMinute = new int[1];
        endTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == endTimeEdit){
                    final Calendar calendar = Calendar.getInstance();
                    endHour[0] = calendar.get(Calendar.HOUR);
                    endMinute[0] = calendar.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            endTimeEdit.setText(hourOfDay+ ":" + minute);
                        }
                    }, endHour[0], endMinute[0], false);
                    timePickerDialog.show();
                }
            }
        });
    }
}
