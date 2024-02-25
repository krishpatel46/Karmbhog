package com.example.karmbhog.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.karmbhog.R;
import com.example.karmbhog.objects.Events;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Events> {

    Context context;
    ArrayList<Events> eventsArrayList;

    public EventAdapter(Context context, ArrayList<Events> eventsArrayList) {
        super(context, android.R.layout.simple_list_item_1, eventsArrayList);

        this.context = context;
        this.eventsArrayList = eventsArrayList;
    }

    @Override
    public int getCount() {
        // Since you want to display only one static item, return 1
        return eventsArrayList.size();
    }

    @Override
    public Events getItem(int position) {
        // Return null or any object you want to represent the static item
        return eventsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Return any unique ID for the item
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflating layout for each row
        convertView = LayoutInflater.from(context).inflate(R.layout.event_row, parent, false);

        Events events = eventsArrayList.get(position);

        TextView name = convertView.findViewById(R.id.eventNameTxt);
        TextView date = convertView.findViewById(R.id.eventDateTxt);

        name.setText(events.getEventName());
        date.setText(longToDate(events.getEventDate()));

        return convertView;
    }

    private String longToDate(long date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
        //this returns date string in format "dd-MM-yyyy"
    }
}
