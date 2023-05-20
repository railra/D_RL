package com.example.hl_3.utilities;

public class TaskListItem {

    String idTask, nameTask, startTask, endTask;
    int amountTask;


    public String getStartTask() {
        return startTask;
    }

    public void setStartTask(String startTask) {
        this.startTask = startTask;
    }

    public String getEndTask() {
        return endTask;
    }

    public void setEndTask(String endTask) {
        this.endTask = endTask;
    }

    public String getNameTask() {
        return nameTask;
    }
    public String getIdTask() {
        return idTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }
    public void setIdTask(String idTask) {
        this.idTask = idTask;
    }

    public int getAmountTask() {
        return amountTask;
    }

    public void setAmountTask(int amountTask) {
        this.amountTask = amountTask;
    }

}
