package com.example.karmbhog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Add_kitchens extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kitchens);
        RelativeLayout r1,r2;
        r1 = findViewById(R.id.cancel);
        r2 = findViewById(R.id.sub);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add_kitchens.this, Dashboard.class));

            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Kitchen Detail Added...",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Add_kitchens.this, Dashboard.class));

            }
        });
    }
}