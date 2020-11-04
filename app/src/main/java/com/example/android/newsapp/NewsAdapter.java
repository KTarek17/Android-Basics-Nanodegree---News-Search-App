package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<NewsItem> newsList;
    private final Context context;

    public NewsAdapter(List<NewsItem> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_card, parent, false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private final View currentItemView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.currentItemView = itemView;
        }

        private void bind(int position) {
            NewsItem currentNewsItem = newsList.get(position);

            TextView title = currentItemView.findViewById(R.id.newsCardTitle);
            TextView author = currentItemView.findViewById(R.id.newsCardAuthor);
            TextView section = currentItemView.findViewById(R.id.newsCardSection);
            TextView datePublished = currentItemView.findViewById(R.id.newsCardDatePublished);
            TextView bodyTextSummary = currentItemView.findViewById(R.id.newsCardBodyTextSummary);

            //region Tie ViewHolder with currentNewsItem

            title.setText(currentNewsItem.getTitle());

            //if there is no author, exclude the author textView from view holder
            if (currentNewsItem.getAuthor().isEmpty())
                author.setVisibility(View.GONE);
            else {
                author.setVisibility(View.VISIBLE);
                author.setText(currentNewsItem.getAuthor());
            }

            section.setText(currentNewsItem.getSection());
            datePublished.setText(currentNewsItem.getDatePublished());

            //TODO Fix this after you add the HTTP stuff
            //If there is no body summary, exclude the body summary text view from view holder,
            //then add padding to the section text view to make it look nicer
            if (currentNewsItem.getBodyTextSummary().isEmpty()) {
                bodyTextSummary.setVisibility(View.GONE);
                int dimension = (int) (context.getResources().getDimension(R.dimen.smallMarginPadding)
                        / context.getResources().getDisplayMetrics().density);
                section.setPadding(0, 0, 0, dimension);
            } else {
                bodyTextSummary.setVisibility(View.VISIBLE);
                bodyTextSummary.setText(currentNewsItem.getBodyTextSummary());
            }

            //endregion

            currentItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNewsItem.getWebUrlString()));
                    context.startActivity(newsIntent);
                }
            });
        }
    }
}
