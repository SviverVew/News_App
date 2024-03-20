package com.example.newsapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.newsapp.R.id.tabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalTime;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.adapter.NewContext_Adapter;
import com.example.newsapp.admin.AdminFragment;
import com.example.newsapp.testmodel.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn;
    private boolean isDarkMode = false;
    public  static final String SHARED_PREFS="sharedPrefs";
    NavigationView Menu_Nav;
    Fragment context;
    TextView Mail_Nav;
    TabLayout tabLayout;
    MaterialToolbar topmenu;
    BottomNavigationView bottomnav;
    DrawerLayout drawerLayout;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.tabLayout);
        getDataFromFirestore();

        //TEST
        //FIND VIEW
        topmenu = findViewById(R.id.main_topmenu);
        bottomnav = findViewById(R.id.main_bottommenu);
        drawerLayout=findViewById(R.id.drawer_layout);
        bottomnav.setOnItemSelectedListener(onItemSelectedListener());
        ChangeFragment(new news_content_Fragment());

        Menu_Nav=findViewById(R.id.Nav_view);
        //đổ dữ liệu vào thanh navbar (Email . ảnh đại diện)
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        View headerView = Menu_Nav.getHeaderView(0);
        TextView MailView = headerView.findViewById(R.id.Email_Nav);
        if(currentUser!=null){
        MailView.setText(currentUser.getEmail());}

        //lấy id của button đăng xuất ,và add chức năng đăng xuất




        //đổi giao diện sáng tối
        Menu_Nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemID= menuItem.getItemId();
                //đổi giao diện
                isDarkMode = !isDarkMode;
                if(itemID==R.id.nav_change){
                    if (isDarkMode) {
                        setDarkMode();
                    } else {
                        setLightMode();
                    }
                }
                //nút trang chủ
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                if(itemID==R.id.nav_home){
                    startActivity(intent);
                }
                //đăng xuất
                if(itemID==R.id.nav_logout){
                    //đặt dữ liệu trong catch về null
                    SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("name","");
                    editor.apply();
                    Intent Out = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(Out);
                }
                if(itemID==R.id.nav_save){

                }
                return false;
            }

            private void setLightMode() {
                int lightColor = ContextCompat.getColor(getApplicationContext(), R.color.light_color);
                // Thực hiện các thay đổi màu sắc cho các thành phần trong giao diện người dùng
                getWindow().getDecorView().setBackgroundColor(lightColor);
                // Các thay đổi màu sắc khác (ví dụ: TextView, Button, etc.)
            }

            private void setDarkMode() {
                int darkColor = ContextCompat.getColor(getApplicationContext(), R.color.dark_color);
                // Thực hiện các thay đổi màu sắc cho các thành phần trong giao diện người dùng
                getWindow().getDecorView().setBackgroundColor(darkColor);
                // Các thay đổi màu sắc khác (ví dụ: TextView, Button, etc.)
            }
        });
        //nút trang chủ


        //thiết kế header
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
                            List<News> newsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                News objnews = new News();
                                objnews.setCategory(data.get("cateName").toString());
                                newsList.add(objnews);
                            }
                            // Thêm dữ liệu category vào TabLayout
                            addCategoriesToTabLayout(newsList);
                        } else {
                            Log.d(ContentValues.TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    // Hàm thêm dữ liệu category vào TabLayout
    private void addCategoriesToTabLayout(List<News> newsList) {
        for (News news : newsList) {
            String category = news.getCategory();
            tabLayout.addTab(tabLayout.newTab().setText(category));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                News selectedNews = newsList.get(position);
                switchFragment(selectedNews.getCategory());
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
    private void switchFragment(String category) {
        switch (category) {
            case "Chính trị":
                ChangeFragment(new news_content_Fragment());
                break;
            case "Thể thao":
                ChangeFragment(new AdminFragment());
                break;
            case "Âm nhạc":
                ChangeFragment(new new_content_trending_Fragment());
                break;
            default:
                break;
        }
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
                    ChangeFragment(new new_content_trending_Fragment());
                }
                if(item.getItemId()==R.id.idtienich){
                    Intent intent = new Intent(MainActivity.this, UserProFileActivity.class);
                    startActivity(intent);
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