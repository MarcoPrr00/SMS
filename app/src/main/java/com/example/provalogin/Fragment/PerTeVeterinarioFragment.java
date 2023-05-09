package com.example.provalogin.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.provalogin.Adapter.SegnalazioniAdapter;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class PerTeVeterinarioFragment extends Fragment {

    RecyclerView recyclerView;
    SegnalazioniAdapter segnalazioniAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView = recyclerView.findViewById(R.id.recycler_view_veterinario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Segnalazioni> options =
                new FirebaseRecyclerOptions.Builder<Segnalazioni>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("segnalazioni"), Segnalazioni.class)
                        .build();

        segnalazioniAdapter = new SegnalazioniAdapter(options);
        recyclerView.setAdapter(segnalazioniAdapter);

        return inflater.inflate(R.layout.fragment_per_te_veterinario, container, false);
    }



    @Override
    public void onStart() {
        super.onStart();
        segnalazioniAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        segnalazioniAdapter.stopListening();
    }
}