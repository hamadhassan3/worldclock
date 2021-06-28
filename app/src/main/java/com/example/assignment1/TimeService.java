package com.example.assignment1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.assignment1.DataStorage.CityDao;
import com.example.assignment1.DataStorage.SelectedCityDao;
import com.example.assignment1.Model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TimeService extends Service {
    private CityDao cityDao;
    private SelectedCityDao selectedCityDao;
    static boolean running;

    @Override
    public void onCreate() {
        super.onCreate();
        cityDao = new CityDao(this);
        selectedCityDao = new SelectedCityDao(this);
        running = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String line;
                try{
                    URL url = new URL("https://api.timezonedb.com/v2.1/list-time-zone?key=COEAXC0345Q0&format=json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(100000);
                    connection.setConnectTimeout(200000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    connection.connect();

                    StringBuilder content = new StringBuilder();
                    BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );

                    while( (line = reader.readLine()) != null ){
                        content.append(line);
                    }

                    line = content.toString();
                    parse(line);
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
        stopSelf();
        return Service.START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    private void parse(String line)
    {
        try {
            JSONObject jsonObject = new JSONObject(line);
            JSONArray jsonArray = (JSONArray) jsonObject.get("zones");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject timeZone = jsonArray.getJSONObject(i);
                String zoneName = timeZone.getString("zoneName");
                String countryName = timeZone.getString("countryName");
                long timestamp = timeZone.getLong("timestamp");




                City city = new City(cityDao, selectedCityDao, zoneName, 123, timestamp);
                city.save();

            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}
