package com.kevin.f1miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class DriversActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    Drivers drivers;
    int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);
        setTitle(getIntent().getStringExtra("YEAR"));

        drivers = new Drivers(Integer.parseInt(getIntent().getStringExtra("YEAR")));


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drivers.jsonParse());
        requestQueue.start();

        RequestQueue.RequestFinishedListener listener =
                new RequestQueue.RequestFinishedListener()
                { @Override public void onRequestFinished(Request request) {

                    System.out.println("alma: " + drivers.getList().get(2).getGivenName());
                    drivers.groupByNations();

                    ExpandableListView expandableListView = findViewById(R.id.driversExpandableListView);

                    DriversExpendableListAdapter adapter = new DriversExpendableListAdapter(DriversActivity.this, drivers.getList());

                    LayoutInflater inflater = getLayoutInflater();
                    ViewGroup header = (ViewGroup)inflater.inflate(R.layout.drivers_header, expandableListView, false);
                    TextView nationsTextView = (TextView) header.findViewById(R.id.nationsTextView);
                    nationsTextView.setText(drivers.groupByNations().toString());
                    expandableListView.addHeaderView(header, null, false);

                    expandableListView.setAdapter(adapter);

                    expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                        @Override
                        public void onGroupExpand(int groupPosition) {
                            if(lastExpandedPosition != -1 && groupPosition != lastExpandedPosition){
                                expandableListView.collapseGroup(lastExpandedPosition);
                            }
                            lastExpandedPosition = groupPosition;
                        }
                    });

                    expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                            String driverId = drivers.getList().get(i).getDriverId();
                            Intent intent = new Intent(DriversActivity.this, DriverProfileActivity.class);
                            intent.putExtra("DRIVERID", driverId);
                            startActivity(intent);
                            return true;
                        }
                    });

                }
                }; requestQueue.addRequestFinishedListener(listener);

//        toDriverButton.setOnClickListener(view -> {
//            Intent myIntent = new Intent(this, DriverActivity.class);
//            //myIntent.putExtra("key", value); //Optional parameters
//            this.startActivity(myIntent);
//        });


    }
}