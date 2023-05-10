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

import com.example.provalogin.HomeEnteActivity;
import com.example.provalogin.R;
import com.example.provalogin.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class NuovaSegnalazioneFragment extends Fragment {

    FirebaseAuth auth;
    DatabaseReference reference;

    EditText descrizione, posizione;
    Spinner tipologia;
    Button btnInviaSegnalazione;
    CheckBox cbVeterinario, cbEnte, cbUtenteTradizionale;



    public NuovaSegnalazioneFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();
        String userid = auth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference().child("Segnalazioni").child(userid);

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

        String s = btnInviaSegnalazione.toString();

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
                hashMap.put("tipologiaSegnalazione", tipologia.getSelectedItem().toString());
                hashMap.put("descrizione", descrizione.getText().toString());
                hashMap.put("posizione", posizione.getText().toString());
                hashMap.put("destinatarioVeterionario", destinatarioVeterinario);
                hashMap.put("destinatarioEnte", destinatarioEnte);
                hashMap.put("destinatarioUtente", destinatarioUtente);
                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        startActivity(new Intent(view.getContext(), HomeEnteActivity.class));
                    }
                });
                pd.dismiss();
            }
        });
    }


}