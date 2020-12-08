package ca.uottawa.finalproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * class for handling database usages
 *
 * this class helps for the ease usage of inserting or deleting data from the database
 */
public class DBManager {
    private DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    /**
     * @return database manager
     * @throws SQLException
     */
    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * @param albumID
     * @param albumName
     * @param artistName
     *
     * Method for inserting an album's content to the albums table
     */
    public void insert(String albumID, String albumName, String artistName) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.ALBUM_ID, albumID);
        contentValue.put(DBHelper.ALBUM_NAME, albumName);
        contentValue.put(DBHelper.ARTIST_NAME, artistName);
        database.insert(DBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DBHelper.ALBUM_ID, DBHelper.ALBUM_NAME, DBHelper.ARTIST_NAME};
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    /**
     * @param albumID
     *
     * Method for deleting data from the albums table
     * this method is called when the user choose the delete button in the album's detail activity
     */
    public void delete(String albumID) {
        database.delete(DBHelper.TABLE_NAME, DBHelper.ALBUM_ID + "=" + albumID, null);
    }

}

