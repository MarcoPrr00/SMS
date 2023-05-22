package com.example.provalogin.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.provalogin.HomeActivity;
import com.example.provalogin.HomeEnteActivity;
import com.example.provalogin.HomeVeterinarioActivity;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;
import com.example.provalogin.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class NuovaSegnalazioneFragment extends Fragment {

    FirebaseAuth auth;
    DatabaseReference reference;
    String userid;
    FirebaseDatabase db;
    Utente utente;

    String id;

    EditText descrizione, posizione;
    Spinner tipologia;
    Button btnInviaSegnalazione;
    CheckBox cbVeterinario, cbEnte, cbUtenteTradizionale;



    public NuovaSegnalazioneFragment() {
        // Required empty public constructor

    }

    public NuovaSegnalazioneFragment(Utente utentetmp) {
        this.utente=utentetmp;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid();
        id = NewAnimal.generacodiceid();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference().child("Segnalazioni").child(id);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_nuova_segnalazione, container, false);



    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        descrizione=view.findViewById(R.id.txt_descrizione);
        posizione=view.findViewById(R.id.txt_posizione);
        tipologia=view.findViewById(R.id.spinner_tipologia);
        btnInviaSegnalazione=view.findViewById(R.id.btn_invia_nuova_segnalazione);
        cbEnte=view.findViewById(R.id.checkbox_ente);
        cbUtenteTradizionale=view.findViewById(R.id.checkbox_utentetradizionale);
        cbVeterinario=view.findViewById(R.id.checkbox_veterinario);


        btnInviaSegnalazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd =new ProgressDialog(view.getContext());
                pd.setMessage("Caricamento...");
                pd.show();
                String destinatarioEnte="no";
                String destinatarioVeterinario="no";
                String destinatarioUtente="no";
                if(cbVeterinario.isChecked()){
                    destinatarioVeterinario="si";
                }
                if(cbEnte.isChecked()){
                    destinatarioEnte="si";
                }
                if(cbUtenteTradizionale.isChecked()){
                    destinatarioUtente="si";
                }

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", id);
                hashMap.put("tipologiaSegnalazione", tipologia.getSelectedItem().toString());
                hashMap.put("descrizione", descrizione.getText().toString());
                hashMap.put("posizione", posizione.getText().toString());
                hashMap.put("destinatarioVeterionario", destinatarioVeterinario);
                hashMap.put("destinatarioEnte", destinatarioEnte);
                hashMap.put("destinatarioUtente", destinatarioUtente);
                hashMap.put("idMittente",userid);
                hashMap.put("presoInCarico","no");
                hashMap.put("idPresoInCarico", "no");

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                                switch (utente.TipoUtente){
                                    case "EntePubblico":
                                        startActivity(new Intent(view.getContext(), HomeEnteActivity.class));
                                        break;
                                    case "Utente Amico":
                                        startActivity(new Intent(view.getContext(), HomeActivity.class));
                                        break;
                                    case "Veterinario":
                                        startActivity(new Intent(view.getContext(), HomeVeterinarioActivity.class));
                                        break;
                                }
                    }
                });
                pd.dismiss();
            }
        });
    }


}