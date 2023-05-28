package com.example.provalogin.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.provalogin.HomeActivity;
import com.example.provalogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

import java.util.Random;

public class NewAnimal extends Fragment {


    public static ImageView immaginedacaricare;
    FloatingActionButton nuovaimmagine;
    Spinner specieanimale, sterilizzazioneanima, sessoanim, statosaluteanim;
    EditText nomeAnimale, padrone, preferenzaCibo, eta, chip;

    String imgPosition = "gs://provalogin-65cb5.appspot.com/logo.png";
    FirebaseAuth auth;
    DatabaseReference reference;
    String userid, id;

    public NewAnimal(){

    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        auth= FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid(); //padrone
        id = NewAnimal.generacodiceid(); //animale
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference().child("Animals").child(id);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_newanimal, container, false);
       immaginedacaricare = view.findViewById(R.id.img);
       nuovaimmagine = view.findViewById(R.id.nuovaimmagine);

       return view;
   }
   @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);

       //reference
        nomeAnimale = view.findViewById(R.id.nome_animale);
        chip = view.findViewById(R.id.chip_animale);
        eta = view.findViewById(R.id.eta_animale);
        //padrone_animale = view.findViewById(R.id.padrone_animale);
        preferenzaCibo = view.findViewById(R.id.preferenza_animale);
        statosaluteanim = view.findViewById(R.id.salute_animale);
        sessoanim = view.findViewById(R.id.sesso_animale);
        specieanimale = view.findViewById(R.id.specie_animale);
        sterilizzazioneanima = view.findViewById(R.id.sterilizzazione_animale);
        // Set up ArrayAdapter for each spinner
       ArrayAdapter<CharSequence> sessoAdapter = ArrayAdapter.createFromResource(
               requireContext(),
               R.array.sessoa,
               android.R.layout.simple_spinner_dropdown_item
       );
       sessoanim.setAdapter(sessoAdapter);

       // Set up ArrayAdapter for each spinner
       ArrayAdapter<CharSequence> sterilizzazioneAdapter = ArrayAdapter.createFromResource(
               requireContext(),
               R.array.sterilizzazioneanimale,
               android.R.layout.simple_spinner_dropdown_item
       );
       sterilizzazioneanima.setAdapter(sterilizzazioneAdapter);
       // Set up ArrayAdapter for each spinner
       ArrayAdapter<CharSequence> specieAdapter = ArrayAdapter.createFromResource(
               requireContext(),
               R.array.speciea,
               android.R.layout.simple_spinner_dropdown_item
       );
       specieanimale.setAdapter(specieAdapter);
       // Set up ArrayAdapter for each spinner
       ArrayAdapter<CharSequence> saluteAdapter = ArrayAdapter.createFromResource(
               requireContext(),
               R.array.statosalute,
               android.R.layout.simple_spinner_dropdown_item
       );
       statosaluteanim.setAdapter(saluteAdapter);


    /*Definire il processo per inserire una immagine animale da telefono*/



    /*Cliccando il bottone di registrazione andiamo a inserire tutti i dati raccolti nel database.
    bisogna però prima controllare che tutto sia corretto*/
        final Button caricamentoanimale = view.findViewById(R.id.caricamentoanimale);
            caricamentoanimale.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){


                String nomeanimale = nomeAnimale.getText().toString();
                String etaanimale = eta.getText().toString();
                String chipanimale = chip.getText().toString();
                //String padrone = padrone_animale.getText().toString();
                String preferenzacibo = preferenzaCibo.getText().toString();
                String sessoa = sessoanim.getSelectedItem().toString();
                String specie = specieanimale.getSelectedItem().toString();
                String sterilizzazioneanimale = sterilizzazioneanima.getSelectedItem().toString();
                String saluteanimale = statosaluteanim.getSelectedItem().toString();

                if(isCorrect(chipanimale, nomeanimale, etaanimale, preferenzacibo)){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("nomeAnimale", nomeanimale);
                    hashMap.put("eta", etaanimale);
                    hashMap.put("chip", chipanimale);
                    hashMap.put("id",id);
                    hashMap.put("padrone",userid);
                    hashMap.put("preferenzaCibo", preferenzacibo);
                    hashMap.put("sesso", sessoa);
                    hashMap.put("specie",specie);
                    hashMap.put("sterilizzazione",sterilizzazioneanimale);
                    hashMap.put("statoSalute",saluteanimale);

                    Toast.makeText(getContext(), "caricamentoeffettuato", Toast.LENGTH_SHORT).show();


                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(view.getContext(), HomeActivity.class));
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "errore", Toast.LENGTH_SHORT).show();
                }



                //mettere dialog se dati sono corretti


               // caricamentoFirebase(nome, eta, chip, padrone, preferenza, sesso, specie, sterilizzazione, salute);



            }
        });


    }





    public static String generacodiceid(){
        final String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder ris = new StringBuilder();
        int i = 10;
        while (i > 0){
            Random random = new Random();
            ris.append(caratteri.charAt(random.nextInt(caratteri.length())));
            i--;
        }
        return ris.toString();
    }


    private boolean isCorrect(String chipanimale, String nomea, String etaa, String preferenza) {
        boolean isValid = true;

        if (!chipanimale.isEmpty() && (chipanimale.length() > 12 || chipanimale.length() < 12)) {
            chip.requestFocus();
            chip.setError(getResources().getString(R.string.chiperror));
            Toast.makeText(getContext(), getResources().getString(R.string.chip), Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            chip.setError(null);
        }

        if (nomea.isEmpty()) {
            nomeAnimale.requestFocus();
            nomeAnimale.setError(getResources().getString(R.string.nomeanimalemancante));
            //Toast.makeText(getContext(), getResources().getString(R.string.nomeanimalemancante), Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            nomeAnimale.setError(null);
        }
        if (etaa.isEmpty()) {
            eta.requestFocus();
            eta.setError(getResources().getString(R.string.etamancante));
           // Toast.makeText(getContext(), getResources().getString(R.string.nomeanimalemancante), Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            eta.setError(null);
        }
        if (preferenza.isEmpty()) {
           preferenzaCibo.requestFocus();
            preferenzaCibo.setError(getResources().getString(R.string.cibomancante));
            // Toast.makeText(getContext(), getResources().getString(R.string.nomeanimalemancante), Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            preferenzaCibo.setError(null);
        }

        return isValid;
    }

}//fineclass