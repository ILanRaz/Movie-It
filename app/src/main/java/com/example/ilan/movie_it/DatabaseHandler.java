package com.example.ilan.movie_it;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_MOVIES = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_name = "name";
    private static final String KEY_imdbRatio = "imdbratio";
    private static final String KEY_releaseDate = "releaseDate";
    private static final String KEY_budget = "budget";
    private static final String KEY_about = "about";
    private static final String KEY_imagepath = "imagepath";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_name + " TEXT,"
                + KEY_imdbRatio + " TEXT," + KEY_releaseDate + " TEXT," + KEY_budget + " TEXT,"
                + KEY_about + " TEXT," + KEY_imagepath + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);

        // Create tables again
        onCreate(db);
    }

    // Adding new moview
    public void addMovie(Movie moview) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_name, moview.getMovieName());
        values.put(KEY_imdbRatio, moview.getImdbRatio());
        values.put(KEY_releaseDate, moview.getReleaseDate());
        values.put(KEY_budget, moview.getBudget());
        values.put(KEY_about, moview.getAbout());
        values.put(KEY_imagepath, moview.getImagepath());

        // Inserting Row
        db.insert(TABLE_MOVIES, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public List<Movie> getAllMovies() {
        List<Movie> contactList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//                Movie contact = new Movie(R.drawable.bladerunner, "Movie Name: Blade Runner 2049", "Budget:$150,000,000", "Release Date:03/10/17", "IMDb:8.4", "Overview \nThirty years after the events of the first film, a new blade runner, LAPD Officer K, unearths a long-buried secret that has the potential to plunge what's left of society into chaos. K's discovery leads him on a quest to find Rick Deckard, a former LAPD blade runner who has been missing for 30 years.");
                Movie contact = new Movie(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Getting contacts Count
    public int getMoviesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    // Updating single contact
    public int updateMovie(Movie moview) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("errorhere", "   **   " + moview.getID());
        ContentValues values = new ContentValues();
        values.put(KEY_name, moview.getMovieName());
        values.put(KEY_imdbRatio, moview.getImdbRatio());
        values.put(KEY_releaseDate, moview.getReleaseDate());
        values.put(KEY_budget, moview.getBudget());
        values.put(KEY_about, moview.getAbout());
        values.put(KEY_imagepath, moview.getImagepath());

        // updating row
        return db.update(TABLE_MOVIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(moview.getID())});
    }

    // Deleting single contact
    public void deleteMovie(Movie contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getResourceID())});
        db.close();
    }

    // Deleting single contact
    public void deleteAllMovie() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, null, null);
        db.close();
    }
}
