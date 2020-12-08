package ca.uottawa.finalproject.covid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database opener class which will handle table creation and version changes
 */
public class MyCovid19CountriesDBOpener extends SQLiteOpenHelper {


    private final static String DB_NAME = "GeoLocationCitiesDB";

    private final static int VERSION_NUM = 1;
    public static final String TABLE_COVID_19_CASES = "COVID_19_CASES";
    public static final String COL_ID = "_id";
    public static final String COL_COUNTRY = "COUNTRY";
    public static final String COL_COUNTRYCODE = "COUNTRYCODE";
    public static final String COL_PROVINCE = "PROVINCE";
    public static final String COL_CITY = "CITY";
    public static final String COL_CITYCODE = "CITYCODE";
    public static final String COL_LAT = "LAT";
    public static final String COL_LON = "LON";
    public static final String COL_CASES = "CASES";
    public static final String COL_STATUS = "STATUS";
    public static final String COL_DATE = "DATE";


    public MyCovid19CountriesDBOpener(Context ctx) {
        super(ctx, DB_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "
                        + TABLE_COVID_19_CASES + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_COUNTRY + " text,"
                        + COL_COUNTRYCODE + " text,"
                        + COL_PROVINCE + " text,"
                        + COL_CITY + " text,"
                        + COL_CITYCODE + " text,"
                        + COL_LAT + " text,"
                        + COL_LON + " text,"
                        + COL_CASES + " INTEGER,"
                        + COL_STATUS + " text,"
                        + COL_DATE + " text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COVID_19_CASES);
        onCreate(db);
    }
}
