package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = findViewById(R.id.materialToolbarHome);
        setSupportActionBar(myToolbar);


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Nóng"));
        tabLayout.addTab(tabLayout.newTab().setText("Mới"));
        tabLayout.addTab(tabLayout.newTab().setText("Bóng đá VN"));
        tabLayout.addTab(tabLayout.newTab().setText("Bóng đá QT"));
        tabLayout.addTab(tabLayout.newTab().setText("Độc & Lạ"));
        tabLayout.addTab(tabLayout.newTab().setText("Tình yêu"));
        tabLayout.addTab(tabLayout.newTab().setText("Tình yêu"));
        tabLayout.addTab(tabLayout.newTab().setText("Giải trí"));
        tabLayout.addTab(tabLayout.newTab().setText("Thế giới"));
        tabLayout.addTab(tabLayout.newTab().setText("Pháp luật"));
        tabLayout.addTab(tabLayout.newTab().setText("Xe 360"));
        tabLayout.addTab(tabLayout.newTab().setText("Công nghệ"));
        tabLayout.addTab(tabLayout.newTab().setText("Ẩm thực"));
        tabLayout.addTab(tabLayout.newTab().setText("Làm đẹp"));
        tabLayout.addTab(tabLayout.newTab().setText("Sức khỏe"));
        tabLayout.addTab(tabLayout.newTab().setText("Du lịch"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return true;
    }
}