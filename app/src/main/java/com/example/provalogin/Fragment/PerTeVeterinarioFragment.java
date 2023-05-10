package com.example.provalogin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.provalogin.Adapter.SegnalazioniAdapter;
import com.example.provalogin.R;

public class PerTeVeterinarioFragment extends Fragment {

    RecyclerView recyclerView;
    SegnalazioniAdapter segnalazioniAdapter;
    com.getbase.floatingactionbutton.FloatingActionButton floatingButtonNuovaSegnalazione;
    Fragment selectedFragment=null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_per_te_veterinario, container, false);
        /*
        recyclerView = recyclerView.findViewById(R.id.recycler_view_veterinario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Segnalazioni> options =
                new FirebaseRecyclerOptions.Builder<Segnalazioni>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("segnalazioni"), Segnalazioni.class)
                        .build();

        segnalazioniAdapter = new SegnalazioniAdapter(options);
        recyclerView.setAdapter(segnalazioniAdapter);
    */

        //TextView txttesto = view.findViewById(R.id.txttesto);

        return inflater.inflate(R.layout.fragment_per_te_veterinario, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Bottone che mi permette di aggiungere manualmente un nuovo animale
        floatingButtonNuovaSegnalazione = view.findViewById(R.id.btn_nuova_segnalazione);
        floatingButtonNuovaSegnalazione.setVisibility(View.VISIBLE);
        floatingButtonNuovaSegnalazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container_ente, new NuovaSegnalazioneFragment()).commit();
                floatingButtonNuovaSegnalazione.setVisibility(View.INVISIBLE);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //segnalazioniAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //segnalazioniAdapter.stopListening();
    }
}