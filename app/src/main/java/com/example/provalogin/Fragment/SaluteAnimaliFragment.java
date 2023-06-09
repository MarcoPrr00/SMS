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

import com.example.provalogin.Adapter.CureAdapter;
import com.example.provalogin.Adapter.SpeseAdapter;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Cure;
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


public class SaluteAnimaliFragment extends Fragment {


    Animal animal;

    Button aggiungiCura;
    AlertDialog dialog;
    DatabaseReference reference;

    RecyclerView recyclerView;
    private CureAdapter cureAdapter;
    private List<Cure> mCure;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mCure.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cure cura = snapshot.getValue(Cure.class);
                    mCure.add(cura);
                }
                cureAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    public SaluteAnimaliFragment(Animal animale) {
        this.animal = animale;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_salute_animali, container, false);


        aggiungiCura = view.findViewById(R.id.aggiungiCura);

        recyclerView = view.findViewById(R.id.recycler_view_cure_animali);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCure = new ArrayList<>();
        cureAdapter = new CureAdapter(this.getContext(),mCure);
        recyclerView.setAdapter(cureAdapter);
        Query db= FirebaseDatabase.getInstance().getReference("Cure").orderByChild("idAnimale").equalTo(animal.id);
        db.addValueEventListener(valueEventListener);

        buildDialog();
        aggiungiCura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        return view;
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_cura,null);
        EditText cura = view.findViewById(R.id.cura_edit);
        EditText prezzo = view.findViewById(R.id.prezzo_edit);
        builder.setView(view);
        builder.setTitle("Inserisci Cura")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = NewAnimal.generacodiceid();
                        Cure holder = new Cure();
                        holder.cura = cura.getText().toString();
                        holder.prezzo = prezzo.getText().toString()+"€";
                        holder.id = id;
                        holder.idAnimale = animal.id;
                        holder.idPadrone = animal.padrone;
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
                        reference = database.getReference().child("Cure").child(id);
                        reference.setValue(holder);
                    }
                })
                .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog = builder.create();

    }
}