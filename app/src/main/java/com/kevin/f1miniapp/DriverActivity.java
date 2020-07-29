package com.kevin.f1miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DriverActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private Driver driver = new Driver("norris");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(driver.jsonParse());
        requestQueue.start();
    }
}