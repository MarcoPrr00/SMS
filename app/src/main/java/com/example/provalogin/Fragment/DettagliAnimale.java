package com.example.provalogin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.R;

public class DettagliAnimale extends Fragment {


   /* Animal animal;
    TextView nomeanimale, etaanimale, padrone, preferenza;
    Button
    ImageView imgAnimal;

    DettagliAnimale(){

    }

    DettagliAnimale(Animal s){
        this.animal = s;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dettagli_segnalazioni, container, false);

        tipoSegnalazione = view.findViewById(R.id.txt_dettagli_segnalazione_tipologia);
        descrizione = view.findViewById(R.id.txt_dettagli_segnalazione_descrizone);
        posizione = view.findViewById(R.id.txt_dettagli_segnalazione_posizione);
        cbVeteterinario = view.findViewById(R.id.dettagli_segnalazione_checkbox_veterinario);
        cbEnte = view.findViewById(R.id.dettagli_segnalazione_checkbox_ente);
        cbUtente = view.findViewById(R.id.dettagli_segnalazione_checkbox_utentetradizionale);
        imgSegnalzioni = view.findViewById(R.id.img_dettagli_segnalazioni);

        tipoSegnalazione.setText(segnalazioni.tipologiaSegnalazione);
        descrizione.setText(segnalazioni.descrizione);
        posizione.setText(segnalazioni.posizione);
        if(segnalazioni.destinatarioVeterionario.equals("si")){
            cbVeteterinario.setChecked(true);
        }
        if(segnalazioni.destinatarioEnte.equals("si")){
            cbEnte.setChecked(true);
        }
        if(segnalazioni.destinatarioUtente.equals("si")){
            cbUtente.setChecked(true);
        }

        Glide.with(getContext())
                .load(R.drawable.logo)
                .into(imgSegnalzioni);
        // Inflate the layout for this fragment
        return view;
    }*/
}
