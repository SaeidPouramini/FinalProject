package ca.uottawa.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ca.uottawa.finalproject.covid.Covid19CountryEnterActivity;
import ca.uottawa.finalproject.ticketmaster.TicketMasterEventSearchActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.covidbutton).setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity.this, Covid19CountryEnterActivity.class));
        });

        findViewById(R.id.Recipebutton2).setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity.this, Recipe_Activity.class));

        });
                findViewById(R.id.audiobutton3).setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity.this, MainActivityAudio.class));
                });
                    findViewById(R.id.ticketbutton4).setOnClickListener(v -> {
                        startActivity(new Intent(MainActivity.this, TicketMasterEventSearchActivity.class));
                    });
    }
}