package com.example.karmbhog;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.objects.KitchenManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    RelativeLayout signUpBtn;
    TextView loginLinkTxt;
    EditText mName, mCity, mEmail, mPwd;
    TextView errorTxt;
    String mNameStr, mCityStr, mEmailStr, mPwdStr;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef_manager = db.collection(Constants.KEY_KITCHEN_MANAGER_COL);
//    CollectionReference colRef_company = db.collection(Constants.KEY_COMPANY_COL);

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //input data
        mName = findViewById(R.id.managerNameETxt);
        mCity = findViewById(R.id.managerCityETxt);
        mEmail = findViewById(R.id.signupemailETxt);
        mPwd = findViewById(R.id.signuppassword);

        //error text
        errorTxt = findViewById(R.id.errorTxt);

        //buttons and links
        loginLinkTxt = findViewById(R.id.gotologin);
        signUpBtn = findViewById(R.id.signup);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNameStr = mName.getText().toString();
                mCityStr = mCity.getText().toString();
                mEmailStr = mEmail.getText().toString();
                mPwdStr = mPwd.getText().toString();

                if(isRegisterDetailsFilled(mNameStr, mCityStr, mEmailStr, mPwdStr)) {
                    KitchenManager kitManager = new KitchenManager(mNameStr, mCityStr, mEmailStr, mPwdStr);
                    colRef_manager.document(mEmailStr).set(kitManager)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignUp.this, MainActivity.class));
                                    finish();
                                }
                            });
                }
            }
        });

        loginLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
                finish();
            }
        });
    }

    private void setErrorTxt(String message) {
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(message);
    }


    //  ...method to verify input details...
    private Boolean isRegisterDetailsFilled(String mNameStr, String mCityStr, String mEmailStr, String mPwdStr) {
        if(mNameStr.isEmpty()) {
            setErrorTxt("*Please enter a name");
            return false;
        }

        else if(mCityStr.isEmpty()) {
            setErrorTxt("*Please enter a city");

            return false;
        }

        else if (! Patterns.EMAIL_ADDRESS.matcher(mEmailStr.trim()).matches()) {
            setErrorTxt("*Please enter a valid email id");
            return false;
        }

        else if(mPwdStr.isEmpty()) {
            setErrorTxt("*Please enter a password");
            return false;
        }

        else {
            return true;
        }
    }
}