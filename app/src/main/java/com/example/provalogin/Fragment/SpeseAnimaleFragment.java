package com.example.provalogin.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.provalogin.Adapter.SegnalazioniAdapter;
import com.example.provalogin.Adapter.SpeseAdapter;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.Model.Spesa;
import com.example.provalogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SpeseAnimaleFragment extends Fragment {


    Button aggiungiSpesa;
    AlertDialog dialog;
    LinearLayout layout;
    DatabaseReference reference;

    RecyclerView recyclerView;
    private SpeseAdapter speseAdapter;
    private List<Spesa> mSpese;

    Animal animale;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mSpese.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Spesa spesa = snapshot.getValue(Spesa.class);
                    mSpese.add(spesa);
                }
                speseAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

        public SpeseAnimaleFragment(Animal animal){
        this.animale=animal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_spese_animale, container, false);


        aggiungiSpesa = view.findViewById(R.id.aggiungiSpesa);
        //layout = view.findViewById(R.id.spese_container);

        //Logica Recycler
        recyclerView = view.findViewById(R.id.recycler_view_spese_animali);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSpese = new ArrayList<>();
        speseAdapter = new SpeseAdapter(this.getContext(), mSpese);
        recyclerView.setAdapter(speseAdapter);
        Query db= FirebaseDatabase.getInstance().getReference("Spese").orderByChild("idAnimale").equalTo(animale.id);
        db.addValueEventListener(valueEventListener);

        buildDialog();
        aggiungiSpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        return view;
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog,null);
        EditText spesa = view.findViewById(R.id.spesaEdit);
        EditText prezzo = view.findViewById(R.id.prezzoEdit);
        builder.setView(view);
        builder.setTitle("Inserisci Spesa")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = NewAnimal.generacodiceid();
                        Spesa holder = new Spesa();
                        holder.spesa = spesa.getText().toString();
                        holder.prezzo = prezzo.getText().toString()+"€";
                        holder.id = id;
                        holder.idAnimale=animale.id;
                        holder.idPadrone=animale.padrone;
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
                        reference = database.getReference().child("Spese").child(id);
                        reference.setValue(holder);

                        //addCard(spesa.getText().toString(), holder.prezzo);
                    }
                })
                .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog = builder.create();
    }

    private void addCard(String spesa, String prezzo) {
        View view = getLayoutInflater().inflate(R.layout.spesa,null);

        TextView nameView = view.findViewById(R.id.tipoSpesa);
        TextView prezzoSpesa = view.findViewById(R.id.PrezzoSpesa);
        Button delete = view.findViewById(R.id.cancellaSpesa);

        nameView.setText(spesa);
        prezzoSpesa.setText(prezzo);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
            }
        });

        layout.addView(view);

    }
}