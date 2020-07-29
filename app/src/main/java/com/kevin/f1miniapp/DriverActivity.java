package com.kevin.f1miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
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

        RequestQueue.RequestFinishedListener listener =
            new RequestQueue.RequestFinishedListener() {
                @Override public void onRequestFinished(Request request) {

                    System.out.println("alma: " + driver.getGivenName());

                    driver.readDriver();
                }
            }; requestQueue.addRequestFinishedListener(listener);
    }
}