package com.example.assignment1.Model;


import com.example.assignment1.DataStorage.CityDao;
import com.example.assignment1.DataStorage.SelectedCityDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;

public class City implements Serializable {

    public static Date date = new Date();


    private CityDao cityDao = null;
    private SelectedCityDao sCityDao = null;


    //Attributes of a city
    private String name;
    private int flagFileAddress;
    private boolean selected;
    private long timestamp;


    public City(CityDao cityDao, SelectedCityDao sCityDao){
        this.cityDao = cityDao;

        this.sCityDao = sCityDao;

    }

    //Parametrized constructor
    public City(CityDao cityDao, SelectedCityDao sCityDao, String name, int flagFileAddress, long timestamp) {
        this.name = name;
        this.flagFileAddress = flagFileAddress;

        this.selected = false;

        this.timestamp = timestamp;


        this.cityDao = cityDao;
    }


    public String getCurrentTime(){

        String time;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(this.name));
        time = calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        if (calendar.get(Calendar.AM_PM) == 0)
            time = time + " AM";
        else
            time = time + " PM";


        return time;


    }


    //*** Data storage functions ***

    public void save(){

        if(cityDao != null){

            Hashtable<String, String> data = new Hashtable<>();

            data.put("Name", this.name);
            data.put("flagfileaddress", Integer.toString(this.flagFileAddress));
            data.put("timestamp", Long.toString(this.timestamp));

            cityDao.save(data);

        }

    }


    public void saveSelectedCity(){

        if(sCityDao != null){

            Hashtable<String, String> data = new Hashtable<>();

            data.put("Name", this.name);
            data.put("flagfileaddress", Integer.toString(this.flagFileAddress));
            data.put("timestamp", Long.toString(this.timestamp));

            sCityDao.save(data);

        }

    }

    public void delete(){
        cityDao.deleteCity(this.name);
    }


    public void deleteSelectedCity(){
        sCityDao.deleteCity(this.name);
    }



    public void load(Hashtable<String, String> data){

        this.name = data.get("name");
        this.flagFileAddress = Integer.valueOf(data.get("flagfileaddress"));
        this.timestamp = Long.valueOf(data.get("timestamp"));
        this.selected = true;



    }


    public static ArrayList<City> load(CityDao dd, SelectedCityDao sCityDao){

        ArrayList<City> cities = new ArrayList<City>();

        if(dd != null){

            ArrayList<Hashtable<String, String>> objects = dd.load();

            for(Hashtable<String, String> obj: objects){

                City city = new City(dd, sCityDao);

                city.load(obj);
                cities.add(city);


            }


        }

        return cities;

    }


    public static ArrayList<City> loadSelectedCities(CityDao dd, SelectedCityDao sCityDao){

        ArrayList<City> cities = new ArrayList<City>();

        if(dd != null){

            ArrayList<Hashtable<String, String>> objects = sCityDao.load();

            for(Hashtable<String, String> obj: objects){

                City city = new City(dd, sCityDao);

                city.load(obj);
                cities.add(city);


            }


        }

        return cities;

    }




    // *** Getters and Setters ***


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlagFileAddress() {
        return flagFileAddress;
    }

    public void setFlagFileAddress(int flagFileAddress) {
        this.flagFileAddress = flagFileAddress;
    }




}
