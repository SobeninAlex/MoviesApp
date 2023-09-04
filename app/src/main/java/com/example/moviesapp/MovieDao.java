package com.example.moviesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDao {
    @Query("select * from favorite_movies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Query("select * from favorite_movies where id = :movieID")
    LiveData<Movie> getFavoriteMovie(int movieID);

    @Insert
    Completable insertMovie(Movie movie);

    @Query("delete from favorite_movies where id = :movieID")
    Completable removeMovie(int movieID);

}
