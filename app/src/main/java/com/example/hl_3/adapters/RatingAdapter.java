package com.example.hl_3.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hl_3.R;
import com.example.hl_3.fragments.RatingFragment;
import com.example.hl_3.utilities.TaskListItem;
import com.example.hl_3.utilities.UserListItem;

import java.util.ArrayList;
import java.util.List;

public class RatingAdapter extends ArrayAdapter<UserListItem>
{


    private LayoutInflater inflater;
    private List<UserListItem> listItemAd = new ArrayList<>();
    private Context context;
    public RatingAdapter(@NonNull Context context, int resource, List<UserListItem> listItemAd, LayoutInflater inflater) {
        super(context, resource, listItemAd);
        this.inflater = inflater;
        this.listItemAd = listItemAd;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        RatingAdapter.ViewHolder viewHolder;
        UserListItem listItemUser = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.user_list_item, null, false);
        viewHolder = new RatingAdapter.ViewHolder();
        viewHolder.nameUser = convertView.findViewById(R.id.name_user);
        viewHolder.amountUser = convertView.findViewById(R.id.amount_user);
        viewHolder.nameUser.setText(listItemUser.getNameUser());
        viewHolder.amountUser.setText(String.valueOf(listItemUser.getAmountUser()));

        return convertView;
    }

    private class ViewHolder {
        TextView nameUser, amountUser;
    }
}
