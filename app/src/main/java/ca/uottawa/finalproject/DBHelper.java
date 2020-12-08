package ca.uottawa.finalproject;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class for handling database creation
 *
 * this class checks for the database and if it doesn't exist, creates the table
 */
public class DBHelper extends SQLiteOpenHelper {
    /**
     * the name of table saved in the device
     */
    public static final String TABLE_NAME = "ALBUMS";

    /**
     * table columns
     */
    public static final String ALBUM_ID = "album_id";
    public static final String ALBUM_NAME = "album_name";
    public static final String ARTIST_NAME = "artist_name";

    /**
     * name of database
     */
    static final String DB_NAME = "SAVED_ALBUMS.DB";

    /**
     * version of database
     */
    static final int DB_VERSION = 1;

    /**
     * query for generating albums table
     */
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ALBUM_ID
            + " INTEGER PRIMARY KEY, " + ALBUM_NAME + " TEXT NOT NULL, " + ARTIST_NAME + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

