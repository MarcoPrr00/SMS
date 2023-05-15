package com.example.provalogin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.provalogin.Adapter.AnimalAdapter;
import com.example.provalogin.HomeActivity;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.R;
import com.example.provalogin.Recycler.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    RecyclerView recyclerView;
    TextView newanimal;
    private AnimalAdapter animalAdapter;
    private List<Animal> listAnimal;
    DatabaseReference db;
    FloatingActionButton inserire;

    com.getbase.floatingactionbutton.FloatingActionButton nuovoanimaleqrcode;
    com.getbase.floatingactionbutton.FloatingActionButton nuovoanimalebluetooth;
    com.getbase.floatingactionbutton.FloatingActionButton nuovoanimalemanualmente;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            listAnimal.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Animal animali = snapshot.getValue(Animal.class);
                    listAnimal.add(animali);
                }
                animalAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume(){
        super.onResume();

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.imieianimali));

        recyclerView =(RecyclerView)getActivity().findViewById(R.id.mieianimali_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Fare collegamento con db


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        recyclerView = view.findViewById(R.id.mieianimali_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        listAnimal = new ArrayList<>();
        animalAdapter = new AnimalAdapter(this.getContext(), listAnimal);

        recyclerView.setAdapter(animalAdapter);

        db= FirebaseDatabase.getInstance().getReference("Animals");
        db.addValueEventListener(valueEventListener);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Bottone che mi permette di aggiungere manualmente un nuovo animale
        nuovoanimalemanualmente = view.findViewById(R.id.nuovomanuale);
        nuovoanimalemanualmente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*   getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.newanimal, new NewAnimal()).commit();
                nuovoanimalemanualmente.setVisibility(View.INVISIBLE);*/


               Fragment fragment = new NewAnimal();
                FragmentTransaction transaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.container, fragment).commit();
            }
        });




        //CLICK ITEM RECYCLERVIEW
      /*  recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        rendiInvisibileView();
                        Animal tmp = listAnimal.get(position);
                      /*  getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new DettagliAnimale(tmp)).commit();*/

                  /*  }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );




*/



    }
   /* private void rendiVisibileView(){
        recyclerView.setVisibility(View.VISIBLE);
        inserire.setVisibility(View.VISIBLE);

    }

    private void rendiInvisibileView(){
        recyclerView.setVisibility(View.INVISIBLE);
        inserire.setVisibility(View.INVISIBLE);
    }*/
    @Override
    public void onStart() {
        super.onStart();

    }

    //if the adapter doesn't sop listening, it will do it for the whole time
    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }
}