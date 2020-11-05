package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    private static final String TAG = MainActivity.class.getName();

    private String currentRequestURL = "";

    private List<NewsItem> newsList;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentRequestURL = QueryUtils.getRequestUrl().toString();

        newsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        newsAdapter = new NewsAdapter(newsList, this);

        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Checking For URL changes");
        checkForUrlChanges();
    }

    /**
     * This method checks if the {@link #currentRequestURL} has changed, and if  that's the case,
     * then it assigns the newRequestURL to the {@link #currentRequestURL}
     */
    private void checkForUrlChanges() {
        String newRequestURL = QueryUtils.getRequestUrl().toString();
        if (!currentRequestURL.equals(newRequestURL)) {
            Log.i(TAG, "URL has changed!");
            currentRequestURL = newRequestURL;
            getLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "New loader created!");
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        newsList.clear();
        newsList.addAll(data);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        Log.i(TAG, "Loader reset!");
        newsList.clear();
        newsAdapter.notifyDataSetChanged();
    }
}