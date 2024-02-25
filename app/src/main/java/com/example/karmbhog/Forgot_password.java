package com.example.karmbhog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Forgot_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button b;
        TextView t;
        t = findViewById(R.id.gobacktologin);
        b  = findViewById(R.id.passwordrecoverbutton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Password Changed",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Forgot_password.this,MainActivity.class));
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Forgot_password.this,MainActivity.class));
            }
        });
    }
}