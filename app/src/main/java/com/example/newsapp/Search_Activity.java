package com.example.newsapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.newsapp.adapter.NewContext_Adapter;
import com.example.newsapp.testmodel.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Search_Activity extends AppCompatActivity {
    NewContext_Adapter news;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    MaterialToolbar toolbar;
    Button btnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = findViewById(R.id.main_search);
        setSupportActionBar(toolbar);
        setTitle("");
        btnClose = findViewById(R.id.btnClose);
        recyclerView = findViewById(R.id.rcvnews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        // Gọi hàm getDataFromFirestore() để lấy dữ liệu từ Firestore
        getDataFromFirestore();

        SearchView searchView = (SearchView) findViewById(R.id.mnsearches);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                news.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                news.getFilter().filter(newText);
                return false;
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_Activity.this, MainActivity.class); // Thay NextActivity bằng tên của hoạt động mà bạn muốn chuyển đến
                startActivity(intent);
            }
        });
    }



    private void getDataFromFirestore() {
        db = FirebaseFirestore.getInstance();
        db.collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<News> newsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Lấy dữ liệu của tài liệu
                                Map<String, Object> data = document.getData();
                                // Tạo object News từ dữ liệu Firestore
                                News objNews = new News();
                                objNews.setImage(R.drawable.google);
                                objNews.setTitle(data.get("news_Title").toString());
                                objNews.setContext(data.get("news_Context").toString());
                                objNews.setUser(data.get("news_ID").toString());
                                objNews.setTime(data.get("news_time").toString());
                                //objNews.setView(Integer.parseInt(data.get("news_View").toString()));
                                newsList.add(objNews);
                            }

                            // Khởi tạo adapter và set dữ liệu vào RecyclerView
                            news = new NewContext_Adapter(newsList);
                            recyclerView.setAdapter(news);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}