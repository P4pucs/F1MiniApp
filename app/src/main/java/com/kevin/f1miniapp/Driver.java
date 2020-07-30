package com.kevin.f1miniapp;

public class Driver {

    private String driverId;
    private String givenName;
    private String familyName;
    private String nationality;
    private String dateOfBirth;
    private int permanentNumber;
    private String wikiUrl;

    public Driver(String driverId,
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

    public int getPermanentNumber() {
        return permanentNumber;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

}