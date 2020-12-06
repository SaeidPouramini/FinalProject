package ca.uottawa.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This activity will ask user to enter country, start and end date
 */
public class Covid19CountryEnterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * drawer layout for navigation drawer
     */
    private DrawerLayout drawerLayout;
    /**
     * Edittexts for entering data from user
     */
    private EditText edittextCountryName, edittextFromDate, edittextToDate;
    /**
     * Shared preferences object to store the last entered data and display next time
     */
    private SharedPreferences covid19CasesDataSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_covid19_country_enter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // NavigationDrawer:
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.c19_geo_navigation_drawer_open, R.string.c19_geo_navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        edittextCountryName = findViewById(R.id.edittextCountryName);
        edittextFromDate = findViewById(R.id.edittext_from_date);
        edittextToDate = findViewById(R.id.edittext_to_date);

        // Getting values from shared-prefs
        covid19CasesDataSharedPreferences = getSharedPreferences("Covid-19CasesData", Context.MODE_PRIVATE);
        edittextCountryName.setText(covid19CasesDataSharedPreferences.getString("country", ""));
        edittextFromDate.setText(covid19CasesDataSharedPreferences.getString("from_date", ""));
        edittextToDate.setText(covid19CasesDataSharedPreferences.getString("to_date", ""));

        findViewById(R.id.buttonFindCovidCases).setOnClickListener(v -> {

            // Validation
            String country = edittextCountryName.getText().toString();
            String from_date = edittextFromDate.getText().toString();
            String to_date = edittextToDate.getText().toString();

            if (country.isEmpty()) {
                // Toast
                Toast.makeText(this, "Please enter country name to search", Toast.LENGTH_SHORT).show();
                return;
            }
            if (from_date.isEmpty()) {
                // Toast
                Toast.makeText(this, "Please enter start date", Toast.LENGTH_SHORT).show();
                return;
            }
            if (to_date.isEmpty()) {
                // Toast
                Toast.makeText(this, "Please enter end date", Toast.LENGTH_SHORT).show();
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                sdf.parse(from_date);
            } catch (ParseException e) {
                Toast.makeText(this, "Please enter valid start date", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                sdf.parse(to_date);
            } catch (ParseException e) {
                Toast.makeText(this, "Please enter valid end date", Toast.LENGTH_SHORT).show();
                return;
            }

            // Adding into the sharedpreference
            SharedPreferences.Editor editor = covid19CasesDataSharedPreferences.edit();
            editor.putString("country", country);
            editor.putString("from_date", from_date);
            editor.putString("to_date", to_date);
            editor.commit();

            Intent intent = new Intent(this, Covid19CasesCountriesAsyncDataActivity.class);
            intent.putExtra("country", country);
            intent.putExtra("from_date", from_date);
            intent.putExtra("to_date", to_date);
            startActivity(intent);
        });

    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.covid_19_country_enter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showSavedData:
                startActivity(new Intent(this, Covid19CasesCountriesDatabaseDataActivity.class));
                break;

            case R.id.help:

                String message = "Welcome to COVID-19 app, this app gives you information regarding latest number of COVID cases in different countries and provinces.";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Help");
                alertDialogBuilder.setMessage(message);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.showSavedData:
                startActivity(new Intent(this, Covid19CasesCountriesDatabaseDataActivity.class));
                break;
        }
        return true;
    }
}