package com.example.karmbhog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.karmbhog.R;
import com.example.karmbhog.objects.EmployeeFeedback;
import com.example.karmbhog.objects.EmployeeReport;

import java.util.ArrayList;

public class FeedbackAdapter extends ArrayAdapter<EmployeeFeedback> {

    Context context;
    ArrayList<EmployeeFeedback> employeeFeedbackArrayList;

    public FeedbackAdapter(Context context, ArrayList<EmployeeFeedback> employeeFeedbackArrayList) {
        super(context, android.R.layout.simple_list_item_1, employeeFeedbackArrayList);

        this.context = context;
        this.employeeFeedbackArrayList = employeeFeedbackArrayList;
    }

    @Override
    public int getCount() {
        // Since you want to display only one static item, return 1
        return employeeFeedbackArrayList.size();
    }

    @Override
    public EmployeeFeedback getItem(int position) {
        // Return null or any object you want to represent the static item
        return employeeFeedbackArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Return any unique ID for the item
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflating layout for each row
        convertView = LayoutInflater.from(context).inflate(R.layout.feedback_row, parent, false);

        EmployeeFeedback employeeFeedback = employeeFeedbackArrayList.get(position);

        TextView name = convertView.findViewById(R.id.name);
        RatingBar rate = convertView.findViewById(R.id.rate);

        name.setText(employeeFeedback.getWorkerName());
        float rating = Float.parseFloat(employeeFeedback.getRating());
        rate.setRating(rating);

        // Setting static data
//        name.setText("HiteshBhai");
//        float rating = 3.5f; // Set your desired rating here
//        rate.setRating(rating);

        return convertView;
    }
}
