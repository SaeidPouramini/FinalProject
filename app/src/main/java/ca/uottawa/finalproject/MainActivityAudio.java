package ca.uottawa.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivityAudio extends AppCompatActivity {

    private ProgressDialog p;
    private Button artistSearchButton;
    private EditText artistEditText;
    private ListView albumsListView;
    private ArrayList<Album> albums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_audio);


        albums = new ArrayList<>();
        artistSearchButton = findViewById(R.id.search_artist_btn);
        artistEditText = findViewById(R.id.artist_edt);
        albumsListView = findViewById(R.id.albums_lv);



        artistEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                AlbumGrabber albumGrabber = new AlbumGrabber();
                String artist = artistEditText.getText().toString();
                String URL = "https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s=" + artist;
                albumGrabber.execute(URL);
                System.out.println(actionId);
                return true;
            }
        });

        artistSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlbumGrabber albumGrabber = new AlbumGrabber();
                String artist = artistEditText.getText().toString();
                String URL = "https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s=" + artist;
                if (!artist.equals("") && !artist.equals(null))
                    albumGrabber.execute(URL);
            }
        });
    }

    /**
     * @param
     * @return
     *
     * Method for creating the options menu
     */
    //  @Override

    /**
     * Class for loading Albums of an artist from API with AsyncTask
     *
     * This class uses the link 'https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s='
     * to load albums of the given artist
     */
    private class AlbumGrabber extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivityAudio.this);
            p.setMessage("Loading ...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
            albums.clear();
        }
        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = bufferedReader.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return new JSONObject(response.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            p.dismiss();
            String s = "";
            try {
                JSONArray albumArray = jsonObject.getJSONArray("album");
                for (int i = 0; i < albumArray.length(); i++) {
                    String idAlbum = albumArray.getJSONObject(i).getString("idAlbum");
                    String idArtist = albumArray.getJSONObject(i).getString("idArtist");
                    String strArtist = albumArray.getJSONObject(i).getString("strArtist");
                    String strAlbum = albumArray.getJSONObject(i).getString("strAlbum");
                    String strGenre = albumArray.getJSONObject(i).getString("strGenre");
                    String intYearReleased = albumArray.getJSONObject(i).getString("intYearReleased");
                    albums.add(new Album(idAlbum, idArtist, strArtist, strAlbum, strGenre, intYearReleased));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                //Toast.makeText(MainActivity.this, "No Network", Toast.LENGTH_SHORT)
                //.show();
                e.printStackTrace();
            }
            AlbumAdapter albumAdapter = new AlbumAdapter(getApplicationContext(), albums);
            albumsListView.setAdapter(albumAdapter);
            albumsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivityAudio.this, AlbumDetailActivity.class);
                    intent.putExtra("idAlbum", albums.get(position).getIdAlbum());
                    intent.putExtra("strAlbum", albums.get(position).getStrAlbum());
                    intent.putExtra("strArtist", albums.get(position).getStrArtist());
                    startActivity(intent);
                }
            });
        }
    }
}

