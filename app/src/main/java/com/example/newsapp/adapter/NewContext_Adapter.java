package com.example.newsapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.testmodel.News;

import java.util.ArrayList;
//
//public class NewContext_Adapter extends RecyclerView.Adapter<NewContext_Adapter.ViewHolder> {
//    ArrayList<News> arr_News;
//    Activity Context;
//
//    public NewContext_Adapter(ArrayList<News> arr_News) {
//        this.arr_News = arr_News;
//    }
//
//    public NewContext_Adapter(ArrayList<News> arr_News, Activity context) {
//        this.arr_News = arr_News;
//        Context = context;
//    }
//
//    @NonNull
//    @Override
//    public NewContext_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(Context);
//        View news = inflater.inflate(R.layout.item_news,parent,false);
//        return new ViewHolder(news);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NewContext_Adapter.ViewHolder holder, int position) {
//        News news = arr_News.get(position);
//        holder.image.setImageResource(news.getImage());
//        holder.title.setText(news.getTitle()+"");
//        holder.user.setText(news.getUser());
//        holder.time.setText(news.getTime()+"");
//    }
//
//    @Override
//    public int getItemCount() {
//        return arr_News.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView image;
//        TextView title,user,time;
//        public ViewHolder(@NonNull View itemview){
//            super(itemview);
//            image =itemview.findViewById(R.id.item_news_image);
//            title = itemview.findViewById(R.id.item_main_title);
//            user = itemview.findViewById(R.id.item_main_poster);
//            time = itemview.findViewById(R.id.item_main_time);
//        }
//    }
//}

public class NewContext_Adapter extends RecyclerView.Adapter<NewContext_Adapter.ViewHolder> {
    private ArrayList<News> arr_News;

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
        holder.image.setImageResource(news.getImage());
        holder.title.setText(news.getTitle());
        holder.user.setText(news.getUser());
        holder.time.setText(news.getTime());
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
