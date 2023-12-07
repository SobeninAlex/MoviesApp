package com.example.moviesapp.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedbackResponse {

    @SerializedName("docs")
    private List<Feedback> feedBacks;

    public FeedbackResponse(List<Feedback> feedBacks) {
        this.feedBacks = feedBacks;
    }

    public List<Feedback> getFeedBacks() {
        return feedBacks;
    }

    @Override
    public String toString() {
        return "FeedBackResponse{" +
                "feedBacks=" + feedBacks +
                '}';
    }
}
