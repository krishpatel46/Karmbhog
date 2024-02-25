package com.example.karmbhog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.karmbhog.R;
import com.example.karmbhog.objects.HungerEmployees;

import java.util.ArrayList;

public class HungerAdapter extends ArrayAdapter<HungerEmployees> {

    Context context;
    ArrayList<HungerEmployees> hungerEmployeesArrayList;

    public HungerAdapter(Context context, ArrayList<HungerEmployees> hungerEmployeesArrayList) {
        super(context, android.R.layout.simple_list_item_1, hungerEmployeesArrayList);

        this.context = context;
        this.hungerEmployeesArrayList = hungerEmployeesArrayList;
    }

    @Override
    public int getCount() {
        // Since you want to display only one static item, return 1
        return hungerEmployeesArrayList.size();
    }

    @Override
    public HungerEmployees getItem(int position) {
        // Return null or any object you want to represent the static item
        return hungerEmployeesArrayList.get(position);
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
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hunger_row, parent, false);
        }

        HungerEmployees hungerEmployees = hungerEmployeesArrayList.get(position);

        if(hungerEmployees != null) {
            TextView name = convertView.findViewById(R.id.nameTxt);
            TextView age = convertView.findViewById(R.id.ageTxt);
            TextView empWorkNameTxt = convertView.findViewById(R.id.empWorkNameTxt);

            name.setText(hungerEmployees.getEmployeeName());
            age.setText(String.valueOf(hungerEmployees.getEmployeeAge()));
            empWorkNameTxt.setText(hungerEmployees.getEmployeeWorkName());
        }

        // Setting static data
//        name.setText("HiteshBhai");
//        empWorkNameTxt.setText("Kneading");

        return convertView;
    }
}
