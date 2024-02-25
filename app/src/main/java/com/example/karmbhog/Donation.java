package com.example.karmbhog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.objects.Donor;
import com.example.karmbhog.objects.Events;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Donation extends AppCompatActivity {

    RelativeLayout cancelBtn, submitBtn;
    TextView errorTxt;
    EditText donorName, donorMobNo, donorAddress, paymentRefNo;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_donor = db.collection(Constants.KEY_DONORS_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KarmBhog");

        //input fields
        donorAddress = findViewById(R.id.donorAddressETxt);
        donorName = findViewById(R.id.donorNameETxt);
        donorMobNo = findViewById(R.id.donorMobNoETxt);
        paymentRefNo = findViewById(R.id.paymentReferralNoETxt);

        errorTxt = findViewById(R.id.errorTxt);

        //buttons
        cancelBtn = findViewById(R.id.cancel);
        submitBtn = findViewById(R.id.sub);

        //listeners
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Donation.this, User_dashboard.class));
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String donorNameStr = donorName.getText().toString();
                String donorMobNoStr = donorMobNo.getText().toString();
                String donorAddressStr = donorAddress.getText().toString();
                String paymentRefNumStr = paymentRefNo.getText().toString();

                if(isRegisterDetailsFilled(errorTxt, donorNameStr, donorMobNoStr, donorAddressStr, paymentRefNumStr)) {
                    Donor donor = new Donor(donorNameStr, donorMobNoStr, donorAddressStr, paymentRefNumStr);

                    //logic to register event
                    colRef_donor.add(donor)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(),"Payment Done", Toast.LENGTH_LONG).show();
                                    //startActivity(new Intent(Donation.this, User_dashboard.class));
                                    finish();
                                }
                            });
                }
            }
        });
    }

    private void setErrorTxt(TextView errorTxt, String message) {
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(message);
    }

    //  ...method to verify input details...
    private Boolean isRegisterDetailsFilled(TextView errorTxt, String donorNameStr, String mobNoStr, String addressStr, String paymentRefStr) {
//        if(eventNameStr.isEmpty()) {
//            setErrorTxt(errorTxt, "*Please enter your name");
//            return false;
//        }

        if(mobNoStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a mobile number");
            return false;
        }

//        else if(addressStr.isEmpty()) {
//            setErrorTxt(errorTxt, "*Please enter an address");
//
//            return false;
//        }

        else if(paymentRefStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter your payment ref number");
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
            //startActivity(new Intent(Donation.this, User_dashboard.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}