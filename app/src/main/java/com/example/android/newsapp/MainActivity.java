package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.newsapp.adapter.NewsAdapter;
import com.example.android.newsapp.model.Constant;
import com.example.android.newsapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<News>>{

    /* Various initializer */
    private NewsAdapter newsAdapter;
    private RecyclerView recyclerView;
    private List<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Getting the UI and set the layout recyclerView. */
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        /* Create a new adapter that takes an empty list of earthquakes as input */
        if (newsList == null) newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsList);

        /* Setting adapter to recycler view. */
        recyclerView.setAdapter(newsAdapter);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant
        loaderManager.initLoader(Constant.NEWS_LOADER_ID, null, this);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     */
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new NewsLoader(this, Constant.BASE_URL);
    }

    /**
     * Called when a previously created loader has finished its load.
     */
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        // Clear the adapter of previous news data.
        newsAdapter.clearAll();

        // If there is a valid lis of {@link News}
        if(data != null || !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
    }

    /**
     * Called when a previously created loader is being reset,
     * and thus making its data unavailable.
     */
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so clear out existing data.
        newsAdapter.clearAll();
    }
}
