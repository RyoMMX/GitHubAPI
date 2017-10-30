package com.example.android.githubapi;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.android.githubapi.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    ArrayList<Item> repositoryItems;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setListeners();


    }

    private void setListeners() {
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaderManager manager = getSupportLoaderManager();
                manager.initLoader(0, null, MainActivity.this).forceLoad();
                binding.progress.setVisibility(View.GONE);
            }
        });

        binding.repositoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = (Item) adapterView.getItemAtPosition(i);
                String url = item.getOwner().getHtml_url();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    public void setRepository(String json) {
        Log.e(">>>>>>>>>", json);
        Root root = new Gson().fromJson(json, Root.class);
        RepositorySearchListAdapter repositorySearchListAdapter = new RepositorySearchListAdapter(this, R.layout.repository_layout_list, root.getItems());
        binding.repositoryListView.setAdapter(repositorySearchListAdapter);
        binding.searchResultTextView.setText("total result : -" + root.getTotal_count());
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new SearchAsyncTask(this, binding.searchEditText.getText().toString());
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        setRepository(data);
        binding.progress.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
