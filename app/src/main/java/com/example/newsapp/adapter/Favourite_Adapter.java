package com.example.newsapp.adapter;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.Show_Context_Activity;
import com.example.newsapp.testmodel.News;
import com.example.newsapp.testmodel.UserFavourite;
import com.google.firebase.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Favourite_Adapter extends RecyclerView.Adapter<Favourite_Adapter.ViewHolder> {
    private ArrayList<News> arr_Fav;
    FirebaseDatabase db;
    public Favourite_Adapter(ArrayList<News> arr_Fav) {
        this.arr_Fav = arr_Fav;
    }


    @NonNull
    @Override
    public Favourite_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Favourite_Adapter.ViewHolder holder, int position) {
        News Fav=arr_Fav.get(position);
        holder.title.setText(Fav.getTitle());
        holder.view.setText(Fav.getView() + " view");
        holder.time.setText(Fav.getTime());
        Picasso.get().load(Fav.getImage()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Show_Context_Activity.class);
                i.putExtra("data", Fav);

                v.getContext().startActivity(i);
                Log.d(TAG, "onClick: " + Fav.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr_Fav.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,  view,time;
        public ViewHolder(@NonNull View itemFav){
            super(itemFav);
            image = itemView.findViewById(R.id.item_news_image);
            title = itemView.findViewById(R.id.item_main_title);
            view = itemView.findViewById(R.id.item_main_poster);
            time = itemView.findViewById(R.id.item_main_time);
        }
    }
}
