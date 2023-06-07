package com.example.provalogin.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.provalogin.Adapter.ImageAdapter;
import com.example.provalogin.Adapter.SegnalazioniAdapter;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Follow;
import com.example.provalogin.Model.Image;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;
import com.example.provalogin.UpdateActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class DettagliMieiAnimali extends Fragment {

    Animal animale;
    String position = new String();
    ImageView imgProfilo;
    FloatingActionButton btnNuovaFotoProfilo, btnAlbumFoto;
    Button btnSpese, btnSalute;
    TextView txtNomeAnimale;

    RecyclerView recyclerView;
    private List<Image> mImage;
    private ImageAdapter imageAdapter;
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mImage.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Image segnalazioni = snapshot.getValue(Image.class);
                    mImage.add(segnalazioni);
                }
                imageAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public DettagliMieiAnimali(Animal tmp) {
        this.animale=tmp;
    }
    public DettagliMieiAnimali(Animal tmp, String position) {
        this.animale=tmp;
        this.position=position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_animal, container, false);
        imgProfilo = view.findViewById(R.id.profileImg);
        btnNuovaFotoProfilo = view.findViewById(R.id.btn_nuava_foto_mio_animale);
        btnAlbumFoto = view.findViewById(R.id.btn_nuava_foto_album_foto);
        txtNomeAnimale = view.findViewById(R.id.name_mio_animale);
        btnSpese = view.findViewById(R.id.editButtonspese);
        btnSalute = view.findViewById(R.id.editButtonsalute);


        btnSpese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SpeseAnimaleFragment()).addToBackStack(null).commit();
            }
        });
        if(!(position.isEmpty()) && position.equals("rendiInvisibiliBottoni")){
            rendiInvisibiliBottoni();
        }

        //LOGICA SEGNALAZIONI ADAPTER
        recyclerView = view.findViewById(R.id.recycler_view_foto_album_animali);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mImage = new ArrayList<>();
        imageAdapter = new ImageAdapter(this.getContext(), mImage);

        recyclerView.setAdapter(imageAdapter);

        Query db= FirebaseDatabase.getInstance().getReference("Image").orderByChild("idAnimale").equalTo(animale.id);
        db.addValueEventListener(valueEventListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        caricaImgProfilo();



        txtNomeAnimale.setText(animale.nomeAnimale);



        btnNuovaFotoProfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("Animale", animale);
                intent.putExtra("Posizione", "dettagliMieiAnimali");
                startActivityForResult(intent, 0111);
            }
        });

        btnAlbumFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("Animale", animale);
                intent.putExtra("Posizione", "nuovaFotoAlbum");
                startActivity(intent);
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Animals").child(animale.id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                animale = task.getResult().getValue(Animal.class);
                caricaImgProfilo();

            }

        });
    }

    public void caricaImgProfilo(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(animale.imgAnimale);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(imgProfilo.getContext()).load(uri).into(imgProfilo);
            }
        });
    }

    private void rendiInvisibiliBottoni(){
        btnAlbumFoto.setVisibility(View.INVISIBLE);
        btnNuovaFotoProfilo.setVisibility(View.INVISIBLE);
        btnSpese.setVisibility(View.INVISIBLE);
        btnSalute.setVisibility(View.INVISIBLE);
    }

}
