package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.assignment1.DataStorage.CityDao;
import com.example.assignment1.DataStorage.SelectedCityDao;
import com.example.assignment1.Model.City;


import java.sql.Time;
import java.util.ArrayList;

import static com.example.assignment1.DataStorage.CityOpenHelper.DATABASE_NAME;

public class AllCititesActivity extends AppCompatActivity {


    public static ArrayList<City> cities = new ArrayList<>();

    private ImageButton backButton;
    private EditText searchText;
    private ImageButton searchButton;


    private final int AMERICA = R.drawable.am;
    private final int GERMANY = R.drawable.ber;
    private final int JAPAN = R.drawable.jp;
    private final int RUSSIA = R.drawable.mos;
    private final int AUSTRALIA = R.drawable.syd;
    private final int UK = R.drawable.uk;
    private final int UNKNOWN = R.drawable.ic_baseline_hourglass_empty_24;


    AllCitiesAdapter sca;

    private CityDao  cityDao = new CityDao(this);

    @Override
    public void onResume(){

        super.onResume();

        RecyclerView rec = findViewById(R.id.allCityList);

        cities = City.load(new CityDao(this), new SelectedCityDao(this));

        sca = new AllCitiesAdapter(this, cities);

        rec.setAdapter(sca);



        rec.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cities);





        backButton = findViewById(R.id.backButton);
        searchText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);





        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<City> citiesTemp = new ArrayList<>();


                for(City c: cities){
                    if(c.getName().contains(searchText.getText())){
                        citiesTemp.add(c);
                    }
                }


                sca.setCities(citiesTemp);
                sca.notifyDataSetChanged();

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}