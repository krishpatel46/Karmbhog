package com.example.karmbhog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.karmbhog.adapters.FeedbackAdapter;
import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;
import com.example.karmbhog.objects.EmployeeFeedback;
import com.example.karmbhog.objects.EmployeeReport;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Feedback extends AppCompatActivity {

    private ManagePreferences managePreferences;

    Button b;
    FeedbackAdapter h;
    ArrayList<EmployeeFeedback> employeeFeedbackArrayList;

    ListView lv_note;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    //CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KarmBhog");
        lv_note = findViewById(R.id.lv_note);

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_KITCHEN_MANAGER_PREFERENCE_NAME);
        String mngrEmail = managePreferences.getString(Constants.KEY_KITCHEN_MANAGER_EMAIL);

        //main logic
        colRef_manager.document(mngrEmail)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots!=null) {
                            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                                String userName = documentSnapshot.getId();
                                loadNoteDataInListView(mngrEmail, userName);
                            }
                        }
                    }
                });


        //home button
        b = findViewById(R.id.Button11);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Feedback.this,Dashboard.class));
                finish();
            }
        });
    }

    private void loadNoteDataInListView(String mngrEmail, String userName) {
        employeeFeedbackArrayList = new ArrayList<>();
        h = new FeedbackAdapter(this, employeeFeedbackArrayList);

        colRef_manager.document(mngrEmail)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                .document(userName)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_FEEDBACK_COL)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots!=null) {
                            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                                employeeFeedbackArrayList.add(documentSnapshot.toObject(EmployeeFeedback.class));
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
            //startActivity(new Intent(Feedback.this, Dashboard.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}