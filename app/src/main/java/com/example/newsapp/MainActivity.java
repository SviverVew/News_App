package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newsapp.fragments.NewsFragment;
import com.example.newsapp.fragments.TienichFragment;
import com.example.newsapp.fragments.TrendFragment;
import com.example.newsapp.fragments.VideoFragment;
import com.google.android.material.navigation.NavigationBarView;
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
        //BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewHome);
        //bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener());
        //setFragment(new NewsFragment());
    }

    void setFragment(Fragment newFragment) {
        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragmentContainerView, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return true;
    }
    /*private NavigationBarView.OnItemSelectedListener onItemSelectedListener() {
        return new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.idtintuc) {
                    setFragment(new NewsFragment());
                    myToolbar.setTitle("Chats");
                }
                if(item.getItemId() == R.id.idvideo) {
                    setFragment(new VideoFragment());
                    myToolbar.setTitle("Calls");
                }
                if(item.getItemId() == R.id.idxuhuong) {
                    setFragment(new TrendFragment());
                    myToolbar.setTitle("People");
                }
                if(item.getItemId() == R.id.idtienich) {
                    setFragment(new TienichFragment());
                    myToolbar.setTitle("Stories");
                }
                return true;
            }
        };
    }*/
}