package com.example.newsapp.adapter;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.LoginActivity;
import com.example.newsapp.R;
import com.example.newsapp.Show_Context_Activity;
import com.example.newsapp.testmodel.News;

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
        holder.image.setImageResource(news.getImage());
        holder.title.setText(news.getTitle());
        holder.user.setText(news.getUser());
        holder.time.setText(String.valueOf(news.getTime()));
        holder.category.setText(news.getCategory());
        holder.view.setText(news.getView());
//       String res = news.getUser().toString();
        //add click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(),"mày đang chọn bài báo số " + res,Toast.LENGTH_LONG).show();
                  Intent i = new Intent(v.getContext(), Show_Context_Activity.class);
                  i.putExtra("data", news);
                  v.getContext().startActivity(i);
                  Log.d(TAG, "onClick: " + news.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr_News.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title, user, time, view, category;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.item_news_image);
            title = itemView.findViewById(R.id.item_main_title);
            user = itemView.findViewById(R.id.item_main_poster);
            time = itemView.findViewById(R.id.item_main_time);
            view = itemView.findViewById(R.id.show_news_view);
            category = itemView.findViewById(R.id.show_news_category);
        }
    }
}
