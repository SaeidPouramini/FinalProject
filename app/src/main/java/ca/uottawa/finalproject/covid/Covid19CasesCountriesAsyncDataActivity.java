package ca.uottawa.finalproject.covid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ca.uottawa.finalproject.R;

/**
 * This class will connect to internet and will fetch the COVID-19 records for provided country and the date duration.
 */
public class Covid19CasesCountriesAsyncDataActivity extends AppCompatActivity {

    /**
     * strings to hold data from previous screen
     */
    private String country, from_date, to_date;
    /**
     * list view to display a list of data
     */
    private ListView listViewCountry;
    /**
     * list to hold data
     */
    private List<Covid19Case> covid19Cases = new ArrayList<>();
    /**
     * adapter for the list view
     */
    private MyCovid19CountriesAdapter adapter;
    /**
     * progressbar to show loading while fetching data from internet and json parsing
     */
    private ProgressBar progressBar;
    /**
     * identify whether a device is tablet or not
     */
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_19_cases_countries_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        country = extras.getString("country");
        from_date = extras.getString("from_date");
        to_date = extras.getString("to_date");


        listViewCountry = findViewById(R.id.list_view_counntry);
        progressBar = findViewById(R.id.progress);

        adapter = new MyCovid19CountriesAdapter();
        listViewCountry.setAdapter(adapter);

        listViewCountry.setOnItemClickListener((parent, view, position, id) -> {

            Bundle dataToPass = new Bundle();
            dataToPass.putLong("ID", covid19Cases.get(position).getId());
            dataToPass.putString("COUNTRY", covid19Cases.get(position).getCountry());
            dataToPass.putString("COUNTRYCODE", covid19Cases.get(position).getCountryCode());
            dataToPass.putString("PROVINCE", covid19Cases.get(position).getProvince());
            dataToPass.putString("CITY", covid19Cases.get(position).getCity());
            dataToPass.putString("CITYCODE", covid19Cases.get(position).getCityCode());
            dataToPass.putString("LAT", covid19Cases.get(position).getLat());
            dataToPass.putString("LON", covid19Cases.get(position).getLon());
            dataToPass.putLong("CASES", covid19Cases.get(position).getCases());
            dataToPass.putString("STATUS", covid19Cases.get(position).getStatus());
            dataToPass.putString("DATE", covid19Cases.get(position).getDate());

            if (isTablet) {
                Covid19CaseDetailsFragment covid19CaseDetailsFragment = new Covid19CaseDetailsFragment();
                covid19CaseDetailsFragment.setArguments(dataToPass);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.my_fragment, covid19CaseDetailsFragment)
                        .commit();
            } else {
                Intent nextActivity = new Intent(this, Covid19CasesDataEmptyActivity.class);
                nextActivity.putExtras(dataToPass);
                startActivity(nextActivity);
            }

        });

        listViewCountry.setOnItemLongClickListener((parent, view, position, id) -> {

            new AlertDialog.Builder(this)
                    .setTitle("Details")
                    .setMessage(
                            getString(R.string.c19_position) + position +
                                    "\n" + getString(R.string.c19_country) + covid19Cases.get(position).getCountry() +
                                    "\n" + getString(R.string.c19_country_code) + covid19Cases.get(position).getCountryCode() +
                                    "\n" + getString(R.string.c19_province) + covid19Cases.get(position).getProvince() +
                                    "\n" + getString(R.string.c19_city) + covid19Cases.get(position).getCity() +
                                    "\n" + getString(R.string.c19_city_code) + covid19Cases.get(position).getCityCode() +
                                    "\n" + getString(R.string.c19_latitude) + covid19Cases.get(position).getLat() +
                                    "\n" + getString(R.string.c19_longitude) + covid19Cases.get(position).getLon() +
                                    "\n" + getString(R.string.c19_cases) + covid19Cases.get(position).getCases() +
                                    "\n" + getString(R.string.c19_status) + covid19Cases.get(position).getStatus() +
                                    "\n" + getString(R.string.c19_date) + covid19Cases.get(position).getDate()
                    )
                    .setPositiveButton(R.string.c19_ok, null)
                    .show();
            return true;

        });

        isTablet = findViewById(R.id.my_fragment) != null;

        Covid19CasesCountryListTask task = new Covid19CasesCountryListTask();
        task.execute("https://api.covid19api.com/country/" + country + "/status/confirmed/live?from=" + from_date + "T00:00:00Z&to=" + to_date + "T00:00:00Z");

    }

    /**
     * Adapter class to display each row in list view
     */
    private class MyCovid19CountriesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return covid19Cases.size();
        }

        @Override
        public Covid19Case getItem(int position) {
            return covid19Cases.get(position);
        }

        @Override
        public long getItemId(int position) {
            return covid19Cases.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            Covid19Case covid19Case = getItem(position);

            View newView = inflater.inflate(R.layout.row_layout_covid_19_case, parent, false);

            TextView textviewProvince = newView.findViewById(R.id.textview_province);
            textviewProvince.setText(getString(R.string.c19_province) + covid19Case.getProvince());
            TextView textviewContry = newView.findViewById(R.id.textview_contry);
            textviewContry.setText(getString(R.string.c19_country) + covid19Case.getCountry());
            TextView textviewCases = newView.findViewById(R.id.textview_cases);
            textviewCases.setText(getString(R.string.c19_cases) + covid19Case.getCases());
            TextView textviewDate = newView.findViewById(R.id.textview_date);
            textviewDate.setText(getString(R.string.c19_date) + covid19Case.getDate());

            return newView;
        }
    }


    private class Covid19CasesCountryListTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL(strings[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, StandardCharsets.UTF_8), 8);
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) sb.append(line + "\n");
                String result = sb.toString();

                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Covid19Case covid19Case = new Covid19Case(
                            jsonObject.getString("Country"),
                            jsonObject.getString("CountryCode"),
                            jsonObject.getString("Province"),
                            jsonObject.getString("City"),
                            jsonObject.getString("CityCode"),
                            jsonObject.getString("Lat"),
                            jsonObject.getString("Lon"),
                            jsonObject.getLong("Cases"),
                            jsonObject.getString("Status"),
                            jsonObject.getString("Date")
                    );

                    covid19Cases.add(covid19Case);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();

            if (covid19Cases.isEmpty()) {
                Snackbar.make(findViewById(R.id.list_view_counntry), "No cases found for: " + country, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(findViewById(R.id.list_view_counntry), "Showing covid-19 cases for: " + country, Snackbar.LENGTH_SHORT).show();
            }


        }
    }

}