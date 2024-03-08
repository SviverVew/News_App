package com.example.newsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.adapter.NewContext_Adapter;
import com.example.newsapp.testmodel.News;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class news_content_trending_Fragment extends Fragment {
    ArrayList<News> news_byview;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_news_content_trending_,container,false);
        recyclerView = view.findViewById(R.id.main_context_trending_view);

        news_byview = new ArrayList<News>();
        //add test data
        news_byview.add(new News(R.drawable.google,"Tổng Bí thư gửi thư chúc mừng Chủ tịch Đảng Nhân dân Campuchia Hunsen","hello world","by thien",1));
        news_byview.add(new News(R.drawable.google,"this is title","hello world","by thien",4));
        news_byview.add(new News(R.drawable.google,"this is title","hello world","by thien",3));
        news_byview.add(new News(R.drawable.google,"this is title","hello world","by thien",2));
        news_byview.add(new News(R.drawable.google,"this is title","hello world","by thien",5));
        news_byview.add(new News(R.drawable.google,"Tổng Bí thư gửi thư chúc mừng Chủ tịch Đảng Nhân dân Campuchia Hunsen","hello world","by thien",9));
        news_byview.add(new News(R.drawable.google,"this is title","hello world","by thien",8));
        news_byview.sort(Comparator.comparingInt(News::getTime).reversed());
        NewContext_Adapter adapter = new NewContext_Adapter(news_byview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        return view;
    }
}