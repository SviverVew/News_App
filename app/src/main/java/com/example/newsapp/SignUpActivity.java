package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText EmailSignUp,PassWord,RePassWord;
    Button SignUpBtn;
    ProgressBar progressBar;
    private FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Auth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarSignUp);
        EmailSignUp=findViewById(R.id.signup_Username);
        PassWord=findViewById(R.id.signup_Password);
        RePassWord=findViewById(R.id.signup_RePassword);
        SignUpBtn= findViewById(R.id.signup_submit);
        progressBar.setVisibility(View.INVISIBLE);
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUp();
            }

        });

    }
    //đẩy data lên firebase realtime
    private void PushData(){
        String UserName,Pass,RePass;
        UserName= EmailSignUp.getText().toString().toLowerCase();
        Pass=PassWord.getText().toString();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        //push data in firebase
        //đổi mail sang dãy kí tự  để lưu dưới dạng id tránh dấu . và , không lưu đc trên firebase
        String MailChangeEncode = UtilsEncode.encodeEmailToNumber(UserName);
        //set key & value cho tree database
        //Pass
        String UserInfoPass = "User/"+MailChangeEncode+"/PassWord";
        DatabaseReference userRefPas = db.getReference(UserInfoPass);
        userRefPas.setValue(Pass);
        //Mail
        String UserInfoMail = "User/"+MailChangeEncode+"/Email";
        DatabaseReference userRefEmail = db.getReference(UserInfoMail);
        userRefEmail.setValue(UserName);
        String MarkNews = "User/"+MailChangeEncode+"/MarkNews";
        DatabaseReference userRefMarkNews = db.getReference(MarkNews);
    }
    private void SignUp() {
        String UserName,Pass,RePass;
        UserName= EmailSignUp.getText().toString();
        Pass=PassWord.getText().toString();
         RePass = RePassWord.getText().toString();

        if(TextUtils.isEmpty(UserName)){
            Toast.makeText(this,"Vui lòng nhập Email!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Pass)){
            Toast.makeText(this,"Vui lòng nhập mật khẩu !!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Pass.equals(RePass)){
            Toast.makeText(this,"Nhập lại mật khẩu chưa trùng khớp !!",Toast.LENGTH_SHORT).show();
            return;
        }

        Auth.createUserWithEmailAndPassword(UserName,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Đăng kí thành công!",Toast.LENGTH_SHORT).show();
                    PushData();
                    Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                    //AlertDialog dang ki thanh cong , vui long dang nhap tai khoan vua dang ki
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(),"Đăng kí thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}