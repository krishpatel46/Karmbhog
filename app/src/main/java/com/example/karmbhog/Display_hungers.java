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
import com.example.karmbhog.objects.WorkerRequests;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Display_hungers extends AppCompatActivity {

    private ManagePreferences managePreferences;
    Button b;

    ArrayList<HungerEmployees> hungerEmployeesArrayList;
    HungerAdapter hungerAdapter;

    ListView lv_note;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_hungers);

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_KITCHEN_MANAGER_PREFERENCE_NAME);
        String kitchenMngrEmail = managePreferences.getString(Constants.KEY_KITCHEN_MANAGER_EMAIL);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KarmBhog");

        Log.d("Company Email: ", kitchenMngrEmail);

        lv_note = findViewById(R.id.lv_note);
        //main logic: display content
        loadNoteDataInListView(kitchenMngrEmail);


        b = findViewById(R.id.Button11);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Display_hungers.this,Dashboard.class));
                finish();
            }
        });
    }

    private void loadNoteDataInListView(String kitchenMngrEmail) {
        hungerEmployeesArrayList = new ArrayList<>();
        hungerAdapter = new HungerAdapter(this, hungerEmployeesArrayList);

        colRef_manager.document(kitchenMngrEmail).collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
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

                                hungerAdapter.notifyDataSetChanged();
                            }
                        }
                        catch (NullPointerException npe) {
                            Log.d("n p e", npe.getMessage());
                        }
                    }
                });

        lv_note.setAdapter((ListAdapter) hungerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuforadd, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.buttonadd2) {
            startActivity(new Intent(Display_hungers.this, Register_Hunger.class));
            finish();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            //startActivity(new Intent(Display_hungers.this, Dashboard.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}