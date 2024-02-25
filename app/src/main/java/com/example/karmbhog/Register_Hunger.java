package com.example.karmbhog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;
import com.example.karmbhog.objects.HungerEmployees;
import com.example.karmbhog.objects.KitchenManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class Register_Hunger extends AppCompatActivity {

    private ManagePreferences managePreferences;

    TextView errorTxt;
    RelativeLayout submitBtn, cancelBtn;
    EditText userName, empName, empAge, empMobNo, empAddress;
    Spinner empComName, empWorkName;
    String userNameStr, empNameStr, empMobNoStr, empAddressStr, empComNameStr, empWorkNameStr;
    Integer  empAgeNum;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hunger);

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_KITCHEN_MANAGER_PREFERENCE_NAME);
        String mngrEmail = managePreferences.getString(Constants.KEY_KITCHEN_MANAGER_EMAIL);


        //input data
        userName = findViewById(R.id.empUserName);
        empName = findViewById(R.id.empName);
        empAge = findViewById(R.id.empAge);
        empMobNo = findViewById(R.id.empMobNo);
        empAddress = findViewById(R.id.empAddress);

        //spinners
        empComName = findViewById(R.id.empCompanySpinner);
        empWorkName = findViewById(R.id.empWorkName);
        empComName.setAdapter(setSpinner1());
        empWorkName.setAdapter(setSpinner2(empComName));

        //error text
        errorTxt = findViewById(R.id.errorTxt);

        //buttons
        cancelBtn = findViewById(R.id.cancelBtn);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameStr = userName.getText().toString();
                empNameStr = empName.getText().toString();
                empAgeNum = Integer.parseInt(empAge.getText().toString());
                empMobNoStr = empMobNo.getText().toString();
                empAddressStr = empAddress.getText().toString();
                empComNameStr = empComName.getSelectedItem().toString();
                empWorkNameStr = empWorkName.getSelectedItem().toString();

                if(isRegisterDetailsFilled(errorTxt, userNameStr, empNameStr, empMobNoStr, empAgeNum, empAddressStr, empComNameStr, empWorkNameStr)) {
                    HungerEmployees hungerEmployees = new HungerEmployees(userNameStr, empNameStr, empMobNoStr, empAgeNum, empAddressStr, empComNameStr, empWorkNameStr);

                    //check userName is unique
                    colRef_manager.document(mngrEmail)
                            .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if(queryDocumentSnapshots.isEmpty()) {
                                        setHungerEmployeesData(hungerEmployees, mngrEmail);
                                    }

                                    else {
                                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                            if (!Objects.equals(documentSnapshot.getId(), userNameStr)) {
                                                setHungerEmployeesData(hungerEmployees, mngrEmail);
                                            }
                                            else {
                                                setErrorTxt(errorTxt, "*Username should be unique");
                                            }
                                        }
                                    }
                                }
                            });
                }
            }
        });


        //listeners
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Register_Hunger.this, Dashboard.class));
                finish();
            }
        });
    }

    private ArrayAdapter<String> setSpinner1() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Register_Hunger.this, android.R.layout.simple_spinner_dropdown_item, arrayList);

        //spinner 1
        colRef_company.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            String comName = documentSnapshot.getId();
                            arrayList.add(comName);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                });

        return arrayAdapter;
    }

    private ArrayAdapter<String> setSpinner2(Spinner empComName) {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Register_Hunger.this, android.R.layout.simple_spinner_dropdown_item, arrayList);

        //spinner 2
        empComName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String companyName = empComName.getSelectedItem().toString();

                colRef_company.document(companyName)
                        .collection(Constants.KEY_COMPANY_WORK_COL)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                for(DocumentSnapshot documentSnapshot: value) {
                                    String workName = documentSnapshot.getId();
                                    arrayList.add(workName);
                                }
                                arrayAdapter.notifyDataSetChanged();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return arrayAdapter;
    }

    private void setHungerEmployeesData(HungerEmployees hungerEmployees, String mngrEmail) {
        colRef_manager.document(mngrEmail)
                .collection(Constants.KEY_KITCHEN_MANAGER_EMPLOYEES_COL)
                .document(hungerEmployees.getUserName())
                .set(hungerEmployees)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Employee registered", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(Register_Hunger.this, Dashboard.class));
                        finish();
                    }
                });
    }

    private void setErrorTxt(TextView errorTxt, String message) {
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(message);
    }

    //  ...method to verify input details...
    private Boolean isRegisterDetailsFilled(TextView errorTxt, String userName, String empNameStr, String empMobNoStr, Integer empAgeNum, String empAddressStr, String empComNameStr, String empWorkNameStr) {
        if(userName.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a username");
            return false;
        }

        else if(empNameStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a name");
            return false;
        }

        else if(empMobNoStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a mobile number\n(enter 0 if not available)");

            return false;
        }

        else if (!(empAgeNum > 18 && empAgeNum < 68)) {
            setErrorTxt(errorTxt, "*Please enter a valid age (18 to 68)");
            return false;
        }

        else if(empAddressStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a address");
            return false;
        }

        else if(empComNameStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please select a company");
            return false;
        }

        else if(empWorkNameStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please select a work");
            return false;
        }

        else {
            return true;
        }
    }
}