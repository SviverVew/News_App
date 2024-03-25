package com.example.newsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText passThis,passThat,passThatRepeat;
    Button Change,back;
    public  static final String SHARED_PREFS="sharedPrefs";
    AlertDialog.Builder alertDialog;
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this);
        setContentView(R.layout.activity_change_password);
        passThis=findViewById(R.id.PassThis);
        passThat=findViewById(R.id.PassThat);
        passThatRepeat=findViewById(R.id.PassThatRepeat);
        Change=findViewById(R.id.ChangePass_submit);
        back=findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChangePasswordActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        FirebaseUser UserPoint = FirebaseAuth.getInstance().getCurrentUser();
        String MailChangeEncode = UtilsEncode.encodeEmailToNumber(UserPoint.getEmail());
        String UID = "User/"+MailChangeEncode+"/PassWord";
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference nodeRef = databaseRef.child(UID);

        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String passes = snapshot.getValue(String.class);
                        if(passThis.getText().toString().equals(passes)){
                            if(passThat.getText().toString().equals(passThatRepeat.getText().toString())){
                               if (!passThat.getText().toString().isEmpty() || !passThatRepeat.getText().toString().isEmpty())  {
                                    alertDialog.setTitle("Bạn chắc chắn muốn đổi mật khẩu?");
                                    alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                            FirebaseUser user = firebaseAuth.getCurrentUser();

                                            user.updatePassword(passThat.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                String passChage = "User/" + MailChangeEncode + "/PassWord";
                                                                DatabaseReference userRefPas = db.getReference(passChage);
                                                                Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                                userRefPas.setValue(passThat.getText().toString());
                                                                Intent i = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                                                startActivity(i);
                                                                FirebaseAuth.getInstance().signOut();
                                                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                editor.putString("name", "");
                                                                editor.apply();
                                                                Intent Out = new Intent(getApplicationContext(), LoginActivity.class);
                                                                startActivity(Out);

                                                            } else {
                                                                Toast.makeText(ChangePasswordActivity.this, "Đăng xuất để đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                            dialog.dismiss();
                                        }
                                    });
                                    alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(ChangePasswordActivity.this, "Hủy đổi mật kẩu", Toast.LENGTH_LONG).show();

                                            dialog.dismiss();
                                        }
                                    });
                                    alertDialog.show();
                                }
                               else { Toast.makeText(ChangePasswordActivity.this, "Không được bỏ trống mật khẩu", Toast.LENGTH_LONG).show();}
                            } else if (passThat.getText().toString().length()<6) {
                                Toast.makeText(ChangePasswordActivity.this, "Mật khẩu phải >=6 kí tự", Toast.LENGTH_LONG).show();

                            }

                        }
                        else {
                            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu hiện tại không đúng  ", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}