package com.example.karmbhog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.objects.Company;
import com.example.karmbhog.objects.KitchenManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class CompanySignUp extends AppCompatActivity {

    RelativeLayout signUpBtn;
    TextView loginLinkTxt;
    TextInputEditText cName, cCity, cEmail, cPwd;
    TextView errorTxt;
    String cNameStr, cCityStr, cEmailStr, cPwdStr;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER);
    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_sign_up);

        cName = findViewById(R.id.companyNameETxt);
        cCity = findViewById(R.id.companyCityETxt);
        cEmail = findViewById(R.id.signupemailETxt);
        cPwd = findViewById(R.id.signuppasswordETxt);

        errorTxt = findViewById(R.id.errorTxt);

        loginLinkTxt = findViewById(R.id.gotologin);
        signUpBtn = findViewById(R.id.signup);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cNameStr = cName.getText().toString();
                cCityStr = cCity.getText().toString();
                cEmailStr = cEmail.getText().toString();
                cPwdStr = cPwd.getText().toString();

                if (isRegisterDetailsFilled(errorTxt, cNameStr, cCityStr, cEmailStr, cPwdStr)) {
                    Company comManager = new Company(cNameStr, cCityStr, cEmailStr, cPwdStr);

                    colRef_company.document(cEmailStr).set(comManager)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(CompanySignUp.this, MainActivity.class));
                                    finish();
                                }
                            });
                }
            }
        });

        loginLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanySignUp.this, MainActivity.class));
                finish();
            }
        });
    }

    private void setErrorTxt(TextView errorTxt, String message) {
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(message);
    }


    //  ...method to verify input details...
    private Boolean isRegisterDetailsFilled(TextView errorTxt, String cNameStr, String cCityStr, String cEmailStr, String cPwdStr) {
        if(cNameStr.isEmpty()) {
            Log.d("Debug", "Company name: " + cNameStr);
            setErrorTxt(errorTxt, "*Please enter a name");
            return false;
        }

        else if(cCityStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a city");
            return false;
        }

//        else if (! Patterns.EMAIL_ADDRESS.matcher(cEmailStr.trim()).matches()) {
//            Log.d("Debug", "Document Reference: " + cEmailStr);
//            setErrorTxt(errorTxt, "*Please enter a valid email id");
//            return false;
//        }

        else if (cEmailStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter an email id");
            return false;
        }

        else if(cPwdStr.isEmpty()) {
            setErrorTxt(errorTxt, "*Please enter a password");
            return false;
        }

        else {
            return true;
        }
    }
}