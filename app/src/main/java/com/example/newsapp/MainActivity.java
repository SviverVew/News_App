package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.newsapp.adapter.NewContext_Adapter;
import com.example.newsapp.admin.AdminFragment;
import com.example.newsapp.testmodel.News;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Fragment context;
    MaterialToolbar topmenu;
    BottomNavigationView bottomnav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // minh ne
        //cai loz me m
        //TEST
        //FIND VIEW
        topmenu = findViewById(R.id.main_topmenu);
        bottomnav = findViewById(R.id.main_bottommenu);
        bottomnav.setOnItemSelectedListener(onItemSelectedListener());
        ChangeFragment(new news_content_Fragment());
        topmenu.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.topmenu_profile){
                    ChangeIntent(MainActivity.this, LoginActivity.class);
                    return true;
                }
                return false;
            }
        });
        //TEST
    }
    //EVENT BOTTOMNAV
    private NavigationBarView.OnItemSelectedListener onItemSelectedListener(){
        return new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.idtintuc){
                    ChangeFragment(new news_content_Fragment());
                }
                if(item.getItemId()==R.id.idxuhuong){
                    ChangeFragment(new news_content_trending_Fragment());
                }
                if(item.getItemId()==R.id.idtienich){
                    ChangeFragment(new AdminFragment());
                }
                return true;
            }
        };
    }
    //CHUYEN DOI INTENT
     void ChangeIntent(Activity current, Class<?> next){
        Intent intent = new Intent(current, next);
        current.startActivity(intent);
    }
    //CHUYEN DOI FRAGMENT
     void ChangeFragment(Fragment next){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_context, next);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}