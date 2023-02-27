package com.example.android.networkconnect.home.models;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
