package com.kevin.f1miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DriversActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private Drivers drivers = new Drivers(2012);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        Button toDriverButton = findViewById(R.id.toDriverButton);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drivers.jsonParse());
        requestQueue.start();

        toDriverButton.setOnClickListener(view -> {
        });


    }
}