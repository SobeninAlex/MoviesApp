package com.example.moviesapp.api;

import com.example.moviesapp.pojo.FeedbackResponse;
import com.example.moviesapp.pojo.MovieResponse;
import com.example.moviesapp.pojo.TrailerResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v1.3/movie?token=BY24RJB-RSSMSH8-P4YBHHG-0CTKPGM&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=30")
    Single<MovieResponse> loadMovie(
            @Query("page") int page
    );

    @GET("v1.3/movie/{id}?token=BY24RJB-RSSMSH8-P4YBHHG-0CTKPGM")
    Single<TrailerResponse> loadTrailers(
            @Path("id") int id
    );

    @GET("v1/review?token=BY24RJB-RSSMSH8-P4YBHHG-0CTKPGM")
    Single<FeedbackResponse> loadFeedback(
            @Query("movieId") int movieId,
            @Query("page") int page
    );

}
