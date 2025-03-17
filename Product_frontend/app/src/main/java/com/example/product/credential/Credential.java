package com.example.product.credential;

import com.google.gson.annotations.SerializedName;



public class Credential {


    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;

    @SerializedName("isLoggedIn")
    private Integer isLoggedIn;

    // Constructors


    public Credential(Integer id,String name, String password, Integer isLoggedIn) {
        this.id=id;
        this.name = name;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(Integer isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}

