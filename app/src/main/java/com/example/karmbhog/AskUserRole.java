package com.example.karmbhog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;

public class AskUserRole extends AppCompatActivity {

    Button userBtn, companySignUpBtn, kitchenSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_user_role);

        companySignUpBtn = findViewById(R.id.companySignUpBtn);
        kitchenSignUpButton = findViewById(R.id.kitchenSignUpBtn);
        userBtn = findViewById(R.id.userBtn);

        boolean mngrHasLoggedIn = checkLogin(Constants.KEY_KITCHEN_MANAGER_EMPLOYEE_ADDRESS);
        boolean companyHasLoggedIn = checkLogin(Constants.KEY_COMPANY_PREFERENCE_NAME);

        Log.d("manager login status : ", String.valueOf(mngrHasLoggedIn));
        Log.d("company login status : ", String.valueOf(companyHasLoggedIn));
//        boolean facultyHasLoggedIn = checkLogin(Constant.KEY_PROFESSOR_PREFERENCE_NAME);

//        else if(facultyHasLoggedIn) {
//            startActivity(new Intent(MainActivity.this, ProfessorDashboardActivity.class));
//            finish();
//        }

        //listeners
        companySignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companyHasLoggedIn) {
                    Log.d("company login status: ", String.valueOf(companyHasLoggedIn));
                    startActivity(new Intent(AskUserRole.this, Company_dashboard.class));
                    finish();
                }
                else {
                    startActivity(new Intent(AskUserRole.this, MainActivity.class));
                }
            }
        });
        kitchenSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mngrHasLoggedIn) {
                    Log.d("manager login status: ", String.valueOf(mngrHasLoggedIn));
                    startActivity(new Intent(AskUserRole.this, Dashboard.class));
                    finish();
                }
                else {
                    startActivity(new Intent(AskUserRole.this, MainActivity.class));
                }
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AskUserRole.this, User_dashboard.class));
            }
        });
    }

    private boolean checkLogin(String preferenceName) {
        ManagePreferences preferences = new ManagePreferences(getApplicationContext(), preferenceName);
        return preferences.getBoolean(Constants.KEY_IS_LOGGED_IN);
    }
}