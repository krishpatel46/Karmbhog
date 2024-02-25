package com.example.karmbhog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.objects.Events;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class Register_event extends AppCompatActivity {

    RelativeLayout submitBtn, cancelBtn;
    TextView errorTxt;
    EditText eventName, mobNo, address;
    Spinner eventCity;
    CalendarView eventDate;
    String eventNameStr, mobNoStr, addressStr, eventCityStr;
    long eventDateDate;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        cancelBtn = findViewById(R.id.cancel);
        submitBtn = findViewById(R.id.sub);

        errorTxt = findViewById(R.id.errorTxt);

        //input fields
        eventName = findViewById(R.id.eventNameETxt);
        mobNo = findViewById(R.id.mobNoETxt);

        eventCity = findViewById(R.id.cityNameETxt);
        eventCity.setAdapter(setSpinner1());

        address = findViewById(R.id.addressETxt);
        eventDate = findViewById(R.id.datePickr);


        //listeners
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Register_event.this, User_dashboard.class));
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventNameStr = eventName.getText().toString();
                mobNoStr = mobNo.getText().toString();
                addressStr = address.getText().toString();
                eventCityStr = eventCity.getSelectedItem().toString();
                //eventDateDate = LocalDate.of(eventDate.getYear(), (eventDate.getMonth()+1), eventDate.getDayOfMonth());
                eventDateDate = eventDate.getDate();

                if(isRegisterDetailsFilled(errorTxt, eventNameStr, mobNoStr, addressStr, eventCityStr)) {
                    Events event = new Events(eventNameStr, mobNoStr, addressStr, eventCityStr, eventDateDate);

                    //logic to register event
                    colRef_manager.whereEqualTo(Constants.KEY_KITCHEN_MANAGER_CITY, eventCityStr)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value!=null) {
                                        for(DocumentSnapshot documentSnapshot: value) {
                                            String mngrEmail = documentSnapshot.getId();
                                            Log.d("mngrEmail : ", mngrEmail);

                                            //main logic: display content
                                            setData(mngrEmail, event);
                                        }
                                    }
                                }
                            });
                }
            }
        });
    }

    private ArrayAdapter<String> setSpinner1() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Register_event.this, android.R.layout.simple_spinner_dropdown_item, arrayList);

        //spinner 1
        colRef_manager.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            String mngrCity = documentSnapshot.get(Constants.KEY_KITCHEN_MANAGER_CITY).toString();
                            arrayList.add(mngrCity);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                });

        return arrayAdapter;
    }

    private void setData(String mngrEmail, Events event) {
        colRef_manager.document(mngrEmail)
                .collection(Constants.KEY_KITCHEN_MANAGER_EVENTS_COL)
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Event Registered", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(Register_event.this, User_dashboard.class));
                        finish();
                    }
                });

    }

    private void setErrorTxt(TextView errorTxt, String message) {
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(message);
    }

    //  ...method to verify input details...
    private Boolean isRegisterDetailsFilled(TextView errorTxt, String eventNameStr, String mobNoStr, String addressStr, String eventCityStr) {
        if(eventNameStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a event name");
            return false;
        }

        else if(mobNoStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a mobile number");
            return false;
        }

        else if(addressStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter an address");

            return false;
        }

        else if(eventCityStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please select a city");
            return false;
        }

        else {
            return true;
        }
    }

    //------------------------------------------------------------------------------------------------------------
    //long to date conversion function
    private String longToDate(long date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
        //this returns date string in format "dd-MM-yyyy"
    }
    //------------------------------------------------------------------------------------------------------------
}