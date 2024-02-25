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

import com.example.karmbhog.adapters.CompanyAdapter;
import com.example.karmbhog.adapters.WorkReqAdapter;
import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;
import com.example.karmbhog.objects.Company;
import com.example.karmbhog.objects.WorkerRequests;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Company_detail extends AppCompatActivity {

    private ManagePreferences managePreferences;
    Button b;
    CompanyAdapter companyAdapter;
    ArrayList<Company> companyArrayList;

    ListView lv_note;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_KITCHEN_MANAGER_PREFERENCE_NAME);
        String mngrCity = managePreferences.getString(Constants.KEY_KITCHEN_MANAGER_CITY);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Zero Hunger");
        lv_note = findViewById(R.id.lv_note);

        Log.d("mngrCity : ", mngrCity);
        loadNoteDataInListView(mngrCity);

        b = findViewById(R.id.Button11);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Company_detail.this, Dashboard.class));
                finish();
            }
        });
    }

    private void loadNoteDataInListView(String mngrCity) {
        companyArrayList = new ArrayList<>();
        companyAdapter = new CompanyAdapter(this, companyArrayList);

        colRef_company.whereEqualTo(Constants.KEY_COMPANY_CITY, mngrCity)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        try {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Log.d("Document : ", documentSnapshot.get(Constants.KEY_COMPANY_NAME).toString());
                                    companyArrayList.add(documentSnapshot.toObject(Company.class));
                                }

                                companyAdapter.notifyDataSetChanged();
                            }
                        }
                        catch (NullPointerException npe) {
                            Log.d("n p e", npe.getMessage());
                        }
                    }
                });

        lv_note.setAdapter((ListAdapter) companyAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //startActivity(new Intent(Company_detail.this, Dashboard.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}