package com.example.newsapp;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;
import static com.google.firebase.appcheck.internal.util.Logger.getLogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.adapter.NewContext_Adapter;
import com.example.newsapp.testmodel.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Show_Context_Activity extends AppCompatActivity {
    Intent intent;
    String EmailSignUp;
    MaterialToolbar toolbar;
    TextView title, context, view, category, time;
    ImageView image;
    RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<News> newsList;
    Button BackBtn, SaveBtn;
    News newss = new News();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        setContentView(R.layout.activity_show_context);

        // Khởi tạo ArrayList newsList
        newsList = new ArrayList<>();

        // Khai báo các thành phần giao diện
        title = findViewById(R.id.show_news_title);
        context = findViewById(R.id.show_news_context);
        recyclerView = findViewById(R.id.listss);
        time = findViewById(R.id.show_news_datecreated);
        view = findViewById(R.id.show_news_view);
        image = findViewById(R.id.shows_news_image);
        category = findViewById(R.id.show_news_category);
        BackBtn = findViewById(R.id.BackContext);
        SaveBtn = findViewById(R.id.SaveNews);

        // Lấy thông tin người dùng hiện tại nếu có
        if (currentUser != null) {
            EmailSignUp = currentUser.getEmail();
        }

        // Lấy dữ liệu từ Intent
        intent = getIntent();
        if (intent.hasExtra("data")) {
            News news = (News) intent.getSerializableExtra("data");
            getIDcate(news.getCategory(), news.getId());
            title.setText(news.getTitle().toString());
            context.setText(news.getContext().toString());
            view.setText(news.getView().toString() + " lượt xem");
            category.setText("Chuyên mục: " + news.getCategory());
            Picasso.get().load(news.getImage()).into(image);
            time.setText(news.getTime().toString());
        } else {
            // Chuyển đến màn hình chính nếu không có dữ liệu
            Intent i = new Intent(Show_Context_Activity.this, news_content_Fragment.class);
            Show_Context_Activity.this.startActivity(i);
            Toast.makeText(this.getParent(), "Lỗi khi tải dữ liệu", Toast.LENGTH_LONG);
        }

        // Xử lý sự kiện nút "Quay lại"
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(Show_Context_Activity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

        // Xử lý sự kiện nút "Lưu tin"
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý lưu tin
                // (Đoạn mã xử lý lưu tin đã được bạn viết ở đây)
            }
        });
    }

    // Phương thức để lấy dữ liệu từ Firestore
    void getDataformFireStore(String category, String newsid) {
        db.collection("news")
                .whereEqualTo("news_cate", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String newsId = document.getString("news_ID");
                                // Kiểm tra xem tin tức có trùng với tin tức đang hiển thị không
                                if (newsId.equals(newsid)) {
                                    continue; // Nếu trùng, bỏ qua và không thêm vào danh sách
                                }

                                // Lấy dữ liệu của tài liệu
                                Map<String, Object> data = document.getData();
                                // Tạo obj news
                                News objnews = new News();
                                String uri = document.get("news_imgURI").toString();
                                Log.d(ContentValues.TAG, "onComplete: the document's image: " + uri);
                                String URL = "https://firebasestorage.googleapis.com/v0/b/newsdb-e0729.appspot.com/o/image%2F" + uri + "?alt=media";
                                objnews.setImage(URL);
                                objnews.setId(data.get("news_ID").toString());
                                objnews.setTitle(data.get("news_Title").toString());
                                objnews.setContext(data.get("news_Context").toString());
                                objnews.setUser(data.get("news_ID").toString());
                                objnews.setTime(data.get("news_time").toString());
                                objnews.setView(data.get("news_View").toString());
                                objnews.setCategory(getCate(data.get("news_cate").toString(), objnews));
                                newsList.add(objnews);
                            }
                            // Tạo adapter và đặt adapter cho RecyclerView
                            NewContext_Adapter adapter = new NewContext_Adapter(newsList);
                            Log.d(TAG, "onComplete: 22222"+newsList);
                            Log.d(TAG, "onComplete: "+category);
                            recyclerView.setAdapter(adapter);
                            // Thiết lập LayoutManager cho RecyclerView
                            recyclerView.setLayoutManager(new LinearLayoutManager(Show_Context_Activity.this));
                        } else {
                            Log.d(ContentValues.TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    // Phương thức để lấy tên chuyên mục từ Firestore dựa trên ID
    private String getCate(String id, News news) {
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
    private void getIDcate(String name, String newsid){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("category")
                .whereEqualTo("cateName", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@android.support.annotation.NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String categoryId = document.getId(); // Lấy ID của category từ document
                                newss.setCategory(categoryId);
                            }
                            getDataformFireStore(newss.getCategory(), newsid);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
