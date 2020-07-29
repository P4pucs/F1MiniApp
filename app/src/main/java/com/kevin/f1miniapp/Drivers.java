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
import java.util.Map;

public class Drivers {
    private String url = "http://ergast.com/api/f1/";

    private ArrayList<Driver> drivers;

    public Drivers(int year) {
        this.url += year + "/drivers.json" ;//concat(year + "/drivers.json");
        drivers = new ArrayList<>();
    }

    private class Driver {
        String driverId;
        String givenName;
        String familyName;
        String nationality;
        String dateOfBirth;
        int permanentNumber;
        String wikiUrl;

        private Driver(String driverId,
                        String givenName,
                        String familyName,
                        String nationality,
                        String dateOfBirth,
                        int permanentNumber,
                        String wikiUrl) {
            this.driverId = driverId;
            this.givenName = givenName;
            this.familyName = familyName;
            this.nationality = nationality;
            this.dateOfBirth = dateOfBirth;
            this.permanentNumber = permanentNumber;
            this.wikiUrl = wikiUrl;
        }
    }

    public JsonObjectRequest jsonParse() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject MRData = response.getJSONObject("MRData");
                        JSONObject driversTable = MRData.getJSONObject("DriverTable");

                        JSONArray driversArray =  driversTable.getJSONArray("Drivers");

                        for(int i=0;i<driversArray.length();i++) {
                            JSONObject driver = driversArray.getJSONObject(i);
                            String driverId = driver.getString("driverId");
                            String givenName = driver.getString("givenName");
                            String familyName = driver.getString("familyName");
                            String nationality = driver.getString("nationality");
                            String dateOfBirth = driver.getString("dateOfBirth");
//                                SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
//                                Date dateOfBirth = format.parse(driver.getString("dateOfBirth"));

                            int permanentNumber;
                            if(driver.isNull("permanentNumber"))
                                permanentNumber = -1;
                            else
                                permanentNumber = Integer.parseInt(driver.getString("permanentNumber"));


                            String wikiUrl = driver.getString("url");
                            drivers.add(new Driver(driverId,
                                    givenName,
                                    familyName,
                                    nationality,
                                    dateOfBirth,
                                    permanentNumber,
                                    wikiUrl));
                            readDrivers();

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

    public void readDrivers() {
        for (int i=0;i<drivers.size();i++) {
            System.out.println("driverId: " + drivers.get(i).driverId +
                    " givenName: " + drivers.get(i).givenName +
                    " familyName: " + drivers.get(i).familyName +
                    " nationality: " + drivers.get(i).nationality +
                    " dateOfBirth: " + drivers.get(i).dateOfBirth.toString() +
                    " permanentNumber: " + drivers.get(i).permanentNumber +
                    " wikiUrl: " + drivers.get(i).wikiUrl);
        }
    }
}


