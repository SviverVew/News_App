package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {
    EditText editMail;
    Button Reset,Back;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    String Mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        editMail=findViewById(R.id.editTextMailReset);
        Reset=findViewById(R.id.ButtonReset);
        Back=findViewById(R.id.ButtonBackReset);
        mAuth=FirebaseAuth.getInstance();
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResetPassActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Mail=editMail.getText().toString().trim();
                if(!TextUtils.isEmpty(Mail)){
                    ResetPassword();
                }
                else {
                    Toast.makeText(ResetPassActivity.this, "Không đuợc để trống Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void ResetPassword()
    {
        mAuth.sendPasswordResetEmail(Mail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ResetPassActivity.this, "Đã gửi yêu cầu đổi mật khẩu vào Mail", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ResetPassActivity.this,LoginActivity.class);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ResetPassActivity.this, "Lỗi :" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}