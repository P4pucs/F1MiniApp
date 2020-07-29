package com.kevin.f1miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SeasonsActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private Seasons seasons = new Seasons();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);

        Button toDriversButton = findViewById(R.id.toDriversButton);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(seasons.jsonParse());
        requestQueue.start();


        RequestQueue.RequestFinishedListener listener =
            new RequestQueue.RequestFinishedListener() {
                @Override public void onRequestFinished(Request request) {
//                if(request.equals()) { }

                    System.out.println("alma: " + seasons.getList().get(1).getWikiUrl());
                    
                }
            }; requestQueue.addRequestFinishedListener(listener);

        toDriversButton.setOnClickListener((v) -> {
            Intent myIntent = new Intent(this, DriversActivity.class);
            //myIntent.putExtra("key", value); //Optional parameters
            this.startActivity(myIntent);
        });

    }
}