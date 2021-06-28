package com.example.assignment1.DataStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SelectedCityOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SelCities";


    public final String CITY_TABLE_NAME = "SelectedCities";

    public SelectedCityOpenHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String query = "Create TABLE " + CITY_TABLE_NAME +
                "(Name TEXT PRIMARY KEY, FlagFileAddress TEXT, TIMESTAMP TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS " + CITY_TABLE_NAME;
        db.execSQL(query);
        onUpgrade(db, oldVersion, newVersion);

    }
}
