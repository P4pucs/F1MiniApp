package com.kevin.f1miniapp;

public class Season {
    private int date;
    private String wikiUrl;

    public Season(int date, String url) {
        this.date = date;
        this.wikiUrl = url;
    }

    public Season() {
    }

    public int getDate() {
        return date;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }
}