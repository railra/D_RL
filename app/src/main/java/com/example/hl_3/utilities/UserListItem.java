package com.example.hl_3.utilities;

public class UserListItem implements Comparable<UserListItem>
{
    String nameUser;
    int amountUser;



    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getAmountUser() {
        return amountUser;
    }

    public void setAmountUser(int amountUser) {
        this.amountUser = amountUser;
    }

    @Override
    public int compareTo(UserListItem o)
    {
        return Double.compare(this.amountUser, o.amountUser);
    }
}
