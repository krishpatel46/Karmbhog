package com.example.karmbhog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.karmbhog.adapters.CompanyReportAdapter;
import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;
import com.example.karmbhog.objects.EmployeeReport;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Work_details extends AppCompatActivity {

    private ManagePreferences managePreferences;
    CompanyReportAdapter companyReportAdapter;
    ArrayList<EmployeeReport> employeeReportArrayList;

    ListView lv_note;
    Button b;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KarmBhog");
        lv_note = findViewById(R.id.lv_note);

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_COMPANY_PREFERENCE_NAME);
        String comEmail = managePreferences.getString(Constants.KEY_COMPANY_EMAIL);
        String comCity = managePreferences.getString(Constants.KEY_COMPANY_CITY);

        //match city with kitchen manager's city
        final String[] mngrEmail = new String[1];
        colRef_manager.whereEqualTo(Constants.KEY_KITCHEN_MANAGER_CITY, comCity)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null) {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                mngrEmail[0] = documentSnapshot.getString(Constants.KEY_KITCHEN_MANAGER_EMAIL);

                                //goto employees
                                colRef_manager.document(mngrEmail[0])
                                        .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                if(queryDocumentSnapshots!=null) {
                                                    for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                                                        String userName = documentSnapshot.getId();

                                                        //main logic
                                                        loadNoteDataInListView(userName, mngrEmail[0]);
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });

        //home btn listener
        b = findViewById(R.id.Button11);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Work_details.this, Company_dashboard.class));
            }
        });
    }

    private void loadNoteDataInListView(String userName, String mngrEmail) {
        employeeReportArrayList = new ArrayList<>();
        companyReportAdapter = new CompanyReportAdapter(this, employeeReportArrayList);

        colRef_manager.document(mngrEmail)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                .document(userName)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_REPORT_COL)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots!=null) {
                            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                                employeeReportArrayList.add(documentSnapshot.toObject(EmployeeReport.class));
                            }
                            companyReportAdapter.notifyDataSetChanged();
                        }
                    }
                });

        lv_note.setAdapter((ListAdapter) companyReportAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuforadd, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.buttonadd2) {
            startActivity(new Intent(Work_details.this, Report.class));
            finish();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            //startActivity(new Intent(Work_details.this, Company_dashboard.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}