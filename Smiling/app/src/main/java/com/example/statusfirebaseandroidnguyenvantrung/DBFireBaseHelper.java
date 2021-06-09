package com.example.statusfirebaseandroidnguyenvantrung;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DBFireBaseHelper {

    private FirebaseDatabase instance;// lay ve instance
    private DatabaseReference db;//lay ve db
    private String userID;
    public  void createUser(String name, String email, String password, int normal, int happy, int unhappy){
        instance=FirebaseDatabase.getInstance();
        db=instance.getReference("User");// ten bang

        if(TextUtils.isEmpty(userID))// neu chua co userid
        {
            userID=db.push().getKey();//tao 1 key
        }
        User user=new User(name,email,password,normal,happy,unhappy);
        db.child(userID).setValue(user);//insert du lieu
    }
    public  void updateUser(String name, String email, String password, int normal, int happy, int unhappy,TextView tvResult){
        instance=FirebaseDatabase.getInstance();
        db=instance.getReference("User");// ten bang
        if(!TextUtils.isEmpty(name))// neu chua co userid
           db.child(userID).child("name").setValue(name);// cap nhat du lieu vao name
        if(!TextUtils.isEmpty(email))
            db.child(userID).child("email").setValue(email);
        if(!TextUtils.isEmpty(password))
            db.child(userID).child("password").setValue(password);
        if(!TextUtils.isEmpty(String.valueOf(normal)))
            db.child(userID).child("normal").setValue(normal);
        if(!TextUtils.isEmpty(String.valueOf(happy)))
            db.child(userID).child("happy").setValue(happy);
        if(!TextUtils.isEmpty(String.valueOf(unhappy)))
            db.child(userID).child("unhappy").setValue(unhappy);
        changeListenerUser(tvResult);
    }
    public void changeListenerUser(TextView textView){
        db.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                textView.setText("Update successfull :"+user.getName());
                //lay listUser
                List<User> ls=new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user1=snapshot.getValue(User.class);
                    ls.add(user1);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                textView.setText(error.getMessage());
            }
        });
    }
}
