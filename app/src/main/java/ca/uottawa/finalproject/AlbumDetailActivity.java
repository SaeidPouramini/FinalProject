package ca.uottawa.finalproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject2.R;

/**
 * Activity for showing an album's detail
 *
 * this activity uses a toolbar and a fragment for the content
 */
public class AlbumDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_album_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     //  * @param item
     * @return
     *
     * Method for handling the usage of options menu
     */
    //  @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_activity_help:
                String message = "You can search artist's albums by typing the artist name in the " +
                        "'Artist' edit text and then tapping the search button. " +
                        "\nThen you can get more details about an album by tapping on the album.";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Help");
                alertDialogBuilder.setMessage(message);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            case R.id.main_activity_saved_album:
                Intent intent = new Intent(AlbumDetailActivity.this, SavedAlbumsActivity.class);
                startActivity(intent);
                return true;

            case R.id.main_activity_menu:
                Toast.makeText(this, "welcome to Your favorite songs", Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
