package com.kevin.f1miniapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Driver {

    private String url = "http://ergast.com/api/f1/drivers/";

    private String driverId;
    private String givenName;
    private String familyName;
    private String nationality;
    private String dateOfBirth;
    private int permanentNumber;
    private String wikiUrl;

    public Driver(String driverId) {
        this.url += driverId + ".json" ;
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

                            JSONObject driver = driversArray.getJSONObject(0);
                            driverId = driver.getString("driverId");
                            givenName = driver.getString("givenName");
                            familyName = driver.getString("familyName");
                            nationality = driver.getString("nationality");
                            dateOfBirth = driver.getString("dateOfBirth");

//                            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
//                            dateOfBirth = format.parse(driver.getString("dateOfBirth"));

                            if(driver.isNull("permanentNumber"))
                                permanentNumber = -1;
                            else
                                permanentNumber = Integer.parseInt(driver.getString("permanentNumber"));

                            wikiUrl = driver.getString("url");

                            readDriver();

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

    public void readDriver() {
        System.out.println("driverId: " + driverId +
                " givenName: " + givenName +
                " familyName: " + familyName +
                " nationality: " + nationality +
                " dateOfBirth: " + dateOfBirth +
                " permanentNumber: " + permanentNumber +
                " wikiUrl: " + wikiUrl);
    }
}
