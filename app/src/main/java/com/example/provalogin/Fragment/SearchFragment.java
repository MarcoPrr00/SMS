package com.example.provalogin.Fragment;

import android.os.Bundle;

import com.example.provalogin.Adapter.AnimalAdapter;
import com.example.provalogin.Adapter.UtenteAdapter;
import com.example.provalogin.Model.Animal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
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

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;

    private AnimalAdapter animalAdapter;
    private List<Animal> listAnimal;
    DatabaseReference db;
    EditText search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search_bar = view.findViewById(R.id.searchbar);



        listAnimal = new ArrayList<>();
        animalAdapter = new AnimalAdapter(this.getContext(), listAnimal);

        recyclerView.setAdapter(animalAdapter);

        db= FirebaseDatabase.getInstance().getReference("Animals");
        db.addValueEventListener(valueEventListener);

       search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void searchUsers(String s){
        Query query = db.orderByChild("Nome")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(valueEventListener);
    }

    //lettura utenti

    private void readUsers (){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_bar.getText().toString().equals("")){
                    listAnimal.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Animal animal = snapshot.getValue(Animal.class);
                        listAnimal.add(animal);
                    }
                    animalAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                animalAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}

