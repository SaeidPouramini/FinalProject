package ca.uottawa.finalproject.ticketmaster;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ca.uottawa.finalproject.R;
import ca.uottawa.finalproject.ticketmaster.fragments.OnBackClickListener;
import ca.uottawa.finalproject.ticketmaster.fragments.SearchEventsFragment;

/**
 * Main Activity for Ticket Master Event Search
 * @Sundus
 */
public class TicketMasterEventSearchActivity extends AppCompatActivity {
    private static final String TAG = TicketMasterEventSearchActivity.class.getSimpleName();
    SearchEventsFragment searchEventsFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_master_event_search_activity_main);

        searchEventsFragment = new SearchEventsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, searchEventsFragment)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("eventDetail");
        if(fragment instanceof OnBackClickListener) {
            ((OnBackClickListener) fragment).onBackClick();
        }
        Log.d(TAG, "onSUpport");
        return super.onSupportNavigateUp();
    }

}
