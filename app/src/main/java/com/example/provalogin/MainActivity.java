package com.example.provalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    ProgressBar processBar;
    FirebaseAuth fAuth;
    TextView mCreateBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail=findViewById(R.id.emailLogin);
        mPassword=findViewById(R.id.passwordLogin);
        mLoginBtn=findViewById(R.id.BtnLogin);
        processBar=findViewById(R.id.progressBarLogin);
        fAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText().toString();
                String password=mPassword.getText().toString();
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email Richiesta!!");
                    System.out.println("sono nel primo if");
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password Richiesta!!");
                    System.out.println("sono nel secondo if");
                }
                if(password.length()<8){
                    mPassword.setError("Password minimo 8 caratteri!!");
                    System.out.println("sono nel terzo if");
                }
                processBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Effettuato", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent (getApplicationContext(),IndexActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this,"Errore!! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}