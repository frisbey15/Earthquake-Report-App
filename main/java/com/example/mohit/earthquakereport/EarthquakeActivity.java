package com.example.mohit.earthquakereport;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.app.LoaderManager;
import android.content.Loader;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Earthquakes>>  {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query";
    //setting up the word adapter to store in the arraylist{earthquake}data
    WordAdapter mAdapter;
    private static final int EARTHQUAKE_LOADER_ID=1;
     ListView earthquakeListView ;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        // Create a list of earthqauke location by calling the network connection class

        // ArrayList<Earthquakes> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        //ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        //WordAdapter adapter = new WordAdapter(
        //      this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        //earthquakeListView.setAdapter(adapter);
        /*the following code is for the procedure2 in which we use Async task to complete the fetch*/
        //QuakeConnection quakeConnection = new QuakeConnection();
        //quakeConnection.execute(USGS_REQUEST_URL);

        //set the adapter to store an empty array list
        // Find a reference to the {@link ListView} in the layout



        mAdapter=new WordAdapter(this,new ArrayList<Earthquakes>());
        earthquakeListView= (ListView) findViewById(R.id.list);
        earthquakeListView.setAdapter(mAdapter);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        //runs the desired code if and only if the device is connected to internet
        if(checkNetworkConnectivity()) {

        /*the following code is for procedure 3 in which we use LoaderAsynctask to complete the fetch*/
            getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);


            //setting up an onClickListener for any View such that we go to a specified url whenever
            //a click event occurs for a particular list item
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    //get the current position of the {earthquake } object that we clicked on
                    Earthquakes earthquake = mAdapter.getItem(position);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.getPageUrl()));
                    startActivity(browserIntent);
                }
            });
        }
        //else it will show that the device is not connected to the internet in a textview
        else{
            ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            TextView noDataText=(TextView)findViewById(R.id.noDataText);
            noDataText.setText("No Network Connection");
            noDataText.setVisibility(View.VISIBLE);
        }
    }

    public void refreshContent(){
        Handler h=new Handler();
        h.postDelayed(new Runnable(){

            public void run(){
                //runs the desired code if and only if the device is connected to internet
                if(checkNetworkConnectivity()) {

        /*the following code is for procedure 3 in which we use LoaderAsynctask to complete the fetch*/
                    getLoaderManager().restartLoader(EARTHQUAKE_LOADER_ID, null, EarthquakeActivity.this);


                    //setting up an onClickListener for any View such that we go to a specified url whenever
                    //a click event occurs for a particular list item
                    earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            //get the current position of the {earthquake } object that we clicked on
                            Earthquakes earthquake = mAdapter.getItem(position);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.getPageUrl()));
                            startActivity(browserIntent);
                        }
                    });
                }
                //else it will show that the device is not connected to the internet in a textview
                else{
                    ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.INVISIBLE);
                    TextView noDataText=(TextView)findViewById(R.id.noDataText);
                    noDataText.setText("No Network Connection");
                    noDataText.setVisibility(View.VISIBLE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }
    //to provide the options menu to the app
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
       //this is the method where we made a class that extends AsyncTask to fetch the data from
       //the USGS api and display it on the screen
       //but now we will do the same with the help of the Loaders to save the memory and many more
       //reason where loaders are of great help as that of AsyncTask

    private class QuakeConnection extends AsyncTask<String,Void,ArrayList<Earthquakes>>{

        @Override
        protected ArrayList<Earthquakes> doInBackground(String... params) {
            String url=params[0];
            ArrayList<Earthquakes> earthquakes=QueryUtils.fetchEarthquakeData(url);
            return earthquakes;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquakes> earthquakes) {
            // Find a reference to the {@link ListView} in the layout
           // ListView earthquakeListView = (ListView) findViewById(R.id.list);
            mAdapter.clear();
            // Create a new {@link ArrayAdapter} of earthquakes
          // WordAdapter adapter = new WordAdapter(EarthquakeActivity.this, earthquakes);
            mAdapter.addAll(earthquakes);
            //earthquakeListView.setAdapter(adapter);
        }
    }
    */

    //these are the methods that we need to override , so that our loaderManager can use them
    //in order to complete the required task done
    @Override
    public Loader<ArrayList<Earthquakes>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG,"inside the onCreateLoader");

        //returns a shared preference instance pointing to the file that contains the value of the
        //preferences.
        //shared preferences allow you to save data in the form of key value pair
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        String minMagnitude=sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy=sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        String entries=sharedPreferences.getString(
                getString(R.string.setting_no_of_entries_key),
                getString(R.string.settings_no_of_entries_default));

        Uri baseUri=Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder=baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format","geojson");
        uriBuilder.appendQueryParameter("starttime","2016-01-01");
        uriBuilder.appendQueryParameter("limit",entries);
        uriBuilder.appendQueryParameter("minmag",minMagnitude);
        uriBuilder.appendQueryParameter("orderby",orderBy);

        Log.v(LOG_TAG,"the value of the limit of data is: ------"+entries);


        return new EarthquakeLoader(this,uriBuilder.toString());

    }
    //to make the changes in the UI
    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquakes>> loader, ArrayList<Earthquakes> earthquakes) {
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        //clear the adapter of previous earthquake data
        mAdapter.clear();
        //this will be shown in case when no data is fetched by the net
        earthquakeListView.setEmptyView(findViewById(R.id.noDataText));
        //add the {earthquakes}arraylist data to the adapter to be displayed on the screen
        if(earthquakes!=null&&!earthquakes.isEmpty())
            mAdapter.addAll(earthquakes);
        Log.v(LOG_TAG,"inside the onLoadFinished ");

    }
    //whenever we need to reset the data of the loader
    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquakes>> loader) {
        //loader reset, so we can clear out the adapter data that we have stored in {madapter}
        mAdapter.clear();
    }
    //to check whether the device is connected to internet or not
    public boolean checkNetworkConnectivity(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
        Log.v(LOG_TAG,"the status of network is :"+isConnected);
        return isConnected;

    }


}
