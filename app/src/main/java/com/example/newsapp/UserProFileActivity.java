package com.example.newsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.Objects;

public class UserProFileActivity extends AppCompatActivity {
    NavigationView Menu_Nav;
    public  static final String SHARED_PREFS="sharedPrefs";
    MenuItem Home , Call,ChangePass,DelUser,Logout;
    TextView TextViewEmail;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertDialog = new AlertDialog.Builder(UserProFileActivity.this);
        setContentView(R.layout.activity_user_pro_file);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        TextViewEmail = findViewById(R.id.textViewEmail);
        TextViewEmail.setText(currentUser.getEmail());
        Toast.makeText(UserProFileActivity.this, TextViewEmail.getText(), Toast.LENGTH_SHORT).show();
        assert currentUser != null;
        String MailChangeEncode = UtilsEncode.encodeEmailToNumber(Objects.requireNonNull(currentUser.getEmail()));
        String UID = "User/"+MailChangeEncode+"/Email";
        Menu_Nav = findViewById(R.id.nav_view);

        Menu_Nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.homUser) {
                    Intent intent = new Intent(UserProFileActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.Lienhe) {
                    Intent intent = new Intent(UserProFileActivity.this, LienHeActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.ChangePass) {
                    Intent intent = new Intent(UserProFileActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.xoathongtin) {
                    onClickDeleteProfile();
                } else if (itemId == R.id.logoutProfile) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "");
                    editor.apply();
                    Intent Out = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(Out);
                }

                return true;
            }
        });
    }

    private void onClickDeleteProfile() {

        FirebaseUser UserPoint = FirebaseAuth.getInstance().getCurrentUser();
        String MailChangeEncode = UtilsEncode.encodeEmailToNumber(UserPoint.getEmail());
        String UID = "User/"+MailChangeEncode;
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference nodeRef = databaseRef.child(UID);
        alertDialog.setTitle("Bạn chắc chắn muốn xóa tài khoản?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (UserPoint != null) {
                    UserPoint.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UserProFileActivity.this, "Xóa User thành công", Toast.LENGTH_SHORT).show();
                                        nodeRef.removeValue();
                                        finish();
                                        FirebaseAuth.getInstance().signOut();
                                        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.putString("name","");
                                        editor.apply();
                                        Intent Out = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(Out);

                                    } else {
                                        Toast.makeText(UserProFileActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UserProFileActivity.this, "Hủy xóa", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }
}