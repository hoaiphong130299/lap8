package com.example.statusfirebaseandroidnguyenvantrung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MainRegister  extends AppCompatActivity {
    private TextInputLayout edtYourname,edtEmail,edtType,edtType2;
    private Button btnRegisteracount;
    private DBFireBaseHelper dbFireBaseHelper;
    private FirebaseAuth firebaseAuth;
    private TextView tvSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        edtYourname=(TextInputLayout) findViewById(R.id.edtYourName);
        edtEmail=(TextInputLayout) findViewById(R.id.edtEmail);
        edtType=(TextInputLayout) findViewById(R.id.password);
        edtType2=(TextInputLayout) findViewById(R.id.type);
        btnRegisteracount=findViewById(R.id.btnRegisteracount);
        tvSignin=findViewById(R.id.tvSignin);

        firebaseAuth=FirebaseAuth.getInstance();


        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainSignIn.class));
            }
        });
        btnRegisteracount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edtEmail.getEditText().getText().toString().trim();
                String type=edtType.getEditText().getText().toString().trim();
                String yourname=edtYourname.getEditText().getText().toString().trim();
                String type2=edtType2.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(yourname)){
                    edtYourname.setError("Your name required !");
                    return;
                }
                else if(TextUtils.isEmpty(email)){
                    edtEmail.setError("Email required !");
                    return;
                }
                else if(TextUtils.isEmpty(type)) {
                    edtType.setError("Type required !");
                    return;
                }
                else if(TextUtils.isEmpty(type2)) {
                    edtType2.setError("Type 2 required !");
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email,type).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(yourname,email,type,0,0,0);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    Toast.makeText(MainRegister.this, "Register successfull !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                }
                            });
                        }else {
                            Toast.makeText(MainRegister.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
    public void hideSoftKeyboard(){
        try {
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

    }
}
