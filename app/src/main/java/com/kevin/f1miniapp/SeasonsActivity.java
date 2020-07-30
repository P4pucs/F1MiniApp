package com.kevin.f1miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class SeasonsActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    //SeasonListAdapter seasonsAdapter;

    private Seasons seasons = new Seasons();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);

        //Button toDriversButton = findViewById(R.id.toDriversButton);
        ListView seasonsListView = (ListView) findViewById(R.id.seasonsListView);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(seasons.jsonParse());
        requestQueue.start();

        RequestQueue.RequestFinishedListener listener =
            new RequestQueue.RequestFinishedListener() {
                @Override public void onRequestFinished(Request request) {
//                if(request.equals(requestQueue.add(request))) { }

                    SeasonListAdapter seasonsAdapter = new SeasonListAdapter(SeasonsActivity.this, R.layout.seasons_adapter_view, seasons.getList());
                    seasonsListView.setAdapter(seasonsAdapter);
                    seasonsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                            String year = String.valueOf(seasons.getList().get(position).getDate());
                            Intent intent = new Intent(SeasonsActivity.this, DriversActivity.class);
                            intent.putExtra("YEAR", year); //Optional parameters
                            startActivity(intent);
                        }
                    });

                }
            }; requestQueue.addRequestFinishedListener(listener);


    }
}