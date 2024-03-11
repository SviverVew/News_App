package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView createAcc;
    EditText Login_Pass, Login_UserName;
    Button Submit;
    private FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createAcc = findViewById(R.id.login_CreateAccount);
        Auth=FirebaseAuth.getInstance();
        Login_Pass=findViewById(R.id.login_password);
        Login_UserName=findViewById(R.id.login_username);
        Submit= findViewById(R.id.login_submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this, SignUpActivity.class);
                signup.putExtra("ok",1);
                LoginActivity.this.startActivity(signup);
            }
        });
    }

    private void login() {
        String UserName,PassWord;
        UserName=Login_UserName.getText().toString();
        PassWord=Login_Pass.getText().toString();
        if(TextUtils.isEmpty(UserName)){
            Toast.makeText(this,"Vui lòng nhập Email hoặc Số điện thoại!!",Toast.LENGTH_LONG).show();
        return;
        }
        if(TextUtils.isEmpty(PassWord)){
            Toast.makeText(this,"Vui lòng nhập mật khẩu !!",Toast.LENGTH_SHORT).show();
       return;
        }
        Auth.signInWithEmailAndPassword(UserName,PassWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Email , Số điện thoại hoặc mật khẩu sai!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}