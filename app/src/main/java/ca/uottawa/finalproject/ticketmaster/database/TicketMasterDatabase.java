package ca.uottawa.finalproject.ticketmaster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ca.uottawa.finalproject.ticketmaster.models.Event;

public class TicketMasterDatabase {

    SQLiteDatabase db;

    public TicketMasterDatabase(Context context) {
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(context);
        db = databaseOpenHelper.getWritableDatabase();
    }

    public ArrayList<Event> loadEvents() {
        String [] columns = {
                DatabaseOpenHelper.COL_ID,
                DatabaseOpenHelper.COL_NAME,
                DatabaseOpenHelper.COL_PRICE_RANGE,
                DatabaseOpenHelper.COL_START_DATE,
                DatabaseOpenHelper.COL_URL,
                DatabaseOpenHelper.COL_IMAGE_URL};

        //query all the results from the database:
        Cursor results = db.query(false, DatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int nameColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_NAME);
        int priceRangeColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_PRICE_RANGE);
        int startDateColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_START_DATE);
        int urlColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_URL);
        int imageUrlColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_IMAGE_URL);
        int idColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        ArrayList<Event> eventsList = new ArrayList<>();
        while(results.moveToNext())
        {
            String name = results.getString(nameColIndex);
            String priceRange = results.getString(priceRangeColIndex);
            String startDate = results.getString(startDateColIndex);
            String url = results.getString(urlColIndex);
            String imageUrl = results.getString(imageUrlColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            eventsList.add(new Event(id,name,priceRange,startDate,url,imageUrl));
        }
        return eventsList;
    }

    public Event save(Event event) {
        //add to the database and get the new ID
        ContentValues newRowValues = new ContentValues();

        newRowValues.put(DatabaseOpenHelper.COL_NAME, event.getName());
        newRowValues.put(DatabaseOpenHelper.COL_PRICE_RANGE, event.getPriceRange());
        newRowValues.put(DatabaseOpenHelper.COL_START_DATE, event.getStartingDate());
        newRowValues.put(DatabaseOpenHelper.COL_URL, event.getUrl());
        newRowValues.put(DatabaseOpenHelper.COL_IMAGE_URL, event.getImageUrl());

        long newId = db.insert(DatabaseOpenHelper.TABLE_NAME, null, newRowValues);

        event.setId(newId);
        return event;
    }

    public void delete(Event event) {
        db.delete(DatabaseOpenHelper.TABLE_NAME, DatabaseOpenHelper.COL_ID + "= ?", new String[] {Long.toString(event.getId())});
    }
}
