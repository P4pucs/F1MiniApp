package com.kevin.f1miniapp;

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

import java.util.HashMap;
import java.util.Map;

public class DriverProfile {

    private String url;

    private String driverId;

    private String givenName;
    private String familyName;
    private String nationality;
    private String dateOfBirth;
    private String code = "";
    private int permanentNumber;
    private String wikiUrl;

    public DriverProfile(String driverId) {
        this.url = "http://ergast.com/api/f1/drivers/" + driverId + ".json" ;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getNationality() {
        return nationality;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCode() {
        return code;
    }

    public int getPermanentNumber() {
        return permanentNumber;
    }

    public String getWikiUrl() {
        return wikiUrl;
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
                            wikiUrl = driver.getString("url");

//                            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
//                            dateOfBirth = format.parse(driver.getString("dateOfBirth"));

                            if(driver.isNull("permanentNumber")) {
                                permanentNumber = -1;
                            }
                            else {
                                permanentNumber = Integer.parseInt(driver.getString("permanentNumber"));
                            }
                            code = driver.getString("code");


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

    public void readDriver() {
        System.out.println("driverId: " + driverId +
                " givenName: " + givenName +
                " familyName: " + familyName +
                " nationality: " + nationality +
                " dateOfBirth: " + dateOfBirth +
                " code: " + code +
                " permanentNumber: " + permanentNumber +
                " wikiUrl: " + wikiUrl);
    }
}
