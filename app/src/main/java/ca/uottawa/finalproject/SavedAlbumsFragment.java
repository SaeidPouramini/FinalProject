package ca.uottawa.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Fragment for loading saved albums from the database
 *
 * This fragment loads the saved albums of the user from the database
 */
public class SavedAlbumsFragment extends Fragment {

    private ArrayList<Album> albums;
    private DBManager dbManager;
    private ListView savedAlbumsListView;
    private AlbumAdapter albumAdapter;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_saved_albums_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savedAlbumsListView = view.findViewById(R.id.saved_albums_lv);
        albums = new ArrayList<>();
        dbManager = new DBManager(getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        if(cursor.moveToFirst())
            do{
                Album album = new Album(cursor.getString(0), ""
                        , cursor.getString(2), cursor.getString(1)
                        , "", "");
                albums.add(album);
            } while (cursor.moveToNext());

        albumAdapter = new AlbumAdapter(getContext(), albums);

        savedAlbumsListView.setAdapter(albumAdapter);
        savedAlbumsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), AlbumDetailActivity.class);
                intent.putExtra("idAlbum", albums.get(position).getIdAlbum());
                intent.putExtra("strAlbum", albums.get(position).getStrAlbum());
                intent.putExtra("strArtist", albums.get(position).getStrArtist());
                startActivity(intent);
            }
        });

    }

    /**
     * Method is called after the fragment is resumed
     *
     * in this method the list is reloaded, so if the user deletes any album, the albums shown
     * will be update.
     */
    @Override
    public void onResume() {
        super.onResume();
        albumAdapter.notifyDataSetChanged();
        Cursor cursor = dbManager.fetch();
        albums.clear();
        if(cursor.moveToFirst()) {
            do {
                Album album = new Album(cursor.getString(0), "", ""
                        , cursor.getString(1), "", "");
                albums.add(album);
            } while (cursor.moveToNext());
        }
    }

}
