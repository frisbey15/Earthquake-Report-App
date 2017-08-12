package com.example.mohit.earthquakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by mohit on 19/5/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquakes>> {

    private String mUrl;
    public EarthquakeLoader(Context context,String requestedUrl) {
        super(context);
        mUrl=requestedUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    //performs the task in background of fetching the data from URL and returning as {ArrayList} object
    @Override
    public ArrayList<Earthquakes> loadInBackground() {
        ArrayList<Earthquakes> earthquakes=QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }

}
