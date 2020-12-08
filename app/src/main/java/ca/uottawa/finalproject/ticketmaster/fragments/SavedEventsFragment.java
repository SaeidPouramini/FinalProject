package ca.uottawa.finalproject.ticketmaster.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

import ca.uottawa.finalproject.R;
import ca.uottawa.finalproject.ticketmaster.database.TicketMasterDatabase;
import ca.uottawa.finalproject.ticketmaster.models.Event;

/**
 * SavedEventsFragment - Loads the user's saved events
 * @author Sundus Dehaibi
 */
public class SavedEventsFragment extends ListFragment {
    private static final String TAG = SavedEventsFragment.class.getSimpleName();
    private TicketMasterDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.saved_events_title);
        db = new TicketMasterDatabase(getActivity());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        setEmptyText(getString(R.string.no_events_found_msg));
        loadSavedEvents();
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.ticket_master_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        switch(item.getItemId())
        {
            case R.id.search_item:

                SearchEventsFragment searchEventsFragment = new SearchEventsFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, searchEventsFragment)
                        .commit();
                Toast.makeText(getActivity(), R.string.search_events_title, Toast.LENGTH_LONG).show();
                break;
            case R.id.saved_item:
                break;
            case R.id.help_item:
                HelpDialog.show(getActivity(), R.string.help_saved);
                break;
        }

        return true;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Log.d(TAG, "position: " + position);
        Log.d(TAG, "Event: " + getListAdapter().getItem(position));
        Bundle dataToPass = Event.exportEventToBundle((Event)getListAdapter().getItem(position));
        dataToPass.putBoolean("fromSearch", false);
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        eventDetailFragment.setArguments(dataToPass);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, eventDetailFragment, "eventDetail")
                .commit();
        super.onListItemClick(l, v, position, id);
    }


    private void loadSavedEvents() {
        setListShownNoAnimation(false);
        ArrayList<Event> eventsList = db.loadEvents();
        ListAdapter listAdapter = new ArrayAdapter<Event>(getActivity(),
                android.R.layout.simple_list_item_1, eventsList);

        setListAdapter(listAdapter);
        setListShownNoAnimation(true);
    }

}
