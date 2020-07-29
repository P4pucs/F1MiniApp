package com.kevin.f1miniapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Seasons {
    private final String url = "http://ergast.com/api/f1/seasons.json";

    private List<Season> seasons;

    public Seasons() {
        seasons = new ArrayList<>();

    }

    protected class Season {
        private int date;
        private String wikiUrl;

        private Season(int date, String url) {
            this.date = date;
            this.wikiUrl = url;
        }

        protected int getDate() {
            return date;
        }

        protected String getWikiUrl() {
            return wikiUrl;
        }
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
                            System.out.println("baj van");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
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
            System.out.println("year: " + seasons.get(i).date + " url: " + seasons.get(i).wikiUrl);
        }
    }

    public List<Season> getList() {
        return this.seasons;
    }


}
