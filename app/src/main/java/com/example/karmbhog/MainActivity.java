package com.example.karmbhog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ManagePreferences managePreferences;


    EditText emailETxt, pwdETxt;
    Spinner roleSpinner;
    String emailStr, pwdStr, roleStr;
    RelativeLayout loginBtn, companySignUpBtn, kitchenMngrSignUpBtn;
    TextView forgotPwdLinkTxt;
    TextView errorTxt;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //spinner
        roleSpinner = findViewById(R.id.roleSpinner);
        roleSpinner.setAdapter(setRoleSpinner(roleSpinner));

        //error text
        errorTxt = findViewById(R.id.errorTxt);

        //buttons and links
        forgotPwdLinkTxt = findViewById(R.id.gotoforgotpassword);
        loginBtn = findViewById(R.id.login);
        kitchenMngrSignUpBtn = findViewById(R.id.gotoKitchensignup);
        companySignUpBtn = findViewById(R.id.gotoCompanysignup);

        //input data
        emailETxt = findViewById(R.id.loginemailETxt);
        pwdETxt = findViewById(R.id.loginpasswordETxt);


        //listeners
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailStr = emailETxt.getText().toString();
                pwdStr = pwdETxt.getText().toString();
                roleStr = roleSpinner.getSelectedItem().toString();

                if(isLoginDetailsFilled(emailStr, pwdStr)) {
                    if (roleStr.equals(Constants.KEY_COMPANY_COL)) {
                        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_COMPANY_PREFERENCE_NAME);
                        isCompany(emailStr, pwdStr, managePreferences);
                    }
                    else if (roleStr.equals(Constants.KEY_KITCHEN_MANAGER_COL)) {
                        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_KITCHEN_MANAGER_PREFERENCE_NAME);
                        isKitchenMngr(emailStr, pwdStr, managePreferences);
                    }
                    else {
                        setErrorTxt("*Please select a correct role");
                    }
                }
            }
        });

        companySignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CompanySignUp.class));
            }
        });
        kitchenMngrSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
        forgotPwdLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Forgot_password.class));
            }
        });
    }

    private ArrayAdapter<String> setRoleSpinner(Spinner roleSpinner) {
        ArrayList<String> roleList = new ArrayList<>();
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, roleList);

        roleList.add(Constants.KEY_COMPANY_COL);
        roleList.add(Constants.KEY_KITCHEN_MANAGER_COL);

        roleAdapter.notifyDataSetChanged();

        return roleAdapter;
    }

    private void setErrorTxt(String message) {
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(message);
    }


    //  [method to verify input details]
    private Boolean isLoginDetailsFilled(String emailStr, String pwdStr) {
        if (! Patterns.EMAIL_ADDRESS.matcher(emailStr.trim()).matches()) {
            setErrorTxt("*Please enter an email id");
            return false;
        }

        else if(pwdStr.isEmpty()) {
            setErrorTxt("*Please enter a password");
            return false;
        }

        else {
            return true;
        }
    }

    private void isCompany(String emailStr, String pwdStr, ManagePreferences managePreferences) {
        colRef_company.whereEqualTo(Constants.KEY_COMPANY_EMAIL, emailStr)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null) {
                            Log.d("database error : ", error.toString());
                            setErrorTxt("*Company not found with the provided email");
                        }

                        else {
                            Log.d("value company : ", value.toString());
                            //List<DocumentSnapshot> documentSnapshotList = value.getDocuments();

                            for(DocumentSnapshot documentSnapshot: value) {
                                String name = documentSnapshot.getString(Constants.KEY_COMPANY_NAME);
                                String pwd = documentSnapshot.getString(Constants.KEY_COMPANY_PWD);
                                String city = documentSnapshot.getString(Constants.KEY_COMPANY_CITY);
                                Log.d("password : ", pwd);

                                assert pwd != null;
                                if (pwd.equals(pwdStr)) {
                                    managePreferences.putBoolean(Constants.KEY_IS_LOGGED_IN, true);
                                    managePreferences.putString(Constants.KEY_COMPANY_EMAIL, emailStr);
                                    managePreferences.putString(Constants.KEY_COMPANY_NAME, name);
                                    managePreferences.putString(Constants.KEY_COMPANY_CITY, city);

                                    Toast.makeText(getApplicationContext(), "You are logged in", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this, Company_dashboard.class));
                                    finish();
                                }
                            }

                            //if all documents are iterated and match not found
                            setErrorTxt("*Wrong Password: Please enter correct password");
                        }
                    }
                });
    }

    private void isKitchenMngr(String emailStr, String pwdStr, ManagePreferences managePreferences) {
        colRef_manager.whereEqualTo(Constants.KEY_KITCHEN_MANAGER_EMAIL, emailStr)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null) {
                            Log.d("database error : ", error.toString());
                            setErrorTxt("*Kitchen Manager not found with the provided email");
                        }

                        else {
                            Log.d("value kitchen manager : ", value.toString());
                            //List<DocumentSnapshot> documentSnapshotList = value.getDocuments();

                            for(DocumentSnapshot documentSnapshot: value) {
                                String name = documentSnapshot.getString(Constants.KEY_KITCHEN_MANAGER_NAME);
                                String pwd = documentSnapshot.getString(Constants.KEY_KITCHEN_MANAGER_PWD);
                                String city = documentSnapshot.getString(Constants.KEY_KITCHEN_MANAGER_CITY);
                                Log.d("password : ", pwd);

                                assert pwd != null;
                                if(pwd.equals(pwdStr)) {
                                    managePreferences.putBoolean(Constants.KEY_IS_LOGGED_IN, true);
                                    managePreferences.putString(Constants.KEY_KITCHEN_MANAGER_EMAIL, emailStr);
                                    managePreferences.putString(Constants.KEY_KITCHEN_MANAGER_NAME, name);
                                    managePreferences.putString(Constants.KEY_KITCHEN_MANAGER_CITY, city);

                                    Toast.makeText(getApplicationContext(), "You are logged in", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this, Dashboard.class));
                                    finish();
                                }
                            }

                            //if all documents are iterated and match not found
                            setErrorTxt("*Wrong Password: Please enter correct password");
                        }
                    }
                });
    }
}