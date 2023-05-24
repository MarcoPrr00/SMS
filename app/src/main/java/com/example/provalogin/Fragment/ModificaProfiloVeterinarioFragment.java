package com.example.provalogin.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ModificaProfiloVeterinarioFragment extends Fragment {

    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Utente utente;



    public ModificaProfiloVeterinarioFragment(Utente u){
        this.utente = u;
    }

    EditText editNome,editCognome,editEmail,editPassword;
    Button salvaButton;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    String nomeUser,cognomeUser,emailUser,passwordUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onPause() {
        super.onPause();

        ProfileVeterinarioFragment profiloVeterinarioFragment = (ProfileVeterinarioFragment) getParentFragmentManager().findFragmentByTag("fragment_container_profilo_veterinario");
        if (profiloVeterinarioFragment != null) {
            profiloVeterinarioFragment.setModificaProfiloVisible(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modifica_profilo_veterinario, container, false);
        editNome = view.findViewById(R.id.editName);
        editCognome = view.findViewById(R.id.editCognome);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        salvaButton = view.findViewById(R.id.saveButton);
        showUserData();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                // Azioni da eseguire quando viene premuto il pulsante "Indietro" nel Fragment
                // Esempio: torna indietro alla schermata precedente del Fragment

                requireActivity().onBackPressed();
                return true; // Consuma l'evento di pressione del pulsante "Indietro"
            }
            return false; // L'evento di pressione del pulsante "Indietro" viene propagato alle altre viste
        });


        salvaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChanged()){
                    Toast.makeText(getContext(),"Modifica avvenuta con successo", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getContext(),"Nessuna modifica trovata", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    public boolean isChanged(){
        boolean tmp = false;
        if(!nomeUser.equals(editNome.getText().toString())){

            reference.child("Users").child(userId).child("Nome").setValue(editNome.getText().toString());
            nomeUser = editNome.getText().toString();
            tmp = true;
        }
        if(!cognomeUser.equals(editCognome.getText().toString())){
            reference.child("Users").child(userId).child("Cognome").setValue(editCognome.getText().toString());
            cognomeUser = editCognome.getText().toString();
            tmp = true;
        }
        if(!emailUser.equals(editEmail.getText().toString())){
            reference.child("Users").child(userId).child("Email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            tmp = true;
        }

        if(!passwordUser.equals(editPassword.getText().toString())){
            reference.child("Users").child(userId).child("Password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            tmp = true;
        }
        return tmp;
    }




    public void showUserData() {

        nomeUser = utente.Nome;
        emailUser = utente.Email;
        cognomeUser = utente.Cognome;
        passwordUser = utente.Password;
        editNome.setText(nomeUser);
        editEmail.setText(emailUser);
        editCognome.setText(cognomeUser);
        editPassword.setText(passwordUser);
    }
}