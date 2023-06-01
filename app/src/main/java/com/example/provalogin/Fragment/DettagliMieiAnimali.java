package com.example.provalogin.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Follow;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;
import com.example.provalogin.UpdateActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DettagliMieiAnimali extends Fragment {

    Animal animale;
    ImageView imgProfilo;
    FloatingActionButton btnNuovaFotoProfilo, btnAlbumFoto;
    TextView txtNomeAnimale;

    public DettagliMieiAnimali(Animal tmp) {
        this.animale=tmp;
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


}
