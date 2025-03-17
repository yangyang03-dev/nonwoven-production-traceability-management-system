package com.example.product;

import com.example.product.barcode.Location;
import com.google.gson.annotations.SerializedName;
public class Run {
    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;



    @SerializedName("startedOn")
    private String startedOn;


    @SerializedName("completedOn")
    private String completedOn;

    @SerializedName("miles")
    private Integer miles;

    @SerializedName("location")
    private Location location;

    @SerializedName("version")
    private Integer version;

    // Constructors, getters, and setters
    public Run(Integer id, String title, String startedOn, String completedOn, Integer miles, Location location, Integer version) {
        this.id = id;
        this.title = title;
        this.startedOn = startedOn;
        this.completedOn = completedOn;
        this.miles = miles;
        this.location = location;
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStartedOn() {
        return startedOn;
    }

    public String getCompletedOn() {
        return completedOn;
    }

    public Integer getMiles() {
        return miles;
    }

    public Location getLocation() {
        return location;
    }

    public Integer getVersion() {
        return version;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }

    public void setMiles(Integer miles) {
        this.miles = miles;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

