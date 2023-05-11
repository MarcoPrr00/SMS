package com.example.provalogin.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.provalogin.Model.Animal;
import com.example.provalogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Random;

public class NewAnimal extends Fragment {


    public static ImageView immaginedacaricare;
    FloatingActionButton nuovaimmagine;
    TextInputLayout text_nomeanimale;
    TextInputLayout text_specie;
    TextInputLayout text_sesso;
    TextInputLayout text_padrone;
    TextInputLayout text_preferenza;
    TextInputLayout text_salute;
    TextInputLayout text_sterilizzazione;
    TextInputLayout text_eta;
    TextInputLayout text_chip;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage storage;

    public NewAnimal(){

    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
        text_nomeanimale = view.findViewById(R.id.text_nomeanimale);
        text_chip = view.findViewById(R.id.text_chip);
        text_eta = view.findViewById(R.id.text_eta);
        text_padrone = view.findViewById(R.id.text_padrone);
        text_preferenza = view.findViewById(R.id.text_preferenza);
        text_salute = view.findViewById(R.id.text_salute);
        text_sesso = view.findViewById(R.id.text_sesso);
        text_specie = view.findViewById(R.id.text_specie);
        text_sterilizzazione = view.findViewById(R.id.text_sterilizzazione);

        //scelta del sesso dell'animale
       final String[] sessoa = getResources().getStringArray(R.array.sessoa);
       ArrayAdapter<String> sessoAdapter = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_scelta, sessoa);
       AutoCompleteTextView addGender = view.findViewById(R.id.sessoanimale);
       addGender.setAdapter(sessoAdapter);

       //scelta del boolean relativo alla sterilizzazione dell'animale
       final String[] sterilizzazione = getResources().getStringArray(R.array.sterilizzazioneanimale);
       ArrayAdapter<String> sterilizzazioneAdapter = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_scelta, sterilizzazione);
       AutoCompleteTextView addSterilization = view.findViewById(R.id.sterilizzazioneanimale);
       addSterilization.setAdapter(sterilizzazioneAdapter);


    /*Definire il processo per inserire una immagine animale da telefono*/



    /*Cliccando il bottone di registrazione andiamo a inserire tutti i dati raccolti nel database.
    bisogna però prima controllare che tutto sia corretto*/
        final Button caricamentoanimale = view.findViewById(R.id.caricamentoanimale);
            caricamentoanimale.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                String nome = text_nomeanimale.getEditText().getText().toString();
                String eta = text_eta.getEditText().getText().toString();
                String chip = text_chip.getEditText().getText().toString();
                String padrone = text_padrone.getEditText().getText().toString();
                String preferenza = text_preferenza.getEditText().getText().toString();
                String sesso = text_sesso.getEditText().getText().toString();
                String specie = text_specie.getEditText().getText().toString();
                String sterilizzazione = text_sterilizzazione.getEditText().getText().toString();
                String salute = text_salute.getEditText().getText().toString();

                //mettere dialog se dati sono corretti


                caricamentoFirebase(nome, eta, chip, padrone, preferenza, sesso, specie, sterilizzazione, salute);
                                    /*bottomNavigationView.getMenu().findItem(R.id.homeFragment).setEnabled(false);
                                    bottomNavigationView.getMenu().findItem(R.id.my_pet_nav_graph).setEnabled(false);*/


            }
        });


    }



    //caricamento dati animale su db firebase
    private void caricamentoFirebase(String nomeanimale, String etaanimale, String chipanimale, String padroneanimale,
                                  String preferenzacibo, String sessoa, String specieanimale, String sterilizzazioneanimale,
                                  String saluteanimale){
        String id = generacodiceid();
        //String userid=firebaseUser.getUid();
       // storage = FirebaseStorage.getInstance();
       // StorageReference caricamento = storage.getReference("Animals").child(id);
        //mettere immagine
        Animal animal = new Animal(id, nomeanimale, etaanimale, chipanimale, padroneanimale, preferenzacibo, sessoa, specieanimale, sterilizzazioneanimale, saluteanimale);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Animals");
        reference.child(id).setValue(animal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).popBackStack(
                        R.id.newanimal, true);


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


   // private Boolean iscorrect(String etaanimale, String sessoanimale, String specieanimale, String sterilizzazioneanimale){

   // }
}//fineclass