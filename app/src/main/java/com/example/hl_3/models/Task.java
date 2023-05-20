package com.example.hl_3.models;

public class Task
{
    public String id, name, start, end, user;
    public int amount;

    public Task() {
    }

    public Task(String id, String name, int amount, String start, String end, String user) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.start = start;
        this.end = end;
        this.user = user;
    }

}
