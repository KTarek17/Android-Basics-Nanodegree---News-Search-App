package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    private static final String TAG = "MainActivity";

    private String currentRequestURL = "";

    private List<NewsItem> newsList;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    private TextView emptyView;
    private ProgressBar progressBar;

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

        emptyView = findViewById(R.id.emptyView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        getLoaderManager().initLoader(0, null, this);

        EditText query = findViewById(R.id.query);
        ImageButton search = findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.clearFocus();
                String queryStr = query.getText().toString();
                String logMessage = "User entered: " + queryStr;
                Log.d(TAG, logMessage);
                QueryUtils.setQuery(queryStr);
                checkForUrlChanges();
            }
        });
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
            showProgressBar();
            currentRequestURL = newRequestURL;
            getLoaderManager().initLoader(0, null, this).forceLoad();
        }
    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "New loader created!");
        showProgressBar();
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        Log.i(TAG, "onLoadFinished() has been called!");
        if (data.isEmpty()) {
            showEmptyView();
            newsAdapter.clearNewsList();
        } else if (!newsList.containsAll(data)) {
            showRecyclerView();
            newsAdapter.addAllToNewsList(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        Log.i(TAG, "Loader reset!");
        newsAdapter.clearNewsList();
        showEmptyView();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    private void showRecyclerView() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    private void showEmptyView() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }
}