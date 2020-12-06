package ca.uottawa.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.List;

/**
 * This activity will display the COVID-19 cases stored in the database.
 */
public class Covid19CasesCountriesDatabaseDataActivity extends AppCompatActivity {
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
    /**
     * Opener for the database
     */
    private MyCovid19CountriesDBOpener dbOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_19_cases_countries_data);

        dbOpener = new MyCovid19CountriesDBOpener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                            "Position: " + position +
                                    "\n" + "Country: " + covid19Cases.get(position).getCountry() +
                                    "\n" + "CountryCode: " + covid19Cases.get(position).getCountryCode() +
                                    "\n" + "Province: " + covid19Cases.get(position).getProvince() +
                                    "\n" + "City: " + covid19Cases.get(position).getCity() +
                                    "\n" + "CityCode: " + covid19Cases.get(position).getCityCode() +
                                    "\n" + "Lat: " + covid19Cases.get(position).getLat() +
                                    "\n" + "Lon: " + covid19Cases.get(position).getLon() +
                                    "\n" + "Cases: " + covid19Cases.get(position).getCases() +
                                    "\n" + "Status: " + covid19Cases.get(position).getStatus() +
                                    "\n" + "Date: " + covid19Cases.get(position).getDate()
                    )
                    .setPositiveButton("Ok", null)
                    .show();
            return true;

        });

        isTablet = findViewById(R.id.my_fragment) != null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataFromDatabase();
    }

    /**
     * This method will read all the rows from database table and put each row into the list
     */
    private void loadDataFromDatabase() {
        covid19Cases = new ArrayList<>();
        SQLiteDatabase db = dbOpener.getReadableDatabase();

        String[] columns = {MyCovid19CountriesDBOpener.COL_ID, MyCovid19CountriesDBOpener.COL_COUNTRY, MyCovid19CountriesDBOpener.COL_COUNTRYCODE, MyCovid19CountriesDBOpener.COL_PROVINCE, MyCovid19CountriesDBOpener.COL_CITY, MyCovid19CountriesDBOpener.COL_CITYCODE, MyCovid19CountriesDBOpener.COL_LAT, MyCovid19CountriesDBOpener.COL_LON, MyCovid19CountriesDBOpener.COL_CASES, MyCovid19CountriesDBOpener.COL_STATUS, MyCovid19CountriesDBOpener.COL_DATE};

        Cursor results = db.query(MyCovid19CountriesDBOpener.TABLE_COVID_19_CASES, columns, null, null, null, null, null);

        int indexId = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_ID);
        int indexCountry = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_COUNTRY);
        int indexCountryCode = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_COUNTRYCODE);
        int indexProvince = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_PROVINCE);
        int indexCity = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_CITY);
        int indexCityCode = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_CITYCODE);
        int indexLat = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_LAT);
        int indexLon = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_LON);
        int indexCases = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_CASES);
        int indexStatus = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_STATUS);
        int indexDate = results.getColumnIndex(MyCovid19CountriesDBOpener.COL_DATE);

        while (results.moveToNext()) {
            Covid19Case covid19Case = new Covid19Case(
                    results.getLong(indexId),
                    results.getString(indexCountry),
                    results.getString(indexCountryCode),
                    results.getString(indexProvince),
                    results.getString(indexCity),
                    results.getString(indexCityCode),
                    results.getString(indexLat),
                    results.getString(indexLon),
                    results.getLong(indexCases),
                    results.getString(indexStatus),
                    results.getString(indexDate)
            );
            covid19Cases.add(covid19Case);
        }
        results.close();

        progressBar.setVisibility(View.INVISIBLE);
        adapter.notifyDataSetChanged();

        if (covid19Cases.isEmpty()) {
            Snackbar.make(findViewById(R.id.list_view_counntry), "No data available in database", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(findViewById(R.id.list_view_counntry), "Loaded data from database", Snackbar.LENGTH_SHORT).show();
        }
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
            textviewProvince.setText("Province: " + covid19Case.getProvince());
            TextView textviewContry = newView.findViewById(R.id.textview_contry);
            textviewContry.setText("Contry: " + covid19Case.getCountry());
            TextView textviewCases = newView.findViewById(R.id.textview_cases);
            textviewCases.setText("Cases: " + covid19Case.getCases());
            TextView textviewDate = newView.findViewById(R.id.textview_date);
            textviewDate.setText("Date: " + covid19Case.getDate());

            return newView;
        }
    }


}