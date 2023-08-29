package com.example.moviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private List<Feedback> feedbackList = new ArrayList<>();

    private OnReachEndListener onReachEndListener;

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_item, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        var feedback = feedbackList.get(position);
        var type = feedback.getType();

//        int backgroundID;
//        if (type == "Позитивный") {
//            backgroundID = android.R.color.holo_green_light;
//        }
//        else if (type == "Нейтральный") {
//            backgroundID = android.R.color.holo_orange_light;
//        }
//        else {
//            backgroundID = android.R.color.holo_red_light;
//        }


//        switch (type) {
//            case "Позитивный" -> backgroundID = android.R.color.holo_green_light;
//            case "Нейтральный" -> backgroundID = android.R.color.holo_orange_light;
//            default -> backgroundID = android.R.color.holo_red_light;
//        }


//        var drawable = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundID);
//        holder.linearLayoutFeedback.setBackground(drawable);

        holder.textViewAuthor.setText(feedback.getAuthor());
        holder.textViewFeedback.setText(feedback.getReview());

        if (position >= feedbackList.size() - 2 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
    }

    interface OnReachEndListener {
        void onReachEnd();
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout linearLayoutFeedback;
        private final TextView textViewAuthor;
        private final TextView textViewFeedback;
        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutFeedback = itemView.findViewById(R.id.linearLayoutFeedback);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewFeedback = itemView.findViewById(R.id.textViewFeedback);
        }
    }
}