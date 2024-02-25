package com.example.karmbhog;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.karmbhog.classes.Constants;
import com.example.karmbhog.classes.ManagePreferences;


public class Dashboard extends AppCompatActivity {

    private ManagePreferences managePreferences;

    Button b1;
    Button registerHungerBtn, b, companyDisplayBtn, d, e, f, g, h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_KITCHEN_MANAGER_PREFERENCE_NAME);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        registerHungerBtn = findViewById(R.id.add);
        b = findViewById(R.id.hunger);
        companyDisplayBtn = findViewById(R.id.company);
        d = findViewById(R.id.event);
        e = findViewById(R.id.feedbacks);
        b1 = findViewById(R.id.Button1);
        f = findViewById(R.id.kitchens);
        //g = findViewById(R.id.addkitchen);
        h = findViewById(R.id.workreq);

        //listeners
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Dashboard.this, Dashboard.class));
            }
        });
        registerHungerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Register_Hunger.class));
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Display_hungers.class));
            }
        });
        companyDisplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Company_detail.class));
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Display_events.class));
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Feedback.class));
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Kitchens.class));
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, display_req.class));
            }
        });

        this.getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder alert = new AlertDialog.Builder(Dashboard.this);

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
                        Toast.makeText(Dashboard.this, "Exit cancelled", Toast.LENGTH_SHORT).show();
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
            managePreferences = new ManagePreferences(getApplicationContext(), Constants.KEY_KITCHEN_MANAGER_PREFERENCE_NAME);
            managePreferences.putBoolean(Constants.KEY_IS_LOGGED_IN, false);

            startActivity(new Intent(Dashboard.this, AskUserRole.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}