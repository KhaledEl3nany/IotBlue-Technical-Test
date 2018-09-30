package com.assem.tests.iotblue.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookmarkModel {
    @SerializedName("weather")
    @Expose
    private List<WeatherModel> weather = null;
    @SerializedName("main")
    @Expose
    private MainForecastModel main;
    @SerializedName("wind")
    @Expose
    private WindModel wind;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    public List<WeatherModel> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherModel> weather) {
        this.weather = weather;
    }

    public MainForecastModel getMain() {
        return main;
    }

    public void setMain(MainForecastModel main) {
        this.main = main;
    }

    public WindModel getWind() {
        return wind;
    }

    public void setWind(WindModel wind) {
        this.wind = wind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
