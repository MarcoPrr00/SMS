package com.example.provalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TintableCheckedTextView;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    EditText username,fullname, email, password;
    Button register;
    TextView txt_login;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.username);
        fullname=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        txt_login=findViewById(R.id.txt_login);

        auth=FirebaseAuth.getInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd=new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Caricamento...");
                pd.show();

                String strUsername=username.getText().toString();
                String strFullname=fullname.getText().toString();
                String strEmail=email.getText().toString();
                String strPassword=password.getText().toString();

                if(TextUtils.isEmpty(strEmail)){
                    email.setError("Email Richiesta!!");
                    System.out.println("sono nel primo if");
                }
                else if(TextUtils.isEmpty(strPassword)){
                    password.setError("Password Richiesta!!");
                    System.out.println("sono nel secondo if");
                }
                else if(TextUtils.isEmpty(strUsername)){
                    username.setError("Cognome Richiesto!!");
                    System.out.println("sono nel secondo if");
                }
                else if(TextUtils.isEmpty(strFullname)){
                    password.setError("Nome Richiesto!!");
                    System.out.println("sono nel secondo if");
                }
                else if(password.length()<6){
                    password.setError("Password minimo 6 caratteri!!");
                    System.out.println("sono nel terzo if");
                }else {
                    register(strUsername,strFullname,strEmail,strPassword);
                }
            }
        });
    }

    private void register(String musername, String mfullname, String memail, String mpassword){
        auth.createUserWithEmailAndPassword(memail,mpassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userid=firebaseUser.getUid();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("Cognome",musername);
                    hashMap.put("Nome",mfullname);
                    hashMap.put("imgUrl","https://firebasestorage.googleapis.com/v0/b/provalogin-65cb5.appspot.com/o/avatar.png?alt=media&token=e6a038aa-942e-4c21-b1ff-d8c9dd33b0fc");


                    //REALTIME DATABASE
                  FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
                    reference = database.getReference().child("Users").child(userid);
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pd.dismiss();
                                Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    }); /* */

                    //CLOUDSTORAGE DATABASE
                    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
                    firebaseFirestore.collection("Users").document(userid).set(hashMap);
                    pd.dismiss();
                    Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                }else {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Non puoi registarti con questa email o password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}