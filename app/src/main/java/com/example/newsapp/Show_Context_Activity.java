package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.testmodel.News;
import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

public class Show_Context_Activity extends AppCompatActivity {
    Intent intent;
    MaterialToolbar toolbar;
    TextView title, context, time, view, category;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_context);
        //FIND VIEW
        title = findViewById(R.id.show_news_title);
        context = findViewById(R.id.show_news_context);
        time = findViewById(R.id.show_news_datecreated);
        view = findViewById(R.id.show_news_view);
        image = findViewById(R.id.shows_news_image);
        category = findViewById(R.id.show_news_category);
        toolbar = findViewById(R.id.show_news_toolbar);
        //GET DATA TỪ Intent
        intent = getIntent();
        if(intent.hasExtra("data")){
            News news = (News) intent.getSerializableExtra("data");
            toolbar.setTitle(news.getUser().toString());
            title.setText(news.getTitle().toString());
            context.setText(news.getContext().toString());
            view.setText(news.getView().toString() + " view");
            category.setText("Chuyên mục: " + news.getCategory());
            time.setText("Ngày đăng: " + news.getTime());
            Picasso.get().load(news.getImage()).into(image);
        }
        else {
            Intent i = new Intent(Show_Context_Activity.this, news_content_Fragment.class);
            Show_Context_Activity.this.startActivity(i);
            Toast.makeText(this.getParent(),"Lỗi khi tải dữ liệu", Toast.LENGTH_LONG);
        }
        //toolbar click
    }
}