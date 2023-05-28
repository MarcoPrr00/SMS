package com.example.provalogin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.provalogin.Adapter.PrefAdapter;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Follow;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.R;

import com.example.provalogin.Recycler.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FavorietesFragment extends Fragment {
    private RecyclerView elencopref;

    TextView nomeanimale;
    ImageView immagine;
    Follow seguiti;
    // Button btn_follow;

    private PrefAdapter prefAdapter;
    private List<Follow> listAnimal;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    //FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorietes, container, false);
        elencopref = view.findViewById(R.id.elencopref);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        elencopref.setLayoutManager(layoutManager);
        //immagine = view.findViewById(R.id.immagine);
        //btn_follow = view.findViewById(R.id.btn_follow);
        //elencopref.setHasFixedSize(true);
        //elencopref.setLayoutManager(new LinearLayoutManager(getContext()));

        listAnimal = new ArrayList<>();
        prefAdapter = new PrefAdapter(this.getContext(), listAnimal);

       elencopref.setAdapter(prefAdapter);


        //db = FirebaseDatabase.getInstance().getReference("Follow");

       // db.addValueEventListener(valueEventListener);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAnimal.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Follow f = snapshot.getValue(Follow.class);
                    listAnimal.add(f);
                }
                prefAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Errore nel recupero degli animali preferiti", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }





    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //CLICK ITEM RECYCLERVIEW
        elencopref.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), elencopref ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Follow tmp = listAnimal.get(position);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new DettagliAnimale(tmp)).addToBackStack(null).commit();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }



}