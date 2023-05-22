package com.example.provalogin.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.provalogin.HomeActivity;
import com.example.provalogin.HomeEnteActivity;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NewAnimal extends Fragment {


    public static ImageView immaginedacaricare;
    FloatingActionButton nuovaimmagine;
    EditText nomeAnimale, specie, padrone, preferenzaCibo, statoSalute, eta, chip, sesso, sterilizzazione;
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
        statoSalute = view.findViewById(R.id.salute_animale);
        sesso = view.findViewById(R.id.sesso_animale);
        specie = view.findViewById(R.id.specie_animale);
        sterilizzazione = view.findViewById(R.id.sterilizzazione_animale);


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
                String sessoa = sesso.getText().toString();
                String specieanimale = specie.getText().toString();
                String sterilizzazioneanimale = sterilizzazione.getText().toString();
                String saluteanimale = statoSalute.getText().toString();

                if(iscorrect(chipanimale)){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("nomeAnimale", nomeanimale);
                    hashMap.put("eta", etaanimale);
                    hashMap.put("chip", chipanimale);
                    hashMap.put("id",id);
                    hashMap.put("padrone",userid);
                    hashMap.put("preferenzaCibo", preferenzacibo);
                    hashMap.put("sesso", sessoa);
                    hashMap.put("specie",specieanimale);
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


   private Boolean iscorrect(String chipanimale){

       if (!chipanimale.isEmpty() && (chipanimale.length() > 12 || chipanimale.length() < 12)) {
           chip.requestFocus();
           chip.setError(getResources().getString(R.string.chip));
           Toast.makeText(getContext(), getResources().getString(R.string.chip), Toast.LENGTH_SHORT).show();
           return false;
       } else {
           chip.setError(null);
           return true;
       }




       
    }
}//fineclass