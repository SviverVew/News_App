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
    Button newsManager, userManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin,container,false);
        newsManager = view.findViewById(R.id.admin_fragment_newsManager);
        newsManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = new MainActivity();
                main.ChangeIntent(getActivity(), List_News_Activity.class);
            }
        });
        return view;
    }
}