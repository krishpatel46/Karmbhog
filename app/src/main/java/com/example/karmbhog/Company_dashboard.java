package com.example.karmbhog;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;

public class Company_dashboard extends AppCompatActivity {

    private ManagePreferences managePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_dashboard);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Button b1;
        Button a,b,c,d,e,f,g;
        a = findViewById(R.id.req);
        b = findViewById(R.id.workers);
        c = findViewById(R.id.report);
        d = findViewById(R.id.workdetail);
        e = findViewById(R.id.givefeedbacks);
        b1 = findViewById(R.id.Button1);

        //listeners
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Company_dashboard.this, Company_dashboard.class));
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Company_dashboard.this, Req_of_worker.class));
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Company_dashboard.this, Workers.class));
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Company_dashboard.this, Report.class));
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Company_dashboard.this, Work_details.class));
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Company_dashboard.this, Give_feedback.class));
            }
        });

        this.getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder alert = new AlertDialog.Builder(Company_dashboard.this);

                alert.setTitle(" Exit KarmBhog");
                alert.setIcon(R.drawable.image_exit);
                alert.setMessage("Are you sure?");
                alert.setCancelable(false);

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Company_dashboard.this, "Exit cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.log) {
            managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_COMPANY_PREFERENCE_NAME);
            managePreferences.putBoolean(Constants.KEY_IS_LOGGED_IN, false);

            //startActivity(new Intent(Company_dashboard.this, AskUserRole.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}