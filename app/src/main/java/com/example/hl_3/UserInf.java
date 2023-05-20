package com.example.hl_3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserInf extends AppCompatActivity
{
    private TextView tvName, tvScore, tvEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        init();
        getIntentMain();
    }
    private void init()
    {
        tvName = findViewById(R.id.tvName);
        tvScore = findViewById(R.id.tvScore);
        tvEmail = findViewById(R.id.tvEmail);
    }
    private void getIntentMain()
    {
        Intent i = getIntent();
        if(i != null)
        {
            tvName.setText(i.getStringExtra("user_name"));
            tvScore.setText(i.getStringExtra("user_score"));
            tvEmail.setText(i.getStringExtra("user_email"));
        }
    }
}
