package ca.uottawa.finalproject.ticketmaster.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "TicketMasterDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "EVENTS";
    public final static String COL_NAME = "NAME";
    public static final String COL_PRICE_RANGE = "PRICE_RANGE";
    public static final String COL_START_DATE = "START_DATE";
    public static final String COL_URL = "URL";
    public static final String COL_IMAGE_URL = "IMAGE_URL";
    public final static String COL_ID = "_id";

    public DatabaseOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase  .execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " text,"
                + COL_PRICE_RANGE + " text,"
                + COL_START_DATE + " text,"
                + COL_URL + " text,"
                + COL_IMAGE_URL  + " text);");  // add or remove columns
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(sqLiteDatabase);
    }
}
