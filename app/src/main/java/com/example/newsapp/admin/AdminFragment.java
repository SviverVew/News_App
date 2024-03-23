
package com.example.newsapp.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.newsapp.List_News_Activity;
import com.example.newsapp.MainActivity;
import com.example.newsapp.R;


public class AdminFragment extends Fragment {
    Button news_Manager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_admin, container, false);
        news_Manager = view.findViewById(R.id.admin_fragment_newsManager);
        news_Manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), List_News_Activity.class);
                startActivity(i);
            }
        });
        return view;
    }
}
