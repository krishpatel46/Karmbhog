package com.example.karmbhog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;
import com.example.karmbhog.objects.WorkerRequests;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Req_of_worker extends AppCompatActivity {

    private ManagePreferences managePreferences;

    RelativeLayout reqBtn, cancelBtn;
    TextView errorTxt;
    EditText managerName, managerMobNo, workAddress, workName, workersNeeded;
    String managerNameStr, managerMobNoStr, workAddressStr, workNameStr;
    Integer workersNeededNum;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_of_worker);

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_COMPANY_PREFERENCE_NAME);
        String comEmail = managePreferences.getString(Constants.KEY_COMPANY_EMAIL);

        //buttons
        reqBtn = findViewById(R.id.reqBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        //error text
        errorTxt = findViewById(R.id.errorTxt);

        //input data
        managerName = findViewById(R.id.managerName);
        managerMobNo = findViewById(R.id.managerMobNo);
        workAddress = findViewById(R.id.workAddress);
        workName = findViewById(R.id.workName);
        workersNeeded = findViewById(R.id.workersNeededNum);

        //listeners
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Req_of_worker.this, Company_dashboard.class));
                finish();
            }
        });
        reqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managerNameStr = managerName.getText().toString();
                managerMobNoStr = managerMobNo.getText().toString();
                workAddressStr = workAddress.getText().toString();
                workNameStr = workName.getText().toString();
                workersNeededNum = Integer.parseInt(workersNeeded.getText().toString());

                if(isRegisterDetailsFilled(errorTxt, managerNameStr, managerMobNoStr, workAddressStr, workNameStr, workersNeededNum)) {
                    WorkerRequests workDetails = new WorkerRequests(managerNameStr, managerMobNoStr, workAddressStr, workNameStr, workersNeededNum);

                    colRef_company.document(comEmail).collection(Constants.KEY_COMPANY_WORK_COL)
                            .document(workNameStr)
                            .set(workDetails)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Workers Request sent",Toast.LENGTH_LONG).show();
                                    //startActivity(new Intent(Req_of_worker.this, Company_dashboard.class));
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
    private Boolean isRegisterDetailsFilled(TextView errorTxt, String managerNameStr, String managerMobNoStr, String workAddressStr, String workNameStr, Integer workersNeededNum) {
        if(managerNameStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a name");
            return false;
        }

        else if(managerMobNoStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a mobile number");

            return false;
        }

        else if (workAddressStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a valid address");
            return false;
        }

        else if(workNameStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a work name");
            return false;
        }

        else if(workersNeededNum==null) {
            setErrorTxt(errorTxt, "*Please enter number of workers");
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
            //startActivity(new Intent(Req_of_worker.this, Company_dashboard.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}