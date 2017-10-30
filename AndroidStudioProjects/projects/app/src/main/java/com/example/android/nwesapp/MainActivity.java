package com.example.android.nwesapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.android.nwesapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    ActivityMainBinding binding;
    SearchAsyncTask searchAsyncTask;
    Menu menu;
    MenuItem pageNumber;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setListeners();
        setLoader();
    }

    private void setListeners() {
        binding.allListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Result result = (Result) parent.getItemAtPosition(position);
                String url = result.getWebUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh:
                setLoader();
                break;

            case R.id.next:
                if (searchAsyncTask != null) {
                    if (searchAsyncTask.nextPage()) {
                        setLoader();
                    }
                }
                break;

            case R.id.previous:
                if (searchAsyncTask != null) {
                    if (searchAsyncTask.previousPage()) {
                        setLoader();
                    } else {
                        Toast.makeText(this, "this is the first page!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.page_number_menu_item:
                Toast.makeText(this, "the total result is : " + total, Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLoader() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.allListView.setVisibility(View.GONE);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            Log.e("notwork", "false:");
            Toast.makeText(MainActivity.this, "You are not connected to the internet ):", Toast.LENGTH_SHORT).show();
        } else {
            LoaderManager manager = getSupportLoaderManager();
            if (manager.getLoader(0) == null) {
                manager.initLoader(0, null, MainActivity.this).forceLoad();
            } else {
                manager.initLoader(0, null, MainActivity.this).reset();
                manager.initLoader(0, null, MainActivity.this).forceLoad();
            }
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        searchAsyncTask = new SearchAsyncTask(this, 1);
        return searchAsyncTask;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (menu != null) {
            if (pageNumber == null) {
                pageNumber = menu.findItem(R.id.page_number_menu_item);
            }
        }

        JsonData jsonData = new JsonData(data);

        total = jsonData.getTotal();
        SearchListAdapter adapter = new SearchListAdapter(this, jsonData.getResultArrayList());
        binding.allListView.setAdapter(adapter);

        if (pageNumber != null && searchAsyncTask != null) {
            pageNumber.setTitle("page: " + searchAsyncTask.getPage());
        }

        binding.progressBar.setVisibility(View.GONE);
        binding.allListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }
}
