package ca.uottawa.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivityCovid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_covid);

        findViewById(R.id.covid19CaseData).setOnClickListener(v -> {
            startActivity(new Intent(MainActivityCovid.this, Covid19CountryEnterActivity.class));
        });

    }
}