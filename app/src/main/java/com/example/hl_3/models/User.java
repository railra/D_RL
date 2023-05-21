package com.example.hl_3.models;

public class User
{
    public String email, name, status;
    public int score;


    public User() {
    }

    public User(String email, String name, String status, int score) {
        this.email = email;
        this.name = name;
        this.score = score;
        this.status = status;
    }


}
