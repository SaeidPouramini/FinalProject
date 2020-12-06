package ca.uottawa.finalproject.ticketmaster.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import ca.uottawa.finalproject.R;
import ca.uottawa.finalproject.ticketmaster.database.TicketMasterDatabase;
import ca.uottawa.finalproject.ticketmaster.models.Event;

/**
 * EventDetailFragment displays the events details with the option to delete or save depending on
 * the state of the event
 */
public class EventDetailFragment extends Fragment implements OnBackClickListener{


    private Event event;
    private Button deleteBtn;
    private Button saveBtn;
    private TicketMasterDatabase db;
    private Boolean fromSearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new TicketMasterDatabase(getActivity());
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.event_details);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle dataFrom = getArguments();
        fromSearch = dataFrom.getBoolean("fromSearch");
        Event event = Event.parseEventFromBundle(dataFrom);
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.ticket_master_event_details_fragment, container, false);

        deleteBtn = result.findViewById(R.id.delete);
        saveBtn = result.findViewById(R.id.save);
        if (fromSearch) {
            deleteBtn.setVisibility(View.INVISIBLE);
            saveBtn.setOnClickListener(clk -> {
                db.save(event);
                Snackbar.make(getActivity().findViewById(R.id.fragmentLocation), "Saved", Snackbar.LENGTH_SHORT).show();
            });
        } else {
            saveBtn.setVisibility(View.INVISIBLE);
            deleteBtn.setOnClickListener(clk -> {
                db.delete(event);
                Snackbar.make(getActivity().findViewById(R.id.fragmentLocation), "Deleted", Snackbar.LENGTH_SHORT).show();
                SavedEventsFragment savedEventsFragment = new SavedEventsFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, savedEventsFragment)
                        .commit();
            });
        }

        TextView nameTextView = result.findViewById(R.id.name);
        nameTextView.setText(event.getName());

        TextView priceRangeTextView = result.findViewById(R.id.priceRange);
        priceRangeTextView.setText(event.getPriceRange());

        TextView startDateTextView = result.findViewById(R.id.startDate);
        startDateTextView.setText(event.getStartingDate());

        TextView urlTextView = result.findViewById(R.id.url);
        urlTextView.setText(event.getUrl());

        ImageView imageView = result.findViewById(R.id.image);
        Picasso.get().load(event.getImageUrl()).into(imageView);
        return result;
    }

    @Override
    public void onBackClick() {
        if (fromSearch) {
            SearchEventsFragment searchEventsFragment = new SearchEventsFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, searchEventsFragment)
                    .commit();
        } else {
            SavedEventsFragment savedEventsFragment = new SavedEventsFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, savedEventsFragment)
                    .commit();
        }

    }
}
