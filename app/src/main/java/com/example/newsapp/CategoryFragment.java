package com.example.newsapp;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.adapter.NewContext_Adapter;
import com.example.newsapp.testmodel.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<News> newsList;
    private NewContext_Adapter adapter;
    private CategoryFragment categoryFragment;
    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryFragment = new CategoryFragment();
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.main_load);
        newsList = new ArrayList<>();
        adapter = new NewContext_Adapter(newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void updateData(String category) {
        // Load dữ liệu tương ứng với category vào recyclerView
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news")
                .whereEqualTo("news_cate", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            newsList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Lấy dữ liệu của tài liệu
                                Map<String, Object> data = document.getData();
                                //Tạo obj news
                                News objnews = new News();
                                String uri = document.get("news_imgURI").toString();
                                Log.d(ContentValues.TAG, "onComplete: the document's image: " + uri);
                                String URL = "https://firebasestorage.googleapis.com/v0/b/newsdb-e0729.appspot.com/o/image%2F" + uri +  "?alt=media";
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
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private String getCate(String id, News news){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
}