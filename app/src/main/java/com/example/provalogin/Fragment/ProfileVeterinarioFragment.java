package com.example.provalogin.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;
import com.example.provalogin.UpdateActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class  ProfileVeterinarioFragment extends Fragment {

    private Context context;
    private boolean isModificaProfiloVisible = true;

    public void setModificaProfiloVisible(boolean isVisible) {
        if (modificaProfilo != null) {
            if (isVisible) {
                modificaProfilo.setVisibility(View.VISIBLE);
            } else {
                modificaProfilo.setVisibility(View.GONE);
            }
        }
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }



    FirebaseAuth dbAuth = FirebaseAuth.getInstance();
    Query query;
    private List<Utente> nUser = new ArrayList<Utente>();
    ImageView profileImg;
    TextView profileNameVeterinario, profileEmailVeterinario, profileUsernameVeterinario,profilePasswordVeterinario;
    TextView titleName, titleUsername;

     Button modificaProfilo;
     FloatingActionButton caricaImagineProfilo;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            nUser.clear();

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Utente user = snapshot.getValue(Utente.class);
                    nUser.add(user);
                }
                showUserData();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();


        caricaImagineProfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("Utente", nUser.get(0));
                intent.putExtra("Posizione", "profilo");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_veterinario, container, false);
        profileNameVeterinario = view.findViewById(R.id.profileName);
        profileEmailVeterinario = view.findViewById(R.id.profileEmail);
        profileUsernameVeterinario = view.findViewById(R.id.profileUsername);
        profilePasswordVeterinario = view.findViewById(R.id.profilePassword);
        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);
        profileImg= view.findViewById(R.id.profileImg);
        modificaProfilo = view.findViewById(R.id.editButton);
        caricaImagineProfilo= view.findViewById(R.id.btn_nuava_foto);

        modificaProfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ModificaProfiloVeterinarioFragment(nUser.get(0))).addToBackStack(null).commit();
            }
        });


        String userId = dbAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
        query = database.getReference().child("Users").orderByChild("Id").equalTo(userId);
        query.addValueEventListener(valueEventListener);
        return view;
    }

    public void showUserData(){

        String nameUser = nUser.get(0).Nome;
        String emailUser = nUser.get(0).Email;
        String usernameUser = nUser.get(0).Cognome;
        String passwordUser = nUser.get(0).Password;

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(nUser.get(0).ImgUrl);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(profileImg);
            }
        });

        titleName.setText(nameUser+" "+usernameUser);
        titleUsername.setText(emailUser);
        profileNameVeterinario.setText(nameUser);
        profileEmailVeterinario.setText(emailUser);
        profileUsernameVeterinario.setText(usernameUser);
        profilePasswordVeterinario.setText(passwordUser);

    }

    public void passUserData(){
        String userId = dbAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
        query = database.getReference().child("Users").orderByChild("Id").equalTo(userId);

    }


}