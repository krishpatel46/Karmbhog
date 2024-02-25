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

import com.example.karmbhog.adapters.HungerAdapter;
import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;
import com.example.karmbhog.objects.HungerEmployees;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Workers extends AppCompatActivity {
    private ManagePreferences managePreferences;
    Button b;
    HungerAdapter h;
    ArrayList<HungerEmployees> hungerEmployeesArrayList;

    ListView lv_note;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_COMPANY_PREFERENCE_NAME);
        String comCity = managePreferences.getString(Constants.KEY_COMPANY_CITY);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KarmBhog");

        //Log.d("Company Email: ", kitchenMngrEmail);

        //main logic: display content
        lv_note = findViewById(R.id.lv_note);
        colRef_manager.whereEqualTo(Constants.KEY_KITCHEN_MANAGER_CITY, comCity)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots!=null) {
                            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                                String mngrEmail = documentSnapshot.getId();
                                Log.d("comEmail : ", mngrEmail);

                                //main logic: display content
                                loadNoteDataInListView(mngrEmail);
                            }
                        }
                    }
                });


        b = findViewById(R.id.Button11);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Workers.this, Company_dashboard.class));
                finish();
            }
        });
    }

    private void loadNoteDataInListView(String kitchenMngrEmail) {
        hungerEmployeesArrayList = new ArrayList<>();
        h = new HungerAdapter(this, hungerEmployeesArrayList);

        colRef_manager.document(kitchenMngrEmail)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        try {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Log.d("Document : ", documentSnapshot.get(Constants.KEY_KITCHEN_MANAGER_EMPLOYEE_NAME).toString());
                                    hungerEmployeesArrayList.add(documentSnapshot.toObject(HungerEmployees.class));
                                }

                                h.notifyDataSetChanged();
                            }
                        }
                        catch (NullPointerException npe) {
                            Log.d("n p e", npe.getMessage());
                        }
                    }
                });

        lv_note.setAdapter((ListAdapter) h);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //startActivity(new Intent(Workers.this, Company_dashboard.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}