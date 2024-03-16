package com.example.newsapp.admin;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.newsapp.R;
import com.example.newsapp.adapter.Category_Adapter;
import com.example.newsapp.testmodel.Category;
import com.example.newsapp.testmodel.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Show_News_Info_Activity extends AppCompatActivity {
    FirebaseFirestore db;
    ArrayList<Category> categories;
    Category_Adapter adapter;
    EditText title, context;
    TextView imgName;
    ImageView img;
    Spinner spinner;
    Button btn_update, btn_delete;
    Intent intent;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news_info);
        //get db
        db = FirebaseFirestore.getInstance();;
        categories = new ArrayList<Category>();
        //find view
        title = findViewById(R.id.admin_show_new_title);
        context = findViewById(R.id.admin_show_new_context);
        imgName = findViewById(R.id.admin_show_update_imgname);
        img = findViewById(R.id.admin_show_update_img);
        spinner = findViewById(R.id.admin_show_new_category);
        btn_delete = findViewById(R.id.admin_show_button_delete);
        btn_update = findViewById(R.id.admin_show_button_update);
        getCategory();
        //get intetnt
        intent = getIntent();
        if(intent.hasExtra("newsinfo")){
            News news = (News) intent.getSerializableExtra("newsinfo");
            title.setText(news.getTitle().toString());
            context.setText(news.getContext().toString());
            Picasso.get().load(news.getImage()).into(img);
            img.setVisibility(View.VISIBLE);
            //set spinner value by news's caet name
        }
    }
    private void getCategory(){
        db.collection("category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Category cate = new Category(document.get("cateID").toString(),document.get("cateName").toString());
                                categories.add(cate);
                            }
                            adapter = new Category_Adapter(Show_News_Info_Activity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categories);
                            spinner.setAdapter(adapter);
                            size =  categories.size();

                            // set spinner value for item when display
                            if(intent.hasExtra("newsinfo")){
                                News news = (News) intent.getSerializableExtra("newsinfo");
                                String newsCategory = news.getCategory();
                                for(int i = 0; i < size; i++) {
                                    if (categories.get(i).getCateName().equals(newsCategory)) {
                                        spinner.setSelection(i);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
    }
}