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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class NewContext_Adapter extends RecyclerView.Adapter<NewContext_Adapter.ViewHolder> implements Filterable {
    private ArrayList<News> arr_News;
    private ArrayList<News> getArr_Newsold;
    FirebaseFirestore db;
    public NewContext_Adapter(ArrayList<News> arr_News) {
        this.arr_News = arr_News;
        this.getArr_Newsold = new ArrayList<>(arr_News);
    }

    @NonNull
    @Override
    public NewContext_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewContext_Adapter.ViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();
        News news = arr_News.get(position);
        holder.title.setText(news.getTitle());
        holder.view.setText(news.getView() + " view");
        holder.time.setText(news.getTime());
        Picasso.get().load(news.getImage()).into(holder.image);
        //add click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Show_Context_Activity.class);
                i.putExtra("data", news);

                // Tăng số lượt view ở đây
                int currentViews = Integer.parseInt(news.getView());
                news.setView(String.valueOf(currentViews + 1));
                updateViews(news.getId(), currentViews+1);

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
        TextView title, user, time, view;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.item_news_image);
            title = itemView.findViewById(R.id.item_main_title);
            view = itemView.findViewById(R.id.item_main_poster);
            time = itemView.findViewById(R.id.item_main_time);
//            view = itemView.findViewById(R.id.show_news_view);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    arr_News.clear();
                    arr_News.addAll(getArr_Newsold);
                } else {
                    List<News> arrayList = new ArrayList<>();
                    for (News news : getArr_Newsold) {
                        if (news.getTitle().toLowerCase().contains(strSearch.toLowerCase())) {
                            arrayList.add(news);
                        }
                    }
                    arr_News.clear();
                    arr_News.addAll(arrayList);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arr_News;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arr_News = (ArrayList<News>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    void updateViews(String documentId, int newViewCount) {

        db.collection("news").document(documentId)
                .update("news_View", newViewCount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Tăng view thành công!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Tăng view thất bại", e);
                    }
                });
    }
}