package com.example.newsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DividerItemDecoration;
import com.example.newsapp.adapter.NewContext_Adapter;
import com.example.newsapp.testmodel.News;

import java.util.ArrayList;

public class news_content_Fragment extends Fragment {
    ArrayList<News> news;
    RecyclerView recyclerView;
    @Override
    //test
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_content_, container, false);

        // Tìm kiếm RecyclerView trong layout
        recyclerView = view.findViewById(R.id.main_context_view);

        news = new ArrayList<News>();
        // Load dữ liệu
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",1));
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",2));
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",3));
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",4));
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",5));
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",1));
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",2));
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",3));
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",4));
        news.add(new News(R.drawable.google,"this is title","hello world","by thien",5));
        NewContext_Adapter adapter = new NewContext_Adapter(news);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        // Return view
        return view;
    }

}