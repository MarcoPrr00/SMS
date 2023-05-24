package com.example.provalogin.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.provalogin.Adapter.AnimalAdapter;
import com.example.provalogin.Adapter.PrefAdapter;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.R;
import com.example.provalogin.Recycler.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FavorietesFragment extends Fragment {
    private RecyclerView elencopref;

    TextView nomeanimale;
    ImageView immagine;
    // Button btn_follow;

    private PrefAdapter prefAdapter;
    private List<Animal> listAnimal;
    DatabaseReference db;
    EditText search_bar;
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

        db = FirebaseDatabase.getInstance().getReference("Follow");
        db.addValueEventListener(valueEventListener);




        return view;
    }





    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //CLICK ITEM RECYCLERVIEW
        elencopref.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), elencopref,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Animal tmp = listAnimal.get(position);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.fragment_profile_animal, new DettagliAnimale(tmp));

                        fragmentTransaction.commit();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            listAnimal.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Animal artist = snapshot.getValue(Animal.class);
                    listAnimal.add(artist);
                }
                prefAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


}