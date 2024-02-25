package com.example.karmbhog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;
import com.example.karmbhog.objects.EmployeeFeedback;
import com.example.karmbhog.objects.EmployeeReport;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Give_feedback extends AppCompatActivity {
    private ManagePreferences managePreferences;

    RelativeLayout submitBtn, cancelBtn;
    EditText workerMobNo;
    RatingBar rating;
    Spinner workerName;

    TextView errorTxt;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KarmBhog");

        errorTxt = findViewById(R.id.errorTxt);

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_COMPANY_PREFERENCE_NAME);
        String comEmail = managePreferences.getString(Constants.KEY_COMPANY_EMAIL);
        String comCity = managePreferences.getString(Constants.KEY_COMPANY_CITY);

        //set spinner
        workerName = findViewById(R.id.workerNameSpinner);

        colRef_company.document(comEmail)
                .collection(Constants.KEY_COMPANY_WORK_COL)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        try {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Log.d("Document : ", documentSnapshot.get(Constants.KEY_COMPANY_WORK_NAME).toString());
                                    String workName = documentSnapshot.getString(Constants.KEY_COMPANY_WORK_NAME);

                                    workerName.setAdapter(setSpinner1(comCity, workName));
                                }
                            }
                        }
                        catch (NullPointerException npe) {
                            Log.d("n p e", npe.getMessage());
                        }
                    }
                });


        //mob no field
        final String[] mngrEmail = new String[1];
        workerMobNo = findViewById(R.id.mobNoETxt);
        workerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String workerNameTemp = workerName.getSelectedItem().toString();

                colRef_manager.whereEqualTo(Constants.KEY_KITCHEN_MANAGER_CITY, comCity)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (queryDocumentSnapshots != null) {
                                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        mngrEmail[0] = documentSnapshot.getString(Constants.KEY_KITCHEN_MANAGER_EMAIL);

                                        setEditText(mngrEmail[0], workerNameTemp, workerMobNo);
                                    }
                                }
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //rating field
        rating = findViewById(R.id.ratingBar);

        //buttons
        cancelBtn = findViewById(R.id.cancel);
        submitBtn = findViewById(R.id.sub);

        //listeners
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Give_feedback.this, Company_dashboard.class));
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String workerNameStr = workerName.getSelectedItem().toString();
                String workerMobNoStr = workerMobNo.getText().toString();
                String ratingStr = String.valueOf(rating.getRating());

                //match mngr city with company city
                if(isRegisterDetailsFilled(errorTxt, workerNameStr, workerMobNoStr, ratingStr)) {
                    EmployeeFeedback employeeFeedback = new EmployeeFeedback(workerNameStr, workerMobNoStr, ratingStr);

                    //set feedback logic
                    colRef_manager.document(mngrEmail[0])
                            .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                            .whereEqualTo(Constants.KEY_KITCHEN_MANAGER_EMPLOYEE_NAME, workerNameStr)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if(queryDocumentSnapshots!=null) {
                                        for(DocumentSnapshot documentSnapshot1: queryDocumentSnapshots) {
                                            String userName = documentSnapshot1.getId();

                                            //main logic to set data
                                            setFeedback(mngrEmail[0], userName, employeeFeedback);
                                        }
                                    }
                                }
                            });

//--------------------------------------------------------------------------------------------------------------------------------
//                    colRef_manager.whereEqualTo(Constants.KEY_KITCHEN_MANAGER_CITY, comCity)
//                            .get()
//                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                                @Override
//                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                    if (queryDocumentSnapshots != null) {
//                                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                                            String mngrEmail = documentSnapshot.getString(Constants.KEY_KITCHEN_MANAGER_EMAIL);
//
//                                            //set feedback logic
//                                            colRef_manager.document(mngrEmail)
//                                                    .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
//                                                    .whereEqualTo(Constants.KEY_KITCHEN_MANAGER_EMPLOYEE_NAME, workerNameStr)
//                                                    .get()
//                                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                                                        @Override
//                                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                                            if(queryDocumentSnapshots!=null) {
//                                                                for(DocumentSnapshot documentSnapshot1: queryDocumentSnapshots) {
//                                                                    String userName = documentSnapshot1.getId();
//
//                                                                    //main logic to set data
//                                                                    setFeedback(mngrEmail, userName, employeeFeedback);
//                                                                }
//                                                            }
//                                                        }
//                                                    });
//                                        }
//                                    }
//                                }
//                            });
//---------------------------------------------------------------------------------------------------------------------------------
                }
            }
        });
    }

    private void setEditText(String mngrEmail, String workerName, EditText mobNo) {
        colRef_manager.document(mngrEmail)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                .whereEqualTo(Constants.KEY_KITCHEN_MANAGER_EMPLOYEE_NAME, workerName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                                mobNo.setText(documentSnapshot.getString(Constants.KEY_KITCHEN_MANAGER_EMPLOYEE_MOB_NO));
                            }
                        }
                    }
                });
    }

    private void setFeedback(String mngrEmail, String userName, EmployeeFeedback employeeFeedback) {
        //set feedback logic
        colRef_manager.document(mngrEmail)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                .document(userName)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_FEEDBACK_COL)
                .add(employeeFeedback)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Feedback Submitted",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Give_feedback.this, Company_dashboard.class));
                        finish();
                    }
                });
    }

    private ArrayAdapter<String> setSpinner1(String comCity, String workName) {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Give_feedback.this, android.R.layout.simple_spinner_dropdown_item, arrayList);

        //match mngr city with company city
        colRef_manager.whereEqualTo(Constants.KEY_KITCHEN_MANAGER_CITY, comCity)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String mngrEmail;
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            mngrEmail = documentSnapshot.getString(Constants.KEY_KITCHEN_MANAGER_EMAIL);

                            //spinner 1
                            colRef_manager.document(mngrEmail)
                                    .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                                    .whereEqualTo(Constants.KEY_KITCHEN_MANAGER_EMPLOYEE_WORK_NAME, workName)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                                                String workerName = documentSnapshot.getString(Constants.KEY_KITCHEN_MANAGER_EMPLOYEE_NAME);
                                                arrayList.add(workerName);
                                            }
                                            arrayAdapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                    }
                });

        return arrayAdapter;
    }

    private void setErrorTxt(TextView errorTxt, String message) {
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(message);
    }

    //  ...method to verify input details...
    private Boolean isRegisterDetailsFilled(TextView errorTxt, String workerNameStr, String mobNoStr, String ratingStr) {
        if(workerNameStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please select a worker name");
            return false;
        }

        else if(mobNoStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a mobile number");
            return false;
        }

        else if(ratingStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter rating of worker");
            return false;
        }

        else {
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(Give_feedback.this, Company_dashboard.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}