package com.example.moviesapp.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.api.ApiFactory;
import com.example.moviesapp.pojo.Feedback;
import com.example.moviesapp.pojo.FeedbackResponse;
import com.example.moviesapp.pojo.Movie;
import com.example.moviesapp.database.MovieDao;
import com.example.moviesapp.database.MovieDatabase;
import com.example.moviesapp.pojo.Trailer;
import com.example.moviesapp.pojo.TrailerResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {
    private static final String TAG = "MovieDetailViewModelTAG";
    private int page = 1;

    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private final MutableLiveData<List<Feedback>> feedbacks = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MovieDao movieDao;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).movieDao();
    }
    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }
    public LiveData<List<Feedback>> getFeedbacks() {
        return feedbacks;
    }

    public LiveData<Movie> getFavoriteMovie(int movieID) {
        return movieDao.getFavoriteMovie(movieID);
    }

    public void insertMovie(Movie movie) {
        Disposable disposable = movieDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void removeMovie(int movieID) {
        Disposable disposable = movieDao.removeMovie(movieID)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getVideos().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailerList) throws Throwable {
                        trailers.setValue(trailerList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadFeedback(int movieId) {
        var loading = isLoading.getValue();
        if (loading != null && loading) {
            return;
        }
        Disposable disposable = ApiFactory.apiService.loadFeedback(movieId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .map(new Function<FeedbackResponse, List<Feedback>>() {
                    @Override
                    public List<Feedback> apply(FeedbackResponse feedBackResponse) throws Throwable {
                        return feedBackResponse.getFeedBacks();
                    }
                })
                .subscribe(new Consumer<List<Feedback>>() {
                    @Override
                    public void accept(List<Feedback> feedBacksList) throws Throwable {
                        List<Feedback> loadedFeedback = feedbacks.getValue();
                        if (loadedFeedback != null) {
                            loadedFeedback.addAll(feedBacksList);
                            feedbacks.setValue(loadedFeedback);
                        }
                        else {
                            feedbacks.setValue(feedBacksList);
                        }
                        Log.d(TAG, "Loaded " + page);
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
