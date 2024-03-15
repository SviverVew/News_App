package com.example.newsapp.adapter;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.LoginActivity;
import com.example.newsapp.R;
import com.example.newsapp.Show_Context_Activity;
import com.example.newsapp.testmodel.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewContext_Adapter extends RecyclerView.Adapter<NewContext_Adapter.ViewHolder> implements Filterable {

    private ArrayList<News> arr_News;
    Context context;
    private List<News> arr_New;
    private List<News> getArr_Newsold;

    public NewContext_Adapter(ArrayList<News> arr_News, Context context) {
        this.arr_News = arr_News;
        this.context = context;
    }


    public NewContext_Adapter(ArrayList<News> arr_News) {
        this.arr_News = arr_News;
    }
    public NewContext_Adapter(List<News> arr_New) {
        this.arr_New = arr_New;
        this.getArr_Newsold= arr_New;
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
        Picasso.get().load(news.getImage()).into(holder.image);
//        holder.context.setText(news.getContext());
//        holder.time.setText(String.valueOf(news.getTime()));
//        holder.category.setText(news.getCategory());
//        holder.view.setText(news.getView());
        //add click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty())
                {
                    arr_New = getArr_Newsold;
                }
                else {
                    List<News> arrayList = new ArrayList<>();
                    for (News news : getArr_Newsold){
                        if(news.getTitle().toLowerCase().contains(strSearch.toLowerCase()))
                        {
                            arrayList.add(news);
                        }
                    }
                    arr_New = arrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arr_News;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arr_New = (List<News>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title, user, time, view, category, context;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.item_news_image);
            title = itemView.findViewById(R.id.item_main_title);
            user = itemView.findViewById(R.id.item_main_poster);
//          time = itemView.findViewById(R.id.item_main_time);
            view = itemView.findViewById(R.id.show_news_view);
//          category = itemView.findViewById(R.id.show_news_category);
        }

    }

}