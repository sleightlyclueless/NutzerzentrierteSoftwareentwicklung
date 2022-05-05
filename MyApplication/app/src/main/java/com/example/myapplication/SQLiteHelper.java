package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "database.db"; // The database file name
    public static final String FAVOURITE_TABLE_NAME = "favourites"; // The table name for Fav.
    public static final String REPORTS_TABLE_NAME = "reports";      // The table name for Rep.
    public static final String ATTRIBUTE_ID = "ID";                 // The Attribute Name for one Entry

    /**
     * Constructor
     * @param context
     */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * Creates the basic DB
     * @param sqLiteDatabase the SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATION_TABLE = "CREATE TABLE favourites ( ID INTEGER PRIMARY KEY )";
        sqLiteDatabase.execSQL(CREATION_TABLE);
        CREATION_TABLE = "CREATE TABLE reports ( ID INTEGER PRIMARY KEY )";
        sqLiteDatabase.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    /**
     * Saves a single favorite to the database
     * @param id the ID of the ladestation to save as fav
     */
    public void saveSingleFavoriteToDB(int id) // save single value
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ATTRIBUTE_ID, id); // Write id to value set
        database.insert(FAVOURITE_TABLE_NAME, null, values); // Write the valueset to DB
        database.close();
    }

    /**
     * Removes a single favorite from db
     * @param id the ladestation's ID
     */
    public void removeSingleFavoriteFromDB(int id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(SQLiteHelper.FAVOURITE_TABLE_NAME, "id = ?",new String[] {String.valueOf(id)});
        database.close();
    }

    /**
     * Returns the favorites as ArrayList
     * @return The favorites as ArrayList
     */
    public ArrayList<Integer> readFavouritesFromDB() {
        SQLiteDatabase database = this.getReadableDatabase(); // Get reference to db
        String query = "SELECT * FROM "+SQLiteHelper.FAVOURITE_TABLE_NAME;
        Cursor res = database.rawQuery(query,null);
        ArrayList<Integer> resultList = new ArrayList<Integer>();

        if(res.moveToFirst())
        {
            do // Loop over all results
            {
                resultList.add(Integer.parseInt(res.getString(0)));
            } while(res.moveToNext());
        }
        res.close();
        database.close();

        for(Integer i : resultList)
        {
            System.out.println(i);
        }
        return resultList;
    }

    /**
     * Saves a single reported ladestations id to db
     * @param id The ID of the ladestation
     */
    public void saveSingleFlagToDB(int id) // save single value
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ATTRIBUTE_ID, id); // Write id to value set
        database.insert(REPORTS_TABLE_NAME, null, values); // Write the valueset to DB
        database.close();
    }

    /**
     * Reads all reported stations from DB and returns as arrayList
     * @return the arrayList
     */
    public ArrayList<Integer> readFlagsFromDB() {
        SQLiteDatabase database = this.getReadableDatabase(); // Get reference to db
        String query = "SELECT * FROM "+SQLiteHelper.REPORTS_TABLE_NAME;
        Cursor res = database.rawQuery(query,null);
        ArrayList<Integer> resultList = new ArrayList<Integer>();

        if(res.moveToFirst())
        {
            do // Loop over all results
            {
                resultList.add(Integer.parseInt(res.getString(0)));
            }while(res.moveToNext());
        }

        res.close();
        database.close();
        for(Integer i : resultList)
        {
            System.out.println(i);
        }
        return resultList;
    }

    /**
     * Remove a single Report from DB
     * @param id The ladestations ID to remove the report from
     */
    public void removeSingleReportFromDB(int id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(SQLiteHelper.REPORTS_TABLE_NAME, "id = ?",new String[] {String.valueOf(id)});
        database.close();
    }

}