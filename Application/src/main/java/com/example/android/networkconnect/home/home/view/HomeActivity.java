/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.networkconnect.home.home.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.networkconnect.R;
import com.example.android.networkconnect.home.home.DownloadCallback;
import com.example.android.networkconnect.home.home.view.adapter.RickAndMortyAdapter;
import com.example.android.networkconnect.home.home.viewmodel.HomeViewModel;

/**
 * Sample Activity demonstrating how to connect to the network and fetch raw
 * HTML. It uses a Fragment that encapsulates the network operations on an AsyncTask.
 *
 * This sample uses a TextView to display output.
 */
public class HomeActivity extends FragmentActivity implements DownloadCallback {

    private String RICK_AND_MORTY_API = "https://rickandmortyapi.com/api/character";
    private String GOOGLE = "https://www.google.com";
//    private ListView rickAndMortyList;
    private ProgressBar spinner;

    // Keep a reference to the NetworkFragment which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment mNetworkFragment;

    // ViewModel
    private HomeViewModel viewModel;

    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean mDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), RICK_AND_MORTY_API);
        setUpView();
    }


    private void setUpView() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.rickAndMortyList = (ListView) findViewById(R.id.rick_and_morty_list_id);
        spinner = (ProgressBar)findViewById(R.id.spinner_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // When the user clicks FETCH, fetch the first 500 characters of
            // raw HTML from www.google.com.
            case R.id.fetch_action:
                startDownload();
                return true;
            // Clear the text and cancel download.
            case R.id.clear_action:
                finishDownloading();
                return true;
        }
        return false;
    }

    private void startDownload() {
        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload();
            mDownloading = true;
        }
    }

    @Override
    public void updateFromDownload(String result) {
        if (result != null) {
            RickAndMortyAdapter adapter = viewModel.fetchDataByParseObject(result, this);
            viewModel.rickAndMortyList.setAdapter(adapter);
            return;
        }
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                spinner.setVisibility(View.GONE);
                break;
            case Progress.CONNECT_SUCCESS:
                spinner.setVisibility(View.GONE);
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                spinner.setVisibility(View.GONE);
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                spinner.setVisibility(View.VISIBLE);
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                spinner.setVisibility(View.GONE);
                break;
        }
    }
}
