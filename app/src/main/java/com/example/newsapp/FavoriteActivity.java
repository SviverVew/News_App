package com.example.newsapp;

import static android.content.ContentValues.TAG;

import android.app.Activity;

import com.example.newsapp.adapter.Favourite_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.adapter.NewContext_Adapter;
import com.example.newsapp.testmodel.News;
import com.example.newsapp.testmodel.UserFavourite;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity {
    private ArrayList<UserFavourite> Fav;
    Favourite_Adapter FavAdap;
    private RecyclerView recyclerView;
    private FirebaseDatabase db;
    ArrayList<News> newsList;
    String Email;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        back=findViewById(R.id.buttonBackFav);

        newsList = new ArrayList<>();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            Email=currentUser.getEmail();}
        EdgeToEdge.enable(this);
        recyclerView = findViewById(R.id.Fav_List);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FavoriteActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        getDataFromRealTime();
    }

    private void getDataFromRealTime() {
        db = FirebaseDatabase.getInstance();
        String mailChangeEncode = UtilsEncode.encodeEmailToNumber(Email);
        DatabaseReference ref = db.getReference("User/" + mailChangeEncode + "/MarkNews");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    News favourite = new News();
                    //String key = childSnapshot.getKey();
                    String img = childSnapshot.child("Img").getValue(String.class);
                    String title = childSnapshot.child("Title").getValue(String.class);
                    String time = childSnapshot.child("Time").getValue(String.class);
                    String context = childSnapshot.child("Context").getValue(String.class);
                    String view = childSnapshot.child("View").getValue(String.class);
                    String id = childSnapshot.child("ID").getValue(String.class);
                    String cate = childSnapshot.child("Category").getValue(String.class);
                    favourite.setImage(img);
                    favourite.setTitle(title);
                    favourite.setTime(time);
                    favourite.setView(view);
                    favourite.setContext(context);
                    favourite.setId(id);
                    favourite.setCategory(cate);
                    newsList.add(favourite);

                }
                FavAdap = new Favourite_Adapter(newsList);

                recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(FavoriteActivity.this, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(FavAdap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Lỗi: " + error.getMessage());
            }
        });
    }
}