package com.example.provalogin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.provalogin.Adapter.SegnalazioniAdapter;
;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;
import com.example.provalogin.Recycler.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PerTeVeterinarioFragment extends Fragment {

    private RecyclerView recyclerView;
    private SegnalazioniAdapter segnalazioniAdapter;
    FloatingActionButton floatingButtonNuovaSegnalazione;
    //Fragment selectedFragment=null;
    private List<Segnalazioni> mSegnalazioni;
    FirebaseDatabase db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Utente utente;



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

                switch (utente.TipoUtente){
                    case "EntePubblico":
                        Query queryEnte = db.getReference("Segnalazioni").orderByChild("destinatarioEnte").equalTo("si");
                        queryEnte.addValueEventListener(valueEventListener);
                        break;
                    case "Utente Amico":
                        Query queryUtenteAmico = db.getReference("Segnalazioni").orderByChild("destinatarioUtente").equalTo("si");
                        queryUtenteAmico.addValueEventListener(valueEventListener);
                        break;
                    case "Veterinario":
                        Query queryVeterinario = db.getReference("Segnalazioni").orderByChild("destinatarioVeterionario").equalTo("si");
                        queryVeterinario.addValueEventListener(valueEventListener);
                        break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //BOTTONE NUOVA SEGNALAZIONE
        floatingButtonNuovaSegnalazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rendiInvisibileView();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NuovaSegnalazioneFragment(utente)).addToBackStack(null).commit();


            }
        });

        //CLICK ITEM RECYCLERVIEW
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //rendiInvisibileView();
                        Segnalazioni tmp = mSegnalazioni.get(position);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new DettagliSegnalazioniFragment(tmp,utente)).addToBackStack(null).commit();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    private void rendiVisibileView(){
        recyclerView.setVisibility(View.VISIBLE);
        floatingButtonNuovaSegnalazione.setVisibility(View.VISIBLE);
    }

    private void rendiInvisibileView(){
        recyclerView.setVisibility(View.INVISIBLE);
        floatingButtonNuovaSegnalazione.setVisibility(View.INVISIBLE);
    }


}