package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

@SuppressLint("StaticFieldLeak")
public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {

    private final ProgressBar progressBar;

    public NewsLoader(Context context, ProgressBar progressBar) {
        super(context);
        this.progressBar = progressBar;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<NewsItem> loadInBackground() {
        progressBar.setVisibility(View.VISIBLE);
        return QueryUtils.getNewsList();
    }
}
