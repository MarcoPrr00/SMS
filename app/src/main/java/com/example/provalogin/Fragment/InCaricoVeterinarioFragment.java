package com.example.provalogin.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.provalogin.Adapter.SegnalazioniAdapter;
import com.example.provalogin.Adapter.UtenteAdapter;
import com.example.provalogin.HomeActivity;
import com.example.provalogin.HomeEnteActivity;
import com.example.provalogin.HomeVeterinarioActivity;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Segnalazioni;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
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
    FirebaseAuth auth = FirebaseAuth.getInstance();


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
        Activity activity = getActivity();
        if (activity instanceof HomeVeterinarioActivity) {
            ((HomeVeterinarioActivity) activity).setCustomBackEnabled(true);
        } else if (activity instanceof HomeEnteActivity) {
            ((HomeEnteActivity) activity).setCustomBackEnabled(true);
        }


        //LOGICA SEGNALAZIONI ADAPTER
        recyclerView = view.findViewById(R.id.recycler_view_incarico);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mSegnalazioni = new ArrayList<>();
        segnalazioniAdapter = new SegnalazioniAdapter(this.getContext(), mSegnalazioni);

        recyclerView.setAdapter(segnalazioniAdapter);

        Query db= FirebaseDatabase.getInstance().getReference("Segnalazioni").orderByChild("idPresaInCarico").equalTo(auth.getCurrentUser().getUid());
        db.addValueEventListener(valueEventListener);

        // Inflate the layout for this fragment
        return view;
    }


    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage("Sei sicuro di voler effettuare il logout?");
        builder.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Effettua il logout da Firebase
                FirebaseAuth.getInstance().signOut();

                // Chiudi l'activity o esegui altre azioni di logout se necessario
                requireActivity().finish();
            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

