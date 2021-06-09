package com.example.statusfirebaseandroidnguyenvantrung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainSignIn  extends AppCompatActivity {

    private Button btnSignin;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase instance;// lay ve instance
    private DatabaseReference db;
    private TextInputLayout edtpassword,edtmail;
    private TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_main);


        edtmail=(TextInputLayout)findViewById(R.id.edtmail);
        edtpassword=(TextInputLayout) findViewById(R.id.edtPassword);
        btnSignin=findViewById(R.id.btnSign);
        tvRegister=findViewById(R.id.tvRegister);
        firebaseAuth=FirebaseAuth.getInstance();

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainRegister.class));
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance=FirebaseDatabase.getInstance();
                db=instance.getReference("User");
                String email=edtmail.getEditText().getText().toString().trim();
                String pw=edtpassword.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    edtmail.setError("Email required !");
                    return;
                }
                if(TextUtils.isEmpty(pw)) {
                    edtpassword.setError("Password required !");
                    return;
                }
                if(pw.length()<6) {
                    edtpassword.setError("Password must be >= 6 characters !");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainSignIn.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainFace.class));
                        }else {
                            Toast.makeText(MainSignIn.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
