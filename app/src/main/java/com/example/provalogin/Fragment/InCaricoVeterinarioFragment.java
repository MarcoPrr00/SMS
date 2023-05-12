package com.example.provalogin.Fragment;

import android.os.Bundle;

import com.example.provalogin.Adapter.SegnalazioniAdapter;
import com.example.provalogin.Adapter.UtenteAdapter;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Segnalazioni;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.provalogin.R;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class  InCaricoVeterinarioFragment extends Fragment {

    private RecyclerView recyclerView;
    //ANIMALE ADAPTER
    private List<Animal> mUtente;
    private UtenteAdapter adapter;
    DatabaseReference dbUtente;

    //SEGNALAZIONI ADAPTER
    private SegnalazioniAdapter segnalazioniAdapter;
    com.getbase.floatingactionbutton.FloatingActionButton floatingButtonNuovaSegnalazione;
    private List<Segnalazioni> mSegnalazioni;
    DatabaseReference db;


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

        View view = inflater.inflate(R.layout.fragment_in_carico_veterinario, container, false);
        //LOGICA ANIMALE ADAPTER
        /*
        recyclerView = view.findViewById(R.id.recycler_view_incarico);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUtente = new ArrayList<>();
        adapter = new UtenteAdapter(this.getContext(), mUtente);
        recyclerView.setAdapter(adapter);
        dbUtente=FirebaseDatabase.getInstance().getReference("Animals");
        dbUtente.addValueEventListener(valueEventListenerAnimale);*/

        //LOGICA SEGNALAZIONI ADAPTER
        recyclerView = view.findViewById(R.id.recycler_view_incarico);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mSegnalazioni = new ArrayList<>();
        segnalazioniAdapter = new SegnalazioniAdapter(this.getContext(), mSegnalazioni);

        recyclerView.setAdapter(segnalazioniAdapter);

        db= FirebaseDatabase.getInstance().getReference("Segnalazioni");
        db.addValueEventListener(valueEventListener);

        // Inflate the layout for this fragment
        return view;
    }

    private void searchUsers(String s){
        Query query = dbUtente.orderByChild("Nome")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(valueEventListenerAnimale);
    }

    //lettura utenti
/*
    private void readUsers (){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_bar.getText().toString().equals("")){
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);
                        mUsers.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
*/
    ValueEventListener valueEventListenerAnimale = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mUtente.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Animal artist = snapshot.getValue(Animal.class);
                    mUtente.add(artist);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



}

