package com.example.hl_3.models;

public class Task
{
    public String id, name, amount, user;//Это нужно отобразить? у меня данные из firebase, но тип такой

    public Task() {
    }

    public Task(String id, String name, String amount, String user) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.user = user;
    }
}
