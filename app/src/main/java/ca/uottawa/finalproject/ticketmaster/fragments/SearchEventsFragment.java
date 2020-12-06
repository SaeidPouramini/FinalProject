package ca.uottawa.finalproject.ticketmaster.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ca.uottawa.finalproject.R;
import ca.uottawa.finalproject.ticketmaster.models.Event;

/**
 * SearchEventsFragment - Lists the events from the location entered in the search field
 * @author Sundus Dehaibi
 */
public class SearchEventsFragment extends ListFragment {

    private static final String TAG = SearchEventsFragment.class.getSimpleName();
    private static final String PREFS_LAST_SEARCH = "LastSearch";
    private static final String PREFS_NAME = "TicketMasterEventSearch";
    private static final String TICKET_MASTER_API_URL
            = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=pW2shqMc5PDgi2UyAF8264NvOwEkDYqz&city=%s&radius=100";

    private SharedPreferences prefs = null;
    private SearchView sView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.search_events_title);
        prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
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
        setListShownNoAnimation(true);
        String lastSearch = prefs.getString(PREFS_LAST_SEARCH,"");
        requestEvents(lastSearch);

        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.ticket_master_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_item);
        sView = (SearchView)searchItem.getActionView();
        sView.setQuery(prefs.getString(PREFS_LAST_SEARCH,""), true);
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                saveSharedPrefs(query);
                requestEvents(query);
                sView .clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }  });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        switch(item.getItemId())
        {
            case R.id.search_item:
                break;
            case R.id.saved_item:
                SavedEventsFragment savedEventsFragment = new SavedEventsFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, savedEventsFragment)
                        .commit();
                break;
            case R.id.help_item:
                HelpDialog.show(getActivity(), R.string.help_search);
                break;
            default:
                sView.clearFocus();
        }
        return true;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Log.d(TAG, "position: " + position);
        Log.d(TAG, "Event: " + getListAdapter().getItem(position));
        Bundle dataToPass = Event.exportEventToBundle((Event)getListAdapter().getItem(position));
        dataToPass.putBoolean("fromSearch", true);
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        eventDetailFragment.setArguments(dataToPass);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, eventDetailFragment, "eventDetail")
                .commit();
        super.onListItemClick(l, v, position, id);
    }

    private void saveSharedPrefs(String stringToSave) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_LAST_SEARCH, stringToSave);
        editor.commit();
    }

    private void requestEvents(String city) {
        setListShownNoAnimation(false);
        MyHTTPRequest req = new MyHTTPRequest();
        String url = String.format(TICKET_MASTER_API_URL,city);
        req.execute(url);
    }

    private class MyHTTPRequest extends AsyncTask< String, Integer, ArrayList<Event>>
    {
        //Type3                      Type1
        public ArrayList<Event> doInBackground(String ... args)
        {
            try {
                String encoded = args[0];
                Log.d(TAG, "url : " + encoded);
                //create a URL object of what server to contact:
                URL url = new URL(encoded);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string
                Log.d(TAG, result);

                // convert string to JSON: Look at slide 27:
                JSONObject jsonObject = new JSONObject(result);
                JSONArray eventsJsonArray = jsonObject.getJSONObject("_embedded").getJSONArray("events");
                ArrayList<Event> events = new ArrayList<>();
                if(eventsJsonArray == null) {
                    return events;
                }
                for( int i = 0; i < eventsJsonArray.length(); i++) {
                    Event event = Event.parseEvent(eventsJsonArray.getJSONObject(i));
                    if(event != null) {
                        events.add(event);
                    }
                }
                return events;

            }
            catch (Exception e)
            {
                Log.e(TAG, e.getLocalizedMessage());
            }

            return new ArrayList<Event>();
        }

        //Type 2
        public void onProgressUpdate(Integer ... args)
        {

        }
        //Type3
        public void onPostExecute(ArrayList<Event> events)
        {
            ListAdapter listAdapter = new ArrayAdapter<Event>(getActivity(),
                android.R.layout.simple_list_item_1, events);

            setListAdapter(listAdapter);
            setListShownNoAnimation(true);
            Log.i(TAG, "" + events.size());
        }
    }
}
