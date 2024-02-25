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

import com.example.karmbhog.adapters.EventAdapter;
import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;
import com.example.karmbhog.objects.Events;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Display_events extends AppCompatActivity {

    private ManagePreferences managePreferences;

    Button b;
    EventAdapter h;
    ArrayList<Events> eventsArrayList;

    ListView lv_note;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KarmBhog");

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_KITCHEN_MANAGER_PREFERENCE_NAME);
        String mngrCity = managePreferences.getString(Constants.KEY_KITCHEN_MANAGER_CITY);

        lv_note = findViewById(R.id.lv_note);

        //main logic
        colRef_manager.whereEqualTo(Constants.KEY_KITCHEN_MANAGER_CITY, mngrCity)
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
        //loadNoteDataInListView(mngrCity);

        b = findViewById(R.id.Button11);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Display_events.this,Dashboard.class));
                finish();
            }
        });
    }

    private void loadNoteDataInListView(String mngrEmail) {
        eventsArrayList = new ArrayList<>();
        h = new EventAdapter(this, eventsArrayList);

        colRef_manager.document(mngrEmail)
                .collection(Constants.KEY_KITCHEN_MANAGER_EVENTS_COL)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null) {
                            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                                Log.d("event doc : ", documentSnapshot.getString("eventName").toString());
                                eventsArrayList.add(documentSnapshot.toObject(Events.class));
                            }
                            h.notifyDataSetChanged();
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
            //startActivity(new Intent(Display_events.this, Dashboard.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}