package com.example.karmbhog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.karmbhog.Company_detail;
import com.example.karmbhog.R;
import com.example.karmbhog.objects.Company;

import java.util.ArrayList;

public class CompanyAdapter extends ArrayAdapter<Company> {

    Context context;
    ArrayList<Company> companyArrayList;

    public CompanyAdapter(Context context, ArrayList<Company> companyArrayList) {
        super(context, android.R.layout.simple_list_item_1, companyArrayList);

        this.context = context;
        this.companyArrayList = companyArrayList;
    }

    @Override
    public int getCount() {
        // Since you want to display only one static item, return 1
        return companyArrayList.size();
    }

    @Override
    public Company getItem(int position) {
        // Return null or any object you want to represent the static item
        return companyArrayList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.company_row, parent, false);
        }

        Company company = companyArrayList.get(position);

        if(company != null) {
            TextView comName = convertView.findViewById(R.id.comNameTxt);
            TextView comEmail = convertView.findViewById(R.id.comEmailTxt);

            comName.setText(company.getCompanyName());
            comEmail.setText(company.getCompanyEmail());
        }

        // Setting static data
//        name.setText("Matrix");
//        no.setText("Mavdi,Rajkot");

        return convertView;
    }
}
