package com.example.coronatest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    TextView country;
    ListView lstItems;
    List<Map<String, String>> result;
    List<Map<String, String>> searchResults;

    String[] from;
    int[] views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        country = (TextView) findViewById(R.id.country);
        lstItems = (ListView) findViewById(R.id.lstItems);
        from = new String[]{"Country", "TotalCases", "NewCases", "TotalDeaths", "NewDeaths", "TotalRecovered", "ActiveCases",
                "SeriousCritical", "TotCasesPerMillion", "TotDeathsPerMillion", "TotTests", "TotTestsPerMillion"};
        views = new int[]{R.id.txtCountry, R.id.txtTotalCases, R.id.txtNewCases, R.id.txtTotalDeaths, R.id.txtNewDeaths, R.id.txtTotalRecovered,
                R.id.txtActiveCases, R.id.txtSeriousCritical, R.id.txtTotCasePerMillion, R.id.txtTotDeathsPerMillion, R.id.txtTotalTests, R.id.txtTotTestsPerMillion};
        new GetCoronaStats().execute();

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hello", "hello");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        Log.i("query", query+"");
        filterCoronaStats(query);
        return true;

    }

    class GetCoronaStats extends AsyncTask<Void, Void, Void> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        String jsonString = "";
        String inputLine;


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(getString(R.string.rapidapi_covid_url));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod(REQUEST_METHOD);
                urlConnection.setRequestProperty("x-rapidapi-host", getString(R.string.rapidapi_host));
                urlConnection.setRequestProperty("x-rapidapi-key", getString(R.string.rapidapi_key));
                urlConnection.setReadTimeout(READ_TIMEOUT);
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.connect();

                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while((inputLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                inputStreamReader.close();
                bufferedReader.close();

                jsonString = stringBuilder.toString();
                Log.i("jsonArray----------->", jsonString);
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("countries_stat");
                outputTranslations(jsonArray);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, result, R.layout.lst_items, from, views);
            lstItems.setAdapter(simpleAdapter);
        }


        protected void outputTranslations(JSONArray jsonArray) throws JSONException {
            result = new ArrayList<Map<String, String>>();
            if((jsonArray != null)) {
                for(int i=1; i<jsonArray.length(); i++) {
                    Log.i("----------jsonObj---->", jsonArray.get(i).toString());
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Map<String, String> datanum= new HashMap<String, String>();
                    datanum.put("Country", String.valueOf(jsonObject.getString("country_name")));
                    datanum.put("TotalCases", String.valueOf(jsonObject.getString("cases")));
                    datanum.put("NewCases", String.valueOf(jsonObject.getString("new_cases")));
                    datanum.put("TotalDeaths", String.valueOf(jsonObject.getString("deaths")));
                    datanum.put("NewDeaths", String.valueOf(jsonObject.getString("new_deaths")));
                    datanum.put("TotalRecovered", String.valueOf(jsonObject.getString("total_recovered")));
                    datanum.put("ActiveCases", String.valueOf(jsonObject.getString("active_cases")));
                    datanum.put("SeriousCritical", String.valueOf(jsonObject.getString("serious_critical")));
                    datanum.put("TotCasesPerMillion", String.valueOf(jsonObject.getString("total_cases_per_1m_population")));
                    datanum.put("TotDeathsPerMillion", String.valueOf(jsonObject.getString("deaths_per_1m_population")));
                    datanum.put("TotTests", String.valueOf(jsonObject.getString("total_tests")));
                    datanum.put("TotTestsPerMillion", String.valueOf(jsonObject.getString("tests_per_1m_population")));
                    result.add(datanum);
                }
            }
        }
    }

    public void filterCoronaStats(String query) {
        String country;
        searchResults = new ArrayList<Map<String, String>>();
        for(int i=0; i<result.size(); i++) {
            country = result.get(i).get("Country").toString().trim();
            if(query.length() <= country.length()) {
                if(query.equalsIgnoreCase(country.substring(0, query.length()))) {
                    searchResults.add(result.get(i));
                }
            }
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, searchResults, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }
}
