package com.example.karmbhog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.karmbhog.R;
import com.example.karmbhog.objects.WorkerRequests;

import java.util.ArrayList;

public class WorkReqAdapter extends ArrayAdapter<WorkerRequests> {

    Context context;
    ArrayList<WorkerRequests> workerRequestsArrayList;

    public WorkReqAdapter(Context context, ArrayList<WorkerRequests> workerRequestsArrayList) {
        super(context, android.R.layout.simple_list_item_1, workerRequestsArrayList);

        this.context = context;
        this.workerRequestsArrayList = workerRequestsArrayList;
    }

    @Override
    public int getCount() {
        // Since you want to display only one static item, return 1
        return workerRequestsArrayList.size();
    }

    @Override
    public WorkerRequests getItem(int position) {
        // Return null or any object you want to represent the static item
        return workerRequestsArrayList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.work_req_row, parent, false);
        }
        WorkerRequests workerRequests = workerRequestsArrayList.get(position);

        if(workerRequests != null) {
            TextView name = convertView.findViewById(R.id.name);
            TextView no = convertView.findViewById(R.id.no);

            name.setText(workerRequests.getWorkName());
            no.setText(String.valueOf(workerRequests.getWorkersNeeded()));
        }

        // Setting static data
//        name.setText("Matrix");
//        no.setText("10 persons");

        return convertView;
    }
}
