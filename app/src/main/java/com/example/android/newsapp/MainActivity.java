package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
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

    ConnectivityManager connMgr;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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
        showProgressBar();

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, this);
        } else
            showEmptyView(R.string.no_internet);

        EditText query = findViewById(R.id.query);
        ImageButton search = findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.clearFocus();
                String queryStr = query.getText().toString();
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
     * This method checks if the {@link #currentRequestURL} has changed, and if that's the case,
     * then it assigns the newRequestURL to the {@link #currentRequestURL}
     * If there is no connection, it returns early after setting the showing the {@link #emptyView}
     */
    private void checkForUrlChanges() {

        //Return early without doing anything if there is no connection
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            showEmptyView(R.string.no_internet);
            return;
        }

        String newRequestURL = QueryUtils.getRequestUrl().toString();
        if (!currentRequestURL.equals(newRequestURL) || emptyView.getVisibility() == View.VISIBLE) {
            Log.i(TAG, "URL has changed!");
            showProgressBar();
            currentRequestURL = newRequestURL;
            getLoaderManager().initLoader(0, null, this).forceLoad();
        }
    }

    //region Loader methods

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
            showEmptyView(R.string.no_data);
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
        showEmptyView(R.string.no_data);
    }

    //endregion

    //region Visibility Helper Methods

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

    private void showEmptyView(@StringRes int stringId) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(stringId);
    }

    //endregion
}