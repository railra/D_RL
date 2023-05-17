package com.example.hl_3.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hl_3.R;
import com.example.hl_3.utilities.TaskListItem;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<TaskListItem> {

    private LayoutInflater inflater;
    private List<TaskListItem> listItemAd;
    private Context context;


    public CustomArrayAdapter(@NonNull Context context, int resource, List<TaskListItem> listItemAd, LayoutInflater inflater) {
        super(context, resource, listItemAd);
        this.inflater = inflater;
        this.listItemAd = listItemAd;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        TaskListItem listItemTask = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.list_item, null, false);
        viewHolder = new ViewHolder();
        viewHolder.nameTask = convertView.findViewById(R.id.name_task);
        viewHolder.amountTask = convertView.findViewById(R.id.amount_task);
        viewHolder.nameTask.setText(listItemTask.getNameTask());
        viewHolder.amountTask.setText(String.valueOf(listItemTask.getAmountTask()));
        return convertView;
    }

    private class ViewHolder {
        TextView nameTask;
        TextView amountTask;
    }
}
