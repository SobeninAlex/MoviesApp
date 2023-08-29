package com.example.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = "MovieDetailActivityTAG";
    private static final String EXTRA_MOVIE = "movie";

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;

    private MovieDetailViewModel viewModel;

    private RecyclerView recyclerViewTrailers;
    private TrailersAdapter trailersAdapter;

    private FeedbackAdapter feedbackAdapter;
    private RecyclerView recyclerViewFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        initViews();

        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailers.setAdapter(trailersAdapter);

        feedbackAdapter = new FeedbackAdapter();
        recyclerViewFeedback.setAdapter(feedbackAdapter);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);
        textViewTitle.setText(movie.getName());
        textViewYear.setText(movie.getYear());
        textViewDescription.setText(movie.getDescription());

        viewModel.loadTrailers(movie.getId());
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });

        trailersAdapter.setOnTrailerClickListener(trailer -> {
            var intent = new Intent(Intent.ACTION_VIEW); //открываем браузер
            intent.setData(Uri.parse(trailer.getUrl())); //передаем uri
            startActivity(intent);
        });

        viewModel.loadFeedback(movie.getId());
        viewModel.getFeedbacks().observe(this, new Observer<List<Feedback>>() {
            @Override
            public void onChanged(List<Feedback> feedBacks) {
                feedbackAdapter.setFeedbackList(feedBacks);
            }
        });

        feedbackAdapter.setOnReachEndListener(new FeedbackAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadFeedback(movie.getId());
            }
        });

    }

    public static Intent newIntent(Context context, Movie movie) {
        var intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewFeedback = findViewById(R.id.recyclerViewFeedback);
    }
}