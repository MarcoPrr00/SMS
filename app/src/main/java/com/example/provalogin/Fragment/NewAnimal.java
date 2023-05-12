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
import android.widget.EditText;
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
    EditText nome_animale, specie_animale, sesso_animale, padrone_animale, preferenza_animale, salute_animale, sterilizzazione_animale, eta_animale, chip_animale;

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
        nome_animale = view.findViewById(R.id.nome_animale);
        chip_animale = view.findViewById(R.id.chip_animale);
        eta_animale = view.findViewById(R.id.eta_animale);
        padrone_animale = view.findViewById(R.id.padrone_animale);
        preferenza_animale = view.findViewById(R.id.preferenza_animale);
        salute_animale = view.findViewById(R.id.salute_animale);
        sesso_animale = view.findViewById(R.id.sesso_animale);
        specie_animale = view.findViewById(R.id.specie_animale);
        sterilizzazione_animale = view.findViewById(R.id.sterilizzazione_animale);

        //scelta del sesso dell'animale
      /* final String[] sessoa = getResources().getStringArray(R.array.sessoa);
       ArrayAdapter<String> sessoAdapter = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_scelta, sessoa);
       AutoCompleteTextView addGender = view.findViewById(R.id.sesso_animale);
       addGender.setAdapter(sessoAdapter);*/

       //scelta del boolean relativo alla sterilizzazione dell'animale
      /* final String[] sterilizzazione = getResources().getStringArray(R.array.sterilizzazioneanimale);
       ArrayAdapter<String> sterilizzazioneAdapter = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_scelta, sterilizzazione);
       AutoCompleteTextView addSterilization = view.findViewById(R.id.sterilizzazione_animale);
       addSterilization.setAdapter(sterilizzazioneAdapter);*/


    /*Definire il processo per inserire una immagine animale da telefono*/



    /*Cliccando il bottone di registrazione andiamo a inserire tutti i dati raccolti nel database.
    bisogna però prima controllare che tutto sia corretto*/
        final Button caricamentoanimale = view.findViewById(R.id.caricamentoanimale);
            caricamentoanimale.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                String nome = nome_animale.getText().toString();
                String eta = eta_animale.getText().toString();
                String chip = chip_animale.getText().toString();
                String padrone = padrone_animale.getText().toString();
                String preferenza = preferenza_animale.getText().toString();
                String sesso = sesso_animale.getText().toString();
                String specie = specie_animale.getText().toString();
                String sterilizzazione = sterilizzazione_animale.getText().toString();
                String salute = salute_animale.getText().toString();

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