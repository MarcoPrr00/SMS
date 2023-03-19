package com.example.provalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView txt_signup;

    FirebaseAuth auth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.tipologia);
//create a list of items for the spinner.
        String[] items = new String[]{"Utente Amico", "Veterinario", "EntePubblico"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        txt_signup=findViewById(R.id.txt_signup);

        auth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Caricamento");
                pd.show();

                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                if (TextUtils.isEmpty(strEmail)) {
                    email.setError("Email Richiesta!!");
                    System.out.println("sono nel primo if");
                } else if (TextUtils.isEmpty(strPassword)) {
                    password.setError("Password Richiesta!!");
                    System.out.println("sono nel secondo if");
                } else if (password.length() < 6) {
                    password.setError("Password minimo 6 caratteri!!");
                    System.out.println("sono nel terzo if");
                } else {
                    auth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(auth.getCurrentUser().getUid());
                                pd.dismiss();
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            }else{
                                Toast.makeText(LoginActivity.this, "Autenticazione Fallita!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
            }
        });

    }
}