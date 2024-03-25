package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
  TextView createAcc;
     EditText Login_Pass, Login_UserName;
    Button Submit,ForgotPass;
    ProgressBar progressBar;
    CheckBox RememberMe;
    public  static final String SHARED_PREFS="sharedPrefs";
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressBarLogin);
        createAcc = findViewById(R.id.login_CreateAccount);
        Auth=FirebaseAuth.getInstance();
        Login_Pass=findViewById(R.id.login_password);
        Login_UserName=findViewById(R.id.login_username);
        Submit= findViewById(R.id.login_submit);
        progressBar.setVisibility(View.INVISIBLE);
        ForgotPass=findViewById(R.id.ResetPass);
        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ResetPassActivity.class);
                startActivity(i);
            }
        });

        checkBox();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginWithMail();
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

    private void checkBox() {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String check=sharedPreferences.getString("name","");
        if(check.equals("true")){
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }
    }

    private void tokenAutoLogin(){
        //lưu token mỗi khi thoát app tự động đăng nhập

    }
    private void loginWithMail() {
        String UserName,PassWord;
        UserName=Login_UserName.getText().toString();
        RememberMe = findViewById(R.id.checkBox_RememberMe);
        PassWord=Login_Pass.getText().toString();
        if(TextUtils.isEmpty(UserName)){
            Toast.makeText(this,"Vui lòng nhập Email!!",Toast.LENGTH_SHORT).show();
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
                    progressBar.setVisibility(View.VISIBLE);
                    //đổ dữ liệu vào catch để lưu phiên đn khi ấn vào nút lưu đn
                    if(RememberMe.isChecked()){
                    SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("name","true");
                    editor.apply();
                    }
                    else {
                        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("name","");
                        editor.apply();
                    }
                    //thông báo đn thành công
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Email hoặc mật khẩu sai!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}