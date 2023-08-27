package com.example.moviesapp;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("url")
    private String url;

    @SerializedName("name")
    private String name;

    public Trailer(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Trailers{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}