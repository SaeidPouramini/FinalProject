package ca.uottawa.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Fragment for loading an album musics
 *
 * This fragment loads the musics of an album by getting the 'idAlbum', 'strAlbum' and 'strArtist'
 * from the Extras
 */
public class AlbumDetailFragment extends Fragment {

    private ProgressDialog p;
    private ListView musicsListView;
    private ArrayList<Music> musics;
    private TextView albumNameTextView;
    private Button saveAlbumButton;
    private DBManager dbManager;
    private String strArtist;
    String idAlbum;
    String strAlbum;
    String URL1;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        musics = new ArrayList<>();
        return inflater.inflate(R.layout.activity_album_detail_fragment, container, false);
    }




    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = getActivity().getIntent();
        idAlbum = intent.getExtras().getString("idAlbum");
        strAlbum = intent.getExtras().getString("strAlbum");
        strArtist = intent.getExtras().getString("strArtist");

        musicsListView = view.findViewById(R.id.musics_lv);
        albumNameTextView = view.findViewById(R.id.detail_album_name_tv);
        saveAlbumButton = view.findViewById(R.id.save_album_btn);
        dbManager = new DBManager(getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        if(cursor.moveToFirst()) {
            do {
                if (cursor.getString(0).equals(idAlbum)) {
                    saveAlbumButton.setText("Delete");
                   // saveAlbumButton.setBackgroundColor(Color.RED);
                }
            } while (cursor.moveToNext());
        }

        albumNameTextView.setText(strAlbum);

        AlbumDetailFragment.MusicGrabber musicGrabber = new AlbumDetailFragment.MusicGrabber();
        URL1 = "https://theaudiodb.com/api/v1/json/1/track.php?m=" + idAlbum;
        musicGrabber.execute(URL1);

        saveAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveAlbumButton.getText().toString().equals("Save")) {
                    dbManager.insert(idAlbum, strAlbum, strArtist);
                    Toast.makeText(getContext(), "Album Saved Successfully"
                            , Toast.LENGTH_SHORT).show();
                    saveAlbumButton.setText("Delete");
                    saveAlbumButton.setBackgroundColor(Color.RED);
                } else {
                    dbManager.delete(idAlbum);
                    Toast.makeText(getContext(), "Album Deleted Successfully"
                            , Toast.LENGTH_SHORT).show();
                    saveAlbumButton.setText("Save");
                    saveAlbumButton.setBackgroundColor(Color.parseColor("#43A047"));
                }
            }
        });
    }

    /**
     * Class for Music loading from API with AsyncTask
     *
     * This class uses the link 'https://theaudiodb.com/api/v1/json/1/track.php?m='
     * to get the musics list of an album
     */
    private class MusicGrabber extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getContext());
            p.setMessage("Loading ...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
            musics.clear();
        }
        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL(URL1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String resultJson = sb.toString(); //result is the whole string
                JSONObject Report = new JSONObject(resultJson);

                JSONArray userArray = Report.getJSONArray("track");
                // implement for loop for getting users list data
                for (int i = 0; i < userArray.length(); i++) {
                    // create a JSONObject for fetching single user data
                    String strTrack = userArray.getJSONObject(i).getString("strTrack");
                    // fetch email and name and store it in arraylist
                    musics.add(new Music(strTrack));


                }

                //get the double associated with "value"

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Done";

        }

        @Override
        public void onPostExecute(String fromDoInBackground) {

            p.dismiss();

            MusicAdapter musicAdapter = new MusicAdapter(getActivity().getApplicationContext(), musics);
            musicsListView.setAdapter(musicAdapter);
            musicsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String query = "http://www.google.com/search?q="
                            + musics.get(position).getStrTrack() + "+" + strArtist;
                    intent.setData(Uri.parse(query));
                    startActivity(intent);
                }
            });
        }
    }

}
