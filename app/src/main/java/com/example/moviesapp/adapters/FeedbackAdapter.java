package com.example.moviesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.pojo.Feedback;
import com.example.moviesapp.R;

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

        holder.textViewAuthor.setText(feedback.getAuthor());
        holder.textViewFeedback.setText(feedback.getReview());

        var type = feedback.getType();
        int colorID = android.R.color.darker_gray;
        if (type != null) {
            switch (type) {
                case "Позитивный" -> colorID = android.R.color.holo_green_light;
                case "Нейтральный" -> colorID = android.R.color.holo_orange_light;
                default -> colorID = android.R.color.holo_red_light;
            }
        }
        var color = ContextCompat.getColor(holder.itemView.getContext(), colorID);
        holder.linearLayoutFeedback.setBackgroundColor(color);

        if (position >= feedbackList.size() - 1 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    interface OnReachEndListener {
        void onReachEnd();
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
