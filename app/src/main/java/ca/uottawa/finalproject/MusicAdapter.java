package ca.uottawa.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Adapter for Musics list
 *
 * this adapter is used for loading the content of an album in it's layout (R.layout.music_adapter)
 */
public class MusicAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Music> musics;
    private LayoutInflater inflater;

    public MusicAdapter(Context context, ArrayList<Music> musics) {
        super(context, R.layout.album_adapter);
        this.context = context;
        this.musics = musics;
        inflater = LayoutInflater.from(context);
    }

    public MusicAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Object getItem(int position) {
        return musics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.music_adapter, null);
        TextView musicNameTextView = view.findViewById(R.id.music_name_tv);
        musicNameTextView.setText(musics.get(position).getStrTrack());
        return view;
    }
}

