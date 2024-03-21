package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.newsapp.admin.AdminFragment;
import com.example.newsapp.testmodel.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Fragment context;
    TabLayout tabLayout;
    MaterialToolbar topmenu;
    BottomNavigationView bottomnav;
    DrawerLayout drawerLayout;
    FirebaseFirestore db;
    private CategoryFragment categoryFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.tabLayout);
        getDataFromFirestore();
        categoryFragment = new CategoryFragment();
        //TEST
        //FIND VIEW
        topmenu = findViewById(R.id.main_topmenu);
        bottomnav = findViewById(R.id.main_bottommenu);
        drawerLayout=findViewById(R.id.drawer_layout);
        bottomnav.setOnItemSelectedListener(onItemSelectedListener());
        ChangeFragment(new news_content_Fragment());
        topmenu.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                /*if(item.getItemId()==R.id.topmenu_profile){
                    ChangeIntent(MainActivity.this, LoginActivity.class);
                    return true;
                }*/
                if(item.getItemId()==R.id.topmenu_search){
                    ChangeIntent(MainActivity.this, Search_Activity.class);
                    return  true;
                }
                return false;
            }
        });
        //TEST
        Button navBtn=findViewById(R.id.NavButton);
        navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }
    // Hàm hoàn chỉnh sau khi thêm dữ liệu category vào TabLayout và xử lý sự kiện khi chọn tab
    private void getDataFromFirestore() {
        db = FirebaseFirestore.getInstance();
        db.collection("category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<News> cateList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                News objnews = new News();
                                objnews.setCategory(data.get("cateName").toString());
                                objnews.setId(data.get("cateID").toString());
                                cateList.add(objnews);
                            }
                            // Thêm dữ liệu category vào TabLayout
                            addCategoriesToTabLayout(cateList);
//                            Intent intent = new Intent(MainActivity.this, news_content_Fragment.class);
//                            intent.putExtra("cate", cateList.toString());
                        } else {
                            Log.d(ContentValues.TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private String getCate(String id, News news){
        db.collection("category").document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        news.setCategory(documentSnapshot.getString("cateName"));
                    }
                });
        return news.getCategory();
    }
    // Hàm thêm dữ liệu category vào TabLayout
    private void addCategoriesToTabLayout(List<News> cateList) {
        for (News cate : cateList) {
            String categoryId = cate.getId();
            String category = cate.getCategory();
            TabLayout.Tab tab = tabLayout.newTab().setText(category);
            tabLayout.addTab(tab);
            // Gán id của category vào tag của tab
            tab.setTag(categoryId);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Lấy categoryId từ tag của tab được chọn
                String categoryId = (String) tab.getTag();
                // Cập nhật dữ liệu trong categoryFragment
                categoryFragment.updateData(categoryId);
                ChangeFragment(categoryFragment);
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Không cần xử lý khi unselected tab
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần xử lý khi reselected tab
            }
        });

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