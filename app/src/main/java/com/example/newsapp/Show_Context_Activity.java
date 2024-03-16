package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.testmodel.News;
import com.google.android.material.appbar.MaterialToolbar;

public class Show_Context_Activity extends AppCompatActivity {
    Intent intent;
    MaterialToolbar toolbar;
    TextView title, context, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_context);
        //FIND VIEW
        title = findViewById(R.id.show_news_title);
        context = findViewById(R.id.show_news_context);
        date = findViewById(R.id.show_news_datecreated);
        toolbar = findViewById(R.id.show_news_toolbar);
        //GET DATA TỪ Intent
        intent = getIntent();
        if(intent.hasExtra("data")){
            News news = (News) intent.getSerializableExtra("data");
            toolbar.setTitle(news.getUser().toString());
            title.setText(news.getTitle().toString());
            context.setText(news.getContext().toString());
//            date.setText(news.getTime().toString() );
        }
        else {
            Intent i = new Intent(Show_Context_Activity.this, news_content_Fragment.class);
            Show_Context_Activity.this.startActivity(i);
            Toast.makeText(this.getParent(),"Lỗi khi tải dữ liệu", Toast.LENGTH_LONG);
        }
        //toolbar click
    }
}