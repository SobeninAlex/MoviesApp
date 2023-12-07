package com.example.moviesapp.pojo;

import com.google.gson.annotations.SerializedName;

public class Feedback {

    @SerializedName("author")
    private String author;

    @SerializedName("review")
    private String review;

    @SerializedName("type")
    private String type;

    public Feedback(String author, String review, String type) {
        this.author = author;
        this.review = review;
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public String getReview() {
        return review;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "author='" + author + '\'' +
                ", review='" + review + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
