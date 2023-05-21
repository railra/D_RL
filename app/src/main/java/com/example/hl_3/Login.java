package com.example.hl_3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hl_3.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {
    Button btn_lregister, btn_llogin;
    EditText et_lusername, et_lpassword;
    FirebaseAuth mAuth;
    String username, password;

    private DatabaseReference mDataBase;
    private List<User> listTemp;
    String admin = "railra48@gmail.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("User");
        setContentView(R.layout.activity_login);
        listTemp = new ArrayList<>();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null)
        {
            if(cUser.getEmail().equals(admin)){
                Intent intent = new Intent(Login.this, Admin.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        }

        //databaseHelper = new DatabaseHelper(Login.this);

        et_lusername = findViewById(R.id.et_lusername);
        et_lpassword = findViewById(R.id.et_lpassword);

        btn_llogin = findViewById(R.id.btn_llogin);
        btn_lregister = findViewById(R.id.btn_lregister);


        btn_lregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
    public void onClickLogin(View view){
        username = et_lusername.getText().toString();
        password = et_lpassword.getText().toString();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
        {
            ValueEventListener vListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if(listTemp.size() > 0)listTemp.clear();
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        User user = ds.getValue(User.class);
                        assert user != null;
                        if(user.status.equals("banned") && user.email.equals(username)){
                            Toast.makeText(getApplicationContext(), "Ваш аккаунт заблокирован", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else{
                            SignIn();
                            break;
                        }

                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            mDataBase.addValueEventListener(vListener);

        }
    }
    private void SignIn(){
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    if(username.equals(admin)){
                        Intent intent = new Intent(Login.this, Admin.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }
                    Toast.makeText(getApplicationContext(), "User SignIn Successful", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(getApplicationContext(), "User SignIn failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}