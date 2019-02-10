package com.vinsoft.lemi;

import android.app.Activity;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static android.app.Activity.RESULT_OK;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    ArrayList<Cities> cities;
    Activity activity;
    Drawable placeholder;

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

        String init = cities.get(position).getName();

        placeholder = placeholderDrawable(createAvatar(),new CropCircleTransformation(), 256);

        if(cities.get(position).getBanner() != null) {
            holder.initial.setVisibility(View.VISIBLE);
            holder.initial.setText(init.substring(0,3));
            Glide.with(activity)
                    .load(cities.get(position).getBanner())
                    .transition(withCrossFade())
                    .apply(new RequestOptions().placeholder(placeholder).circleCrop())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.initial.setVisibility(View.VISIBLE);
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.initial.setVisibility(View.INVISIBLE);
                            return false;
                        }
                    })
                    .into(holder.photo);
        }else{
            holder.initial.setVisibility(View.VISIBLE);

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

    private BitmapDrawable placeholderDrawable(Drawable drawable, Transformation<Bitmap> transform, int size) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, size, size);
        drawable.draw(canvas);
        Resource<Bitmap> original = BitmapResource.obtain(bitmap, Glide.get(activity).getBitmapPool());
        Resource<Bitmap> rounded = transform.transform(activity, original, size, size);
        if (!original.equals(rounded)) {
            original.recycle();
        }
        return new BitmapDrawable(activity.getResources(), rounded.get());
    }

    private int randomColor(){
        Random random = new Random();
        int color = Color.argb(255, random .nextInt(256), random .nextInt(256), random .nextInt(256));

        return color;
    }
}
