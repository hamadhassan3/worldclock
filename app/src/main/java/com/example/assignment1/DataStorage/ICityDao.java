package com.example.assignment1.DataStorage;

import java.util.ArrayList;
import java.util.Hashtable;

public interface ICityDao {

    void save(Hashtable<String, String> attributes);
    void save(ArrayList<Hashtable> objects);
    ArrayList<Hashtable<String, String>> load();
    Hashtable<String, String> load(String id);


}
