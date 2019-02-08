package com.vinsoft.lemi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    ArrayList<Cities> cities;
    Activity activity;

    public CityAdapter(ArrayList<Cities> cities, Activity activity) {
        this.activity = activity;
        this.cities = cities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citylist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(cities.get(position).getBanner() != null) {
            holder.initial.setVisibility(View.INVISIBLE);
            Glide.with(activity)
                    .load(cities.get(position).getBanner())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.photo);
        }else{
            holder.initial.setVisibility(View.VISIBLE);
            String init = cities.get(position).getName();

            holder.initial.setText(init.substring(0,3));

            Glide.with(activity)
                    .load(createAvatar())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.photo);
        }

        holder.title.setText(cities.get(position).getName());
        holder.subtitle.setText(cities.get(position).getSubtitle());

    }
    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, subtitle, initial;
        public ImageView photo;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvCityName);
            subtitle = itemView.findViewById(R.id.tvSubtitle);
            initial = itemView.findViewById(R.id.tvInitial);

            photo = itemView.findViewById(R.id.ivBanner);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra("cityname", cities.get(getAdapterPosition()).getName());
                activity.setResult(RESULT_OK, intent);
                activity.finish();
            });

        }

    }

    private Drawable createAvatar(){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(randomColor());

        return  drawable;
    }

    private int randomColor(){
        Random random = new Random();
        int color = Color.argb(255, random .nextInt(256), random .nextInt(256), random .nextInt(256));

        return color;
    }
}
