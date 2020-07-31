package com.kevin.f1miniapp;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Seasons extends Season {
    private final String url = "http://ergast.com/api/f1/seasons.json?limit=100.json";

    private List<Season> seasons;

    public Seasons() {
        seasons = new ArrayList<>();

    }

    public JsonObjectRequest jsonParse() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject MRData = response.getJSONObject("MRData");
                            JSONObject seasonTable = MRData.getJSONObject("SeasonTable");

                            JSONArray seasonsArray =  seasonTable.getJSONArray("Seasons");

                            for(int i=0;i<seasonsArray.length();i++) {
                                JSONObject season = seasonsArray.getJSONObject(i);
                                int year = Integer.parseInt(season.getString("season"));
                                String url = season.getString("url");
                                seasons.add(new Season(year, url));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    System.out.println("No Connection/Communication Error!");
                } else if (error instanceof AuthFailureError) {
                    System.out.println("Authentication/ Auth Error!");
                } else if (error instanceof ServerError) {
                    System.out.println("Server Error!");
                } else if (error instanceof NetworkError) {
                    System.out.println("Network Error!");
                } else if (error instanceof ParseError) {
                    System.out.println("Parse Error!");
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        return request;
    }

    public void readSeason() {
        for (int i=0;i<seasons.size();i++) {
            System.out.println("year: " + seasons.get(i).getDate() + " url: " + seasons.get(i).getWikiUrl());
        }
    }

    public List<Season> getList() {
        return this.seasons;
    }


}
