package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
  EditText signup_Username,signup_UserPhone,signup_Password,signup_RePassword;
  Button signup_submit;
  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup_Username=findViewById(R.id.signup_Username);
        signup_UserPhone=findViewById(R.id.signup_UserPhone);
        signup_Password=findViewById(R.id.signup_Password);
        signup_RePassword=findViewById(R.id.signup_RePassword);
        signup_submit=findViewById(R.id.signup_submit);
        signup_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                Bundle bundle=new Bundle();//tao goi du lieu
                bundle.putString("User",signup_Username.getText().toString());//dua user vao
                if(signup_Password.getText().toString().equals(signup_RePassword.getText().toString()))
                {
                    bundle.putString("Pass",signup_Password.getText().toString());//dua pass vao
                }
                //sai pass thi dua ra thong bao
                else {
                    Toast.makeText(getApplicationContext(),"MAT KHAU KHONG KHOP", Toast.LENGTH_LONG).show();
                    return;
                }
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}