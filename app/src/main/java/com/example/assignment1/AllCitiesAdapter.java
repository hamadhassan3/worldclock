package com.example.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1.DataStorage.CityDao;
import com.example.assignment1.DataStorage.SelectedCityDao;
import com.example.assignment1.Model.City;

import java.util.ArrayList;
import java.util.Date;

public class AllCitiesAdapter extends RecyclerView.Adapter<AllCitiesAdapter.ACViewHolder>{

    private ArrayList<City> cities;
    private Context ct;



    void setCities(ArrayList<City>  cities){
        this.cities = cities;
    }


    public AllCitiesAdapter(Context ct, ArrayList<City> cities){

        this.cities = cities;
        this.ct = ct;


    }


    @NonNull
    @Override
    public ACViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(ct);

        View view = inflater.inflate(R.layout.item_all_cities, parent, false);




        return new AllCitiesAdapter.ACViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ACViewHolder holder, int position) {

        holder.cityName.setText(this.cities.get(position).getName());

        ArrayList<City> selectedCities = City.loadSelectedCities(new CityDao(this.ct), new SelectedCityDao(this.ct));

        boolean isSelected = false;

        for(City city: selectedCities){
            if(city.getName().equals(cities.get(position).getName())){
                isSelected = true;
            }
        }

        holder.checkBox.setChecked(isSelected);



        holder.cityTime.setText(cities.get(position).getCurrentTime());

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ACViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CheckBox checkBox;
        TextView cityName;
        TextView cityTime;

        public ACViewHolder(@NonNull View itemView) {
            super(itemView);

            this.checkBox = itemView.findViewById(R.id.aCityCheck);
            this.cityName = itemView.findViewById(R.id.acityName);
            this.cityTime = itemView.findViewById(R.id.aCityTime);

            this.checkBox.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {


            CheckBox checkBox = (CheckBox) v;


            if(checkBox.isChecked()) {


                cities.get(getAdapterPosition()).saveSelectedCity();
            }
            else{
                cities.get(getAdapterPosition()).deleteSelectedCity();
            }



        }
    }
}
