package com.example.newsapp.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
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
import com.example.newsapp.admin.Show_News_Info_Activity;
import com.example.newsapp.testmodel.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewContext_Adapter extends RecyclerView.Adapter<NewContext_Adapter.ViewHolder> {

    private ArrayList<News> arr_News;
    Context context;

    public NewContext_Adapter(ArrayList<News> arr_News, Context context) {
        this.arr_News = arr_News;
        this.context = context;
    }


    public NewContext_Adapter(ArrayList<News> arr_News) {
        this.arr_News = arr_News;
    }

    @NonNull
    @Override
    public NewContext_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewContext_Adapter.ViewHolder holder, int position) {
        News news = arr_News.get(position);
        holder.title.setText(news.getTitle());
        holder.user.setText(news.getUser());
        holder.time.setText(news.getTime());
        Picasso.get().load(news.getImage()).into(holder.image);
        //add click listener on selected item, intent will send a data of object news
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.getContext().getClass().getName().equals("com.example.newsapp.List_News_Activity")){
                    Intent i = new Intent(v.getContext(), Show_Context_Activity.class);
                    i.putExtra("data", news);
                    v.getContext().startActivity(i);
                    Log.d(TAG, "onClick: " + v.getContext().getClass().getName());
                }
                else{
                    Intent i = new Intent(v.getContext(), Show_News_Info_Activity.class);
                    i.putExtra("newsinfo", news);
                    v.getContext().startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr_News.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title, user, time;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.item_news_image);
            title = itemView.findViewById(R.id.item_main_title);
            user = itemView.findViewById(R.id.item_main_poster);
            time = itemView.findViewById(R.id.item_main_time);
        }
    }
}