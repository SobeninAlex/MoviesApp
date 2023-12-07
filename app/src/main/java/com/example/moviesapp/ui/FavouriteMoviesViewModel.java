package com.example.moviesapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moviesapp.pojo.Movie;
import com.example.moviesapp.database.MovieDao;
import com.example.moviesapp.database.MovieDatabase;

import java.util.List;

public class FavouriteMoviesViewModel extends AndroidViewModel {

    private final MovieDao movieDao;
    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).movieDao();
    }

    public LiveData<List<Movie>> getMovies() {
        return movieDao.getAllFavoriteMovies();
    }

}
