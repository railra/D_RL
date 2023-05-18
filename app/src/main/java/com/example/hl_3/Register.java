package com.example.hl_3;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private DatabaseReference mDataBase;
    FirebaseAuth mAuth;
    TextView textView;
    EditText et_username, et_password, et_cpassword, et_name;
    Button btn_register, btn_login, btn_ver_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseHelper = new DatabaseHelper(Register.this);
        et_username = (EditText)findViewById(R.id.et_username);
        et_name = (EditText)findViewById(R.id.et_name);
        et_password = (EditText)findViewById(R.id.et_password);
        et_cpassword = (EditText)findViewById(R.id.et_cpassword);
        textView = (TextView)findViewById(R.id.textView);

        btn_register = (Button)findViewById(R.id.btn_register);
        btn_ver_register = (Button)findViewById(R.id.btn_ver_register);
        btn_login = (Button)findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("User");
        et_username.setVisibility(View.VISIBLE);
        et_password.setVisibility(View.VISIBLE);
        et_cpassword.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.VISIBLE);
        btn_ver_register.setVisibility(View.GONE);
        et_name.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        // кнопка перехода на экран логина
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);

                startActivity(intent);
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = et_username.getText().toString();
                //считываем с полей ввода

                String password = et_password.getText().toString();
                String confirm_password = et_cpassword.getText().toString();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    if(password.equals(confirm_password)){
                        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    et_username.setVisibility(View.GONE);
                                    et_password.setVisibility(View.GONE);
                                    et_cpassword.setVisibility(View.GONE);
                                    btn_register.setVisibility(View.GONE);
                                    btn_ver_register.setVisibility(View.VISIBLE);
                                    et_name.setVisibility(View.VISIBLE);
                                    textView.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), "Пользователь зарегестрирован", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Что то пошло не так", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Введение пароли не совпадают", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Введите почту и пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_ver_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String name = et_name.getText().toString();
                com.example.hl_3.models.User newUser = new com.example.hl_3.models.User(username, name);
                mDataBase.push().setValue(newUser);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}