package com.kevin.f1miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                @SuppressLint("SetTextI18n")
                @Override public void onRequestFinished(Request request) {

                    ImageView placeHolderImageView = (ImageView) findViewById(R.id.placeHolderImageView);
                    Button profileWikiButton = (Button) findViewById(R.id.profileWikiButton);
                    TextView profileNameTextView = (TextView) findViewById(R.id.profileNameTextView);
                    TextView profileNationalityTextView = (TextView) findViewById(R.id.profileNationalityTextView);
                    TextView profileDateOfBirthTextView = (TextView) findViewById(R.id.profileDateOfBirthTextView);
                    TextView profileCodeTextView = (TextView) findViewById(R.id.profileCodeTextView);
                    TextView profilePermanentNumberTextView = (TextView) findViewById(R.id.profilePermanentNumberTextView);


                    placeHolderImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(DriverProfileActivity.this, "Well... it is from google", Toast.LENGTH_SHORT).show();
                            driverProfile.readDriver();
                        }
                    });

                    profileWikiButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println("url? " + driverProfile.getWikiUrl());
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(driverProfile.getWikiUrl()));
                            startActivity(browserIntent);
                        }
                    });

                    profileNameTextView.setText("name: " + driverProfile.getGivenName() + " " + driverProfile.getFamilyName());
                    profileNationalityTextView.setText("nationality: " + driverProfile.getNationality());
                    profileDateOfBirthTextView.setText("date of birth: " + driverProfile.getDateOfBirth());
                    profileCodeTextView.setText("code: " + driverProfile.getCode());
                    String raceNumber = ((driverProfile.getPermanentNumber() == -1) ? "no racenumber" : "" + driverProfile.getPermanentNumber());
                    profilePermanentNumberTextView.setText("race number: " +  raceNumber);
                }
            }; requestQueue.addRequestFinishedListener(listener);
    }
}