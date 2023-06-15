package com.example.provalogin.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.provalogin.Adapter.SegnalazioniAdapter;
import com.example.provalogin.HomeActivity;
import com.example.provalogin.LoginActivity;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private SegnalazioniAdapter segnalazioniAdapter;
    FloatingActionButton floatingButtonNuovaSegnalazione;
    //Fragment selectedFragment=null;
    private List<Segnalazioni> mSegnalazioni;
    FirebaseDatabase db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Utente utente;

    public HomeFragment() {
        // Required empty public constructor
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mSegnalazioni.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Segnalazioni segnalazioni = snapshot.getValue(Segnalazioni.class);
                    mSegnalazioni.add(segnalazioni);
                }
                segnalazioniAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_per_te_veterinario, container, false);


        recyclerView = view.findViewById(R.id.recycler_view_veterinario);
        floatingButtonNuovaSegnalazione = view.findViewById(R.id.btn_nuova_segnalazione);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rendiVisibileView();

        mSegnalazioni = new ArrayList<>();
        segnalazioniAdapter = new SegnalazioniAdapter(this.getContext(), mSegnalazioni);

        recyclerView.setAdapter(segnalazioniAdapter);

        db= FirebaseDatabase.getInstance();


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        Query queryUtente = db.getReference("Users").orderByChild("Id").equalTo(auth.getCurrentUser().getUid());
        queryUtente.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot tmpsnapshot : snapshot.getChildren()) {
                        utente = tmpsnapshot.getValue(Utente.class);

                    }
                }
                Query query = db.getReference("Segnalazioni");
                switch (utente.TipoUtente){
                    case "EntePubblico":
                        query = db.getReference("Segnalazioni").orderByChild("destinatarioEnte").equalTo("si");
                    case "Utente Amico":
                        query = db.getReference("Segnalazioni").orderByChild("destinatarioUtente").equalTo("si");
                    case "Veterinario":
                        query = db.getReference("Segnalazioni").orderByChild("destinatarioVeterionario").equalTo("si");
                }
                query.addValueEventListener(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }



    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rendiVisibileView();

        //BOTTONE NUOVA SEGNALAZIONE
        floatingButtonNuovaSegnalazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rendiInvisibileView();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_per_te, new NuovaSegnalazioneFragment()).commit();


            }
        });

    }



    //click item recycler
    private void rendiVisibileView(){
        recyclerView.setVisibility(View.VISIBLE);
        floatingButtonNuovaSegnalazione.setVisibility(View.VISIBLE);
    }

    private void rendiInvisibileView(){
        recyclerView.setVisibility(View.INVISIBLE);
        floatingButtonNuovaSegnalazione.setVisibility(View.INVISIBLE);
    }




    void logout(){
            new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("ELIMINAZIONE CURA").setMessage("Sei sicuro di voler eliminare?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

    }





}