package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.testmodel.News;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Show_Context_Activity extends AppCompatActivity {
    Intent intent;
    String EmailSignUp;
    MaterialToolbar toolbar;
    TextView title, context, view, category,time;
    ImageView image;
    Button BackBtn,SaveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        setContentView(R.layout.activity_show_context);
        //FIND VIEW
        title = findViewById(R.id.show_news_title);
        context = findViewById(R.id.show_news_context);
        time = findViewById(R.id.show_news_datecreated);
        view = findViewById(R.id.show_news_view);
        image = findViewById(R.id.shows_news_image);
        category = findViewById(R.id.show_news_category);
        BackBtn= findViewById(R.id.BackContext);
        SaveBtn= findViewById(R.id.SaveNews);
        if(currentUser!=null){
            EmailSignUp=currentUser.getEmail();}
        //GET DATA TỪ Intent
        intent = getIntent();
        if(intent.hasExtra("data")){
            News news = (News) intent.getSerializableExtra("data");

            title.setText(news.getTitle().toString());
            context.setText(news.getContext().toString());
            view.setText(news.getView().toString() + " view");
            category.setText("Chuyên mục: " + news.getCategory());
            Picasso.get().load(news.getImage()).into(image);
            time.setText(news.getTime().toString());
        }
        else {
            Intent i = new Intent(Show_Context_Activity.this, news_content_Fragment.class);
            Show_Context_Activity.this.startActivity(i);
            Toast.makeText(this.getParent(),"Lỗi khi tải dữ liệu", Toast.LENGTH_LONG);
        }
        //toolbar click
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(Show_Context_Activity.this,MainActivity.class);
                startActivity(intentBack);
            }
        });
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MailChangeEncode = UtilsEncode.encodeEmailToNumber(EmailSignUp);
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                String MarkNews = "User/"+MailChangeEncode+"/MarkNews/Title";
                DatabaseReference userRefMarkNews = db.getReference(MarkNews);
                userRefMarkNews.setValue(title);
            }
        });
    }
}