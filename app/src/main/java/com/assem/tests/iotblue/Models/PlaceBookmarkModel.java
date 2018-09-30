package com.assem.tests.iotblue.Models;

import java.io.Serializable;

public class PlaceBookmarkModel implements Serializable {

    private double lat;
    private double lon;
    private String title;

    public PlaceBookmarkModel() {
    }

    public PlaceBookmarkModel(double lat, double lon, String title) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
