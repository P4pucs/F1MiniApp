package com.kevin.f1miniapp;

import android.annotation.SuppressLint;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Drivers {
    private String url = "http://ergast.com/api/f1/";

    private List<Driver> drivers;

    public Drivers(int year) {
        this.url += year + "/drivers.json" ;//concat(year + "/drivers.json");
        drivers = new ArrayList<Driver>();
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

    public void readDrivers() {
        for (int i=0;i<drivers.size();i++) {
            System.out.println("driverId: " + drivers.get(i).getDriverId() +
                    " givenName: " + drivers.get(i).getGivenName() +
                    " familyName: " + drivers.get(i).getFamilyName() +
                    " nationality: " + drivers.get(i).getNationality() +
                    " dateOfBirth: " + drivers.get(i).getDateOfBirth().toString() +
                    " permanentNumber: " + drivers.get(i).getPermanentNumber() +
                    " wikiUrl: " + drivers.get(i).getWikiUrl());
        }
    }

    public List<Driver> getList() {
        return this.drivers;
    }


    @SuppressLint("NewApi")
    public HashMap<String, Integer> groupByNations() {
        HashMap<String, Integer> map = new HashMap<>();

        String countryTmp;
        for (int i=0;i<getList().size(); i++) {
            countryTmp = getList().get(i).getNationality();
            if (map.containsKey(countryTmp)) {
                int tmp = map.get(countryTmp) + 1;
                map.replace(countryTmp, tmp);
            } else {
                map.put(countryTmp, 1);
            }
        }

        final HashMap<String, Integer> sortedByCount = map.entrySet().stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println(sortedByCount);

        return sortedByCount;

    }
}



