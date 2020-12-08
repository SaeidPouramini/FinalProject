package ca.uottawa.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.covidbutton).setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity.this, MainActivityCovid.class));
        });
        findViewById(R.id.Recipebutton2).setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity.this, Recipe_activity.class));

        });
                findViewById(R.id.audiobutton3).setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity.this, MainActivityAudio.class));
                });
                    findViewById(R.id.ticketbutton4).setOnClickListener(v -> {
                        startActivity(new Intent(MainActivity.this, TicketMasterEventSearchActivity.class));
                    });
    }
}