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
import com.example.karmbhog.objects.EmployeeReport;

import java.util.ArrayList;

public class CompanyReportAdapter extends ArrayAdapter<EmployeeReport> {

    Context context;
    ArrayList<EmployeeReport> employeeReportArrayList;

    public CompanyReportAdapter(Context context, ArrayList<EmployeeReport> employeeReportArrayList) {
        super(context, android.R.layout.simple_list_item_1, employeeReportArrayList);
        this.context = context;
        this.employeeReportArrayList = employeeReportArrayList;
    }

    @Override
    public int getCount() {
        // Since you want to display only one static item, return 1
        return employeeReportArrayList.size();
    }

    @Override
    public EmployeeReport getItem(int position) {
        // Return null or any object you want to represent the static item
        return employeeReportArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Return any unique ID for the item
        return position;
    }

    @NonNull
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflating layout for each row
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.report_row, parent, false);
        }

        EmployeeReport employeeReport = employeeReportArrayList.get(position);

        TextView name = convertView.findViewById(R.id.name);
        TextView workHours = convertView.findViewById(R.id.workHours);

        name.setText(employeeReport.getWorkerName());
        workHours.setText(employeeReport.getAvgWorkPerDay() + " hours");


        // Setting static data
//        name.setText("HiteshBhai");
//        age.setText("2 hour Daily - 1 Hour Extra");

        return convertView;
    }
}
