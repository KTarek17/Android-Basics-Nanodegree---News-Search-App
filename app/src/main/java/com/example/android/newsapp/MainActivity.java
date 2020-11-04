package com.example.android.newsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        //TODO Implement loaders
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForUrlChanges();
    }

    /**
     * This method checks if the {@link #currentRequestURL} has changed, and if  that's the case,
     * then it assigns the newRequestURL to the {@link #currentRequestURL}
     */
    private void checkForUrlChanges() {
        String newRequestURL = QueryUtils.getRequestUrl().toString();
        if (!currentRequestURL.equals(newRequestURL)) {
            currentRequestURL = newRequestURL;
            //TODO If the URL has changed, perform a new request using loaders

        }
    }


}