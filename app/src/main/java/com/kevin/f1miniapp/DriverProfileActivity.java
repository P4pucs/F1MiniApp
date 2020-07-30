package com.kevin.f1miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DriverProfileActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private DriverProfile driverProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        setTitle(getIntent().getStringExtra("DRIVERID"));
        driverProfile = new DriverProfile(getIntent().getStringExtra("DRIVERID"));

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(driverProfile.jsonParse());
        requestQueue.start();

        RequestQueue.RequestFinishedListener listener =
            new RequestQueue.RequestFinishedListener() {
                @Override public void onRequestFinished(Request request) {

                }
            }; requestQueue.addRequestFinishedListener(listener);
    }
}