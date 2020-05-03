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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView lstItems;
    TextView totalCases, newCases, totalDeaths, newDeaths, totalRecovered, activeCases,
            seriousCritical, totCasesPerMillion, totDeathsPerMillion, totTests, totTestsPerMillion;

    public static boolean totalCasesDescending = true, newCasesDescending = true, totalDeathsDescending = true,
            newDeathsDescending = true, totalRecoveredDescending = true, activeCasesDescending = true,
            seriousCriticalDescending = true, totCasesPerMillionDescending= true, totDeathsPerMillionDescending = true,
            totTestsDescending= true, totTestsPerMillionDescending= true;

    List<Map<String, String>> results;
    List<Map<String, String>> searchResults;

    private String[] from;
    private int[] views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeViews();
        this.setListeners();
        new GetCoronaStats().execute();

    }

    private void initializeViews() {
        lstItems = (ListView) findViewById(R.id.lstItems);
        totalCases = (TextView) findViewById(R.id.totalCases);
        newCases = (TextView) findViewById(R.id.newCases);
        totalDeaths = (TextView) findViewById(R.id.totalDeaths);
        newDeaths = (TextView) findViewById(R.id.newDeaths);
        totalRecovered = (TextView) findViewById(R.id.totalRecovered);
        activeCases = (TextView) findViewById(R.id.activeCases);
        seriousCritical = (TextView) findViewById(R.id.seriousCritical);
        totCasesPerMillion = (TextView) findViewById(R.id.totCasesPerMillion);
        totDeathsPerMillion = (TextView) findViewById(R.id.totDeathsPerMillion);
        totTests = (TextView) findViewById(R.id.totTests);
        totTestsPerMillion = (TextView) findViewById(R.id.totTestsPerMillion);

        from = new String[]{"Country", "TotalCases", "NewCases", "TotalDeaths", "NewDeaths", "TotalRecovered", "ActiveCases",
                "SeriousCritical", "TotCasesPerMillion", "TotDeathsPerMillion", "TotTests", "TotTestsPerMillion"};
        views = new int[]{R.id.txtCountry, R.id.txtTotalCases, R.id.txtNewCases, R.id.txtTotalDeaths, R.id.txtNewDeaths,
                R.id.txtTotalRecovered, R.id.txtActiveCases, R.id.txtSeriousCritical, R.id.txtTotCasePerMillion,
                R.id.txtTotDeathsPerMillion, R.id.txtTotalTests, R.id.txtTotTestsPerMillion};
    }

    private void setListeners() {
        totalCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByTotalCases();
            }
        });

        newCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByNewCases();
            }
        });

        totalDeaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByTotalDeaths();
            }
        });

        newDeaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByNewDeaths();
            }
        });

        totalRecovered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByTotalRecovered();
            }
        });

        activeCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByActiveCases();
            }
        });

        seriousCritical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortBySeriousCritical();
            }
        });

        totCasesPerMillion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sortByTotCasesPerMillion();
            }
        });

        totDeathsPerMillion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByTotDeathsPerMillion();
            }
        });

        totTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByTotTests();
            }
        });

        totTestsPerMillion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sortByTotTestsPerMillion();
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
            sortByTotalCases();
        }


        protected void outputTranslations(JSONArray jsonArray) throws JSONException {
            results = new ArrayList<Map<String, String>>();
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
                    results.add(datanum);
                }
            }
        }

//        protected void outputTranslations(JSONArray jsonArray) throws JSONException {
//            arrayList = new ArrayList<Corona>();
//            if((jsonArray != null)) {
//                for(int i=1; i<jsonArray.length(); i++) {
//                    Log.i("----------jsonObj---->", jsonArray.get(i).toString());
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    arrayList.add(new Corona(String.valueOf(jsonObject.getString("country_name")),
//                            String.valueOf(jsonObject.getString("cases")),
//                            String.valueOf(jsonObject.getString("new_cases")),
//                            String.valueOf(jsonObject.getString("deaths")),
//                            String.valueOf(jsonObject.getString("new_deaths")),
//                            String.valueOf(jsonObject.getString("total_recovered")),
//                            String.valueOf(jsonObject.getString("active_cases")),
//                            String.valueOf(jsonObject.getString("serious_critical")),
//                            String.valueOf(jsonObject.getString("total_cases_per_1m_population")),
//                            String.valueOf(jsonObject.getString("deaths_per_1m_population")),
//                            String.valueOf(jsonObject.getString("total_tests")),
//                            String.valueOf(jsonObject.getString("tests_per_1m_population")));
//                }
//            }
//        }
    }

    public void filterCoronaStats(String query) {
        String country;
        searchResults = new ArrayList<Map<String, String>>();
        for(int i=0; i<results.size(); i++) {
            country = results.get(i).get("Country").toString().trim();
            if(query.length() <= country.length()) {
                if(query.equalsIgnoreCase(country.substring(0, query.length()))) {
                    searchResults.add(results.get(i));
                }
            }
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, searchResults, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByTotalCases() {
        Collections.sort(results, SortFunctions.totalCasesComparator);
        totalCasesDescending = !totalCasesDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByNewCases() {
        Collections.sort(results, SortFunctions.newCasesComparator);
        newCasesDescending = !newCasesDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByTotalDeaths() {
        Collections.sort(results, SortFunctions.totalDeathsComparator);
        totalDeathsDescending = !totalDeathsDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByNewDeaths() {
        Collections.sort(results, SortFunctions.newDeathsComparator);
        newDeathsDescending = !newDeathsDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByTotalRecovered() {
        Collections.sort(results, SortFunctions.totalRecoveredComparator);
        totalRecoveredDescending = !totalRecoveredDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByActiveCases() {
        Collections.sort(results, SortFunctions.activeCasesComparator);
        activeCasesDescending = !activeCasesDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortBySeriousCritical() {
        Collections.sort(results, SortFunctions.seriousCriticalComparator);
        seriousCriticalDescending = !seriousCriticalDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByTotCasesPerMillion() {
        Collections.sort(results, SortFunctions.totCasesPerMillionComparator);
        totCasesPerMillionDescending = !totCasesPerMillionDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByTotDeathsPerMillion() {
        Collections.sort(results, SortFunctions.totDeathsPerMillionComparator);
        totDeathsPerMillionDescending = !totDeathsPerMillionDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByTotTests() {
        Collections.sort(results, SortFunctions.totTestsComparator);
        totTestsDescending = !totTestsDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }

    public void sortByTotTestsPerMillion() {
        Collections.sort(results, SortFunctions.totTestsPerMillionComparator);
        totTestsPerMillionDescending = !totTestsPerMillionDescending;
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, results, R.layout.lst_items, from, views);
        lstItems.setAdapter(simpleAdapter);
    }


}
