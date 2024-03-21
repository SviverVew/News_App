package com.example.newsapp;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
                News news = (News) intent.getSerializableExtra("data");
               String Title = news.getTitle();
               String ID = news.getId();
               String View = news.getView();
               String Image = news.getImage();
                String MailChangeEncode = UtilsEncode.encodeEmailToNumber(EmailSignUp);
                FirebaseDatabase db=FirebaseDatabase.getInstance();

                String MarkNewsTitle = "User/"+MailChangeEncode+"/MarkNews/"+ID+"/Title";
                String MarkNewsView = "User/"+MailChangeEncode+"/MarkNews/"+ID+"/View";
                String MarkNewsImg = "User/"+MailChangeEncode+"/MarkNews/"+ID+"/Img";
                DatabaseReference userRefMarkNewsTitle = db.getReference(MarkNewsTitle);
                DatabaseReference userRefMarkNewsView = db.getReference(MarkNewsView);
                DatabaseReference userRefMarkNewsImg = db.getReference(MarkNewsImg);
                userRefMarkNewsTitle.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Dữ liệu đã tồn tại, không thực hiện ghi
                            Toast.makeText(getApplicationContext(), "Tin đã lưu rồi!!", Toast.LENGTH_SHORT).show();

                        } else {
                            // Dữ liệu chưa tồn tại, thực hiện ghi
                            userRefMarkNewsTitle.setValue(Title).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Ghi dữ liệu thành công
                                        Toast.makeText(getApplicationContext(), "Lưu tin thành công !!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        // Ghi dữ liệu không thành công

                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi khi truy xuất dữ liệu
                    }
                });
                userRefMarkNewsView.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Dữ liệu đã tồn tại, không thực hiện ghi

                        } else {
                            // Dữ liệu chưa tồn tại, thực hiện ghi
                            userRefMarkNewsView.setValue(View).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Ghi dữ liệu thành công

                                    } else {
                                        // Ghi dữ liệu không thành côngToast.makeText(getApplicationContext(), "Lưu tin thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi khi truy xuất dữ liệu
                    }
                });
                userRefMarkNewsImg.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Dữ liệu đã tồn tại, không thực hiện ghi

                        } else {
                            // Dữ liệu chưa tồn tại, thực hiện ghi
                            userRefMarkNewsImg.setValue(Image).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Ghi dữ liệu thành công

                                    } else {
                                        // Ghi dữ liệu không thành công

                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi khi truy xuất dữ liệu
                    }
                });
            }
        });
    }
}