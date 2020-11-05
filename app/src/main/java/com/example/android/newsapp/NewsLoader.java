package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

@SuppressLint("StaticFieldLeak")
public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {


    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<NewsItem> loadInBackground() {
        return QueryUtils.getNewsList();
    }
}
