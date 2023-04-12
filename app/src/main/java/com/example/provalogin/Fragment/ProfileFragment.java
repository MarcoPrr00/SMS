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

import com.example.provalogin.HomeActivity;
import com.example.provalogin.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    RecyclerView recyclerView;
    TextView newanimal;
    //PetsAdapter adapter;
    com.getbase.floatingactionbutton.FloatingActionButton nuovoanimaleqrcode;
    com.getbase.floatingactionbutton.FloatingActionButton nuovoanimalebluetooth;
    com.getbase.floatingactionbutton.FloatingActionButton nuovoanimalemanualmente;


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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Bottone che mi permette di aggiungere manualmente un nuovo animale
        nuovoanimalemanualmente = view.findViewById(R.id.nuovomanuale);
        nuovoanimalemanualmente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new NewAnimal();
                FragmentTransaction transaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.container, fragment).commit();
            }
        });
    }
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