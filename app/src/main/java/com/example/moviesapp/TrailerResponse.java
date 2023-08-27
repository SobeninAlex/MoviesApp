package com.example.moviesapp;

import com.google.gson.annotations.SerializedName;

public class TrailerResponse {

    @SerializedName("videos")
    private TrailersList videos;

    public TrailerResponse(TrailersList videos) {
        this.videos = videos;
    }

    public TrailersList getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return "MovieDetailResponse{" +
                "videos=" + videos +
                '}';
    }
}
