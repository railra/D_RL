package com.example.hl_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hl_3.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity
{

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<User> listTemp;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        init();
        getDataFromDB();
        setOnClickItem();
    }
    private void init()
    {
        listView = findViewById(R.id.listv);
        listData = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);

    }
    private void getDataFromDB()
    {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(listData.size() > 0)listData.clear();
                if(listTemp.size() > 0)listTemp.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    listData.add(user.email);
                    listTemp.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mDataBase.addValueEventListener(vListener);

    }
    private void setOnClickItem()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = listTemp.get(position);
                Intent i = new Intent(Admin.this, UserInf.class);
                i.putExtra("user_name",user.name);
                i.putExtra("user_score",String.valueOf(user.score));
                i.putExtra("user_email",user.email);
                startActivity(i);
            }
        });
    }
}
