package com.example.cofigure;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class CoronaCases extends AppCompatActivity {


    private CoronaAdapter coronaAdapter;

    private TextView mEmptyStateTextView;

    private static final String COVID_DETAILS_REQUEST_URL = "https://api.rootnet.in/covid19-in/stats/latest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_cases);

        ListView listItem = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.no_connection);

        coronaAdapter = new CoronaAdapter(this, new ArrayList<coronaDetails>());

        listItem.setAdapter(coronaAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if((networkInfo != null && networkInfo.isConnected())){

            CoronaAsyncTask task = new CoronaAsyncTask();
            task.execute(COVID_DETAILS_REQUEST_URL);
        }
        else{
            View loadingIndicator = findViewById(R.id.Loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText("OOPS No Internet Connection");

        }

    }

    private class CoronaAsyncTask extends AsyncTask<String, Void, ArrayList<coronaDetails>> {

        @Override
        protected ArrayList<coronaDetails> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            ArrayList<coronaDetails> result = CoronaDataFetch.fetchCovidData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<coronaDetails> data) {

            View loadingIndicator = findViewById(R.id.Loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Clear the adapter of previous earthquake data
            coronaAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                coronaAdapter.addAll(data);
            }
        }
    }


}