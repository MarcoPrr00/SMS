package com.example.provalogin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.provalogin.Adapter.AnimalAdapter;


import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.play.core.integrity.p;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;

    private AnimalAdapter animalAdapter;
    FloatingActionButton inserire;

    com.getbase.floatingactionbutton.FloatingActionButton nuovoanimaleqrcode;
    com.getbase.floatingactionbutton.FloatingActionButton nuovoanimalebluetooth;
    com.getbase.floatingactionbutton.FloatingActionButton nuovoanimalemanualmente;

    private List<Animal> mAnimal;
    Query db;
    FirebaseAuth auth;

    DatabaseReference reference;
    String userid;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        auth= FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid();
        String id = NewAnimal.generacodiceid();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference().child("Animals").child(id);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mAnimal.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Animal animal = snapshot.getValue(Animal.class);
                    mAnimal.add(animal);
                }
                animalAdapter.notifyDataSetChanged();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        recyclerView = view.findViewById(R.id.mieianimali_recyclerview);

        nuovoanimalemanualmente = view.findViewById(R.id.nuovomanuale);
        //inserire = view.findViewById(R.id.inserire);
        //String current = Animal.padrone;



            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // rendiVisibileView();

            mAnimal = new ArrayList<>();
            animalAdapter = new AnimalAdapter(getContext(), mAnimal);

//        if(current.equals(auth.getUid())) {

            recyclerView.setAdapter(animalAdapter);

       // }
            db = FirebaseDatabase.getInstance().getReference("Animals").orderByChild("padrone").equalTo(userid);
            db.addValueEventListener(valueEventListener);



        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //rendiVisibileView();




        //BOTTONE NUOVA SEGNALAZIONE
        nuovoanimalemanualmente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rendiInvisibileView();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_animali, new NewAnimal()).commit();


            }
        });

        //CLICK ITEM RECYCLERVIEW
        /*recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        rendiInvisibileView();
                        Animal tmp = mAnimal.get(position);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_animali, new DettagliAnimale(tmp)).commit();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/

    }


    private void rendiInvisibileView(){
        recyclerView.setVisibility(View.INVISIBLE);

    }
}
