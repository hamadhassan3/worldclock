package com.example.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1.Model.City;

import java.util.ArrayList;
import java.util.Date;

public class SelectedCitiesAdapter extends RecyclerView.Adapter<SelectedCitiesAdapter.ViewHolder>{

    private Context context;
    private ArrayList<City> cities;
    SelectedCitiesActivity activity;


    public SelectedCitiesAdapter(SelectedCitiesActivity activity, Context context, ArrayList<City> cities){
        this.context = context;
        this.cities = cities;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_selected_citites, parent, false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int resource = cities.get(position).getFlagFileAddress();
        if(resource == 123) {

        }
        else {
            holder.flagImage.setBackgroundResource(resource);
        }













        holder.textName.setText(cities.get(position).getName());
        holder.textDiff.setText(cities.get(position).getCurrentTime());
        holder.textTime.setText("");
        holder.textTM.setText("");
        holder.textDate.setText("");
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        TextView textName;
        TextView textDiff;
        TextView textTime;
        TextView textTM;
        TextView textDate;
        ImageView flagImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            textName = itemView.findViewById(R.id.cityName);
            textDiff = itemView.findViewById(R.id.cityDiff);
            textTime = itemView.findViewById(R.id.cityTime);
            textTM = itemView.findViewById(R.id.cityTM);
            textDate = itemView.findViewById(R.id.cityDate);

            this.flagImage = itemView.findViewById(R.id.flag);

            flagImage.setOnLongClickListener(this);



        }

        @Override
        public boolean onLongClick(View v) {




            cities.get(getAdapterPosition()).deleteSelectedCity();





            activity.refresh();


            return true;
        }
    }
}
