package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.assignment1.DataStorage.CityDao;
import com.example.assignment1.DataStorage.SelectedCityDao;
import com.example.assignment1.Model.City;

import java.util.ArrayList;

import static com.example.assignment1.DataStorage.CityOpenHelper.DATABASE_NAME;

public class SelectedCitiesActivity extends AppCompatActivity {

    private ArrayList<City> cities = new ArrayList<>();


    public void refresh(){
        cities = City.loadSelectedCities(new CityDao(this), new SelectedCityDao(this));

        Button addButton = findViewById(R.id.AddButton);

        RecyclerView rec = findViewById(R.id.SelectedCityList);

        SelectedCitiesAdapter sca = new SelectedCitiesAdapter(this, this, cities);

        rec.setAdapter(sca);

        rec.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onResume(){
        super.onResume();


        refresh();



        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000); // Sleep for 1 sec
                        runOnUiThread(() -> refresh());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_cities);

        // Deleting database before populating again
        this.getApplicationContext().deleteDatabase(DATABASE_NAME);
        Intent intent = new Intent(this, TimeService.class);
        startService(intent);


        cities = City.loadSelectedCities(new CityDao(this), new SelectedCityDao(this));
        cities = new ArrayList<>();

        Button addButton = findViewById(R.id.AddButton);

        RecyclerView rec = findViewById(R.id.SelectedCityList);

        SelectedCitiesAdapter sca = new SelectedCitiesAdapter(this,this, cities);

        rec.setAdapter(sca);

        rec.setLayoutManager(new LinearLayoutManager(this));


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedCitiesActivity.this, AllCititesActivity.class);
                startActivity(intent);

            }
        });

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu1, menu);




        return true;
    }*/




}