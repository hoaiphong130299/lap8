package com.example.statusfirebaseandroidnguyenvantrung;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MainFace extends AppCompatActivity {

    ImageView imgNormal,imgHappy,imgUnHappy;
    Button btnFinish;
    Integer normal=0,happy=0,unhappy=0;
    DatabaseReference databaseReference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_main);
        init();
        imgNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normal++;
                Toast.makeText(MainFace.this, "Normal: "+String.valueOf(normal), Toast.LENGTH_SHORT).show();
            }
        });
        imgHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                happy++;
                Toast.makeText(MainFace.this, "Happy: "+String.valueOf(happy), Toast.LENGTH_SHORT).show();
            }
        });
        imgUnHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 unhappy++;
                Toast.makeText(MainFace.this, "Unhappy: "+String.valueOf(unhappy), Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userauth=user.getUid().toString();
                HashMap hashMap=new HashMap();
                hashMap.put("happy",happy);
                hashMap.put("normal",normal);
                hashMap.put("unhappy",unhappy);
                databaseReference.child(userauth).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task task) {
                        Toast.makeText(MainFace.this, "Update successfull !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void init(){
        imgHappy=findViewById(R.id.imgHappy);
        imgNormal=findViewById(R.id.imgNormal);
        imgUnHappy=findViewById(R.id.imgUnHappy);
        btnFinish=findViewById(R.id.btnFinish);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("User");
    }
}
