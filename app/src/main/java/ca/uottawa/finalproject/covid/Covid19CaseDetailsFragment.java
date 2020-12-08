package ca.uottawa.finalproject.covid;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ca.uottawa.finalproject.R;

/**
 * Covid19CaseDetailsFragment - This fragment will display the detailed record
 */
public class Covid19CaseDetailsFragment extends Fragment {

    /**
     * database opener class object
     */
    private MyCovid19CountriesDBOpener dbOpener;

    /**
     * storing id to handle save/remove from database
     */
    private long id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_covid_19_case_details, container, false);

        TextView textviewCountry = ((TextView) view.findViewById(R.id.textview_country));
        textviewCountry.setText(getString(R.string.c19_country) + getArguments().getString("COUNTRY"));
        TextView textviewCountryCode = ((TextView) view.findViewById(R.id.textview_countryCode));
        textviewCountryCode.setText(getString(R.string.c19_country_code) + getArguments().getString("COUNTRYCODE"));
        TextView textviewProvince = ((TextView) view.findViewById(R.id.textview_province));
        textviewProvince.setText(getString(R.string.c19_province) + getArguments().getString("PROVINCE"));
        TextView textviewCity = ((TextView) view.findViewById(R.id.textview_city));
        textviewCity.setText(getString(R.string.c19_city) + getArguments().getString("CITY"));
        TextView textviewCityCode = ((TextView) view.findViewById(R.id.textview_cityCode));
        textviewCityCode.setText(getString(R.string.c19_city_code) + getArguments().getString("CITYCODE"));
        TextView textviewLat = ((TextView) view.findViewById(R.id.textview_lat));
        textviewLat.setText(getString(R.string.c19_latitude) + getArguments().getString("LAT"));
        TextView textviewLon = ((TextView) view.findViewById(R.id.textview_lon));
        textviewLon.setText(getString(R.string.c19_longitude) + getArguments().getString("LON"));
        TextView textviewCases = ((TextView) view.findViewById(R.id.textview_cases));
        textviewCases.setText(getString(R.string.c19_cases) + getArguments().getString("CASES"));
        TextView textviewStatus = ((TextView) view.findViewById(R.id.textview_status));
        textviewStatus.setText(getString(R.string.c19_status) + getArguments().getString("STATUS"));
        TextView textviewDate = ((TextView) view.findViewById(R.id.textview_date));
        textviewDate.setText(getString(R.string.c19_date) + getArguments().getString("DATE"));

        dbOpener = new MyCovid19CountriesDBOpener(getActivity());

        Button button_save_or_remove_database = view.findViewById(R.id.button_save_or_remove_database);

        id = getArguments().getLong("ID");
        if (id == 0) {
            button_save_or_remove_database.setText("Save to database");
        } else {
            button_save_or_remove_database.setText("Remove from database");
        }

        button_save_or_remove_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == 0) {

                    ContentValues newRowValues = new ContentValues();

                    newRowValues.put(MyCovid19CountriesDBOpener.COL_COUNTRY, getArguments().getString("COUNTRY"));
                    newRowValues.put(MyCovid19CountriesDBOpener.COL_COUNTRYCODE, getArguments().getString("COUNTRYCODE"));
                    newRowValues.put(MyCovid19CountriesDBOpener.COL_PROVINCE, getArguments().getString("PROVINCE"));
                    newRowValues.put(MyCovid19CountriesDBOpener.COL_CITY, getArguments().getString("CITY"));
                    newRowValues.put(MyCovid19CountriesDBOpener.COL_CITYCODE, getArguments().getString("CITYCODE"));
                    newRowValues.put(MyCovid19CountriesDBOpener.COL_LAT, getArguments().getString("LAT"));
                    newRowValues.put(MyCovid19CountriesDBOpener.COL_LON, getArguments().getString("LON"));
                    newRowValues.put(MyCovid19CountriesDBOpener.COL_CASES, getArguments().getLong("CASES"));
                    newRowValues.put(MyCovid19CountriesDBOpener.COL_STATUS, getArguments().getString("STATUS"));
                    newRowValues.put(MyCovid19CountriesDBOpener.COL_DATE, getArguments().getString("DATE"));

                    SQLiteDatabase db = dbOpener.getWritableDatabase();
                    id = db.insert(MyCovid19CountriesDBOpener.TABLE_COVID_19_CASES, null, newRowValues);

                    button_save_or_remove_database.setText("Remove from database");

                } else {

                    SQLiteDatabase db = dbOpener.getWritableDatabase();
                    db.delete(MyCovid19CountriesDBOpener.TABLE_COVID_19_CASES, MyCovid19CountriesDBOpener.COL_ID + "=?", new String[]{Long.toString(id)});
                    id = 0;

                    button_save_or_remove_database.setText("Save to database");
                }
            }
        });


        return view;
    }

}