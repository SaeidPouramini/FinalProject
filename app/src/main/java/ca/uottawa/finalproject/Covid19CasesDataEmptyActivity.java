package ca.uottawa.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Empty activity to display fragment on phone or non-tablet devices
 */
public class Covid19CasesDataEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_19_cases_data_empty);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Covid19CaseDetailsFragment covid19CaseDetailsFragment = new Covid19CaseDetailsFragment();
        covid19CaseDetailsFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.my_fragment, covid19CaseDetailsFragment)
                .commit();

    }

}