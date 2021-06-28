package com.example.assignment1.DataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CityDao implements ICityDao {

    private Context context;

    public CityDao(Context context){
        this.context = context;
    }


    public boolean deleteCity(String name)
    {

        CityOpenHelper cityOpenHelper = new CityOpenHelper(context);
        SQLiteDatabase db = cityOpenHelper.getWritableDatabase();


        return db.delete(CityOpenHelper.DATABASE_NAME, "Name" + "=\"" + name + "\"", null) > 0;
    }


    //Saves a specific object
    @Override
    public void save(Hashtable<String, String> attributes) {



        CityOpenHelper cityOpenHelper = new CityOpenHelper(context);



        SQLiteDatabase db = cityOpenHelper.getWritableDatabase();

        ContentValues vals = new ContentValues();

        Enumeration<String> keys = attributes.keys();

        while(keys.hasMoreElements()){

            String key = keys.nextElement();

            vals.put(key, attributes.get(key));


        }

        db.insert(cityOpenHelper.CITY_TABLE_NAME, null, vals);



    }


    //Saves all objects
    @Override
    public void save(ArrayList<Hashtable> objects) {

        for(Hashtable<String, String> obj: objects){
            save(obj);
        }
    }


    //Loads all objects
    @Override
    public ArrayList<Hashtable<String, String>> load() {

        CityOpenHelper cityOpenHelper = new CityOpenHelper(context);

        SQLiteDatabase db = cityOpenHelper.getReadableDatabase();

        String query = "SELECT * FROM " + cityOpenHelper.CITY_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Hashtable<String, String>> objects = new ArrayList<Hashtable<String, String>>();

        while(cursor.moveToNext()){
            Hashtable<String, String> obj = new Hashtable<>();
            String[] cols = cursor.getColumnNames();

            for(String col: cols){
                obj.put(col.toLowerCase(), cursor.getString(cursor.getColumnIndex(col)));
            }
            objects.add(obj);
        }

        cursor.close();

        return objects;
    }


    //Loads a specific object
    @Override
    public Hashtable<String, String> load(String name) {
        ArrayList<Hashtable<String, String>> objects = load();

        for(Hashtable<String, String> obj: objects){
            if(obj.get("name").equals(name)){
                return obj;
            }
        }

        return null;
    }
}
