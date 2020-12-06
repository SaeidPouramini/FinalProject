package ca.uottawa.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter for Albums list
 *
 * this adapter is used for loading the content of an album in it's layout (R.layout.album_adapter)
 */
public class AlbumAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Album> albums;
    private LayoutInflater inflater;

    public AlbumAdapter(Context context, ArrayList<Album> albums) {
        super(context, R.layout.album_adapter);
        this.context = context;
        this.albums = albums;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return albums.size();
    }

    @Override
    public Object getItem(int position) {
        return albums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.album_adapter, null);
        TextView albumNameTextView = view.findViewById(R.id.album_name_tv);
        TextView albumReleaseDateTextView = view.findViewById(R.id.album_release_date_tv);
        albumNameTextView.setText(albums.get(position).getStrAlbum());
        albumReleaseDateTextView.setText(albums.get(position).getIntYearReleased());
        return view;
    }
}