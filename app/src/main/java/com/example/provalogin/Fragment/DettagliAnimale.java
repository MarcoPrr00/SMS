package com.example.provalogin.Fragment;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.provalogin.HomeActivity;
import com.example.provalogin.HomeEnteActivity;
import com.example.provalogin.HomeVeterinarioActivity;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Follow;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DettagliAnimale extends Fragment {


    Follow f;
    Animal animali;
    Utente utente;

    ImageView img;
    TextView txtNomeAnimale, txtPadrone, txtPreferenzaCibo, txtEta, txtSesso;

    Button btnalbumFoto;


    public DettagliAnimale(Follow tmp) {
        this.f=tmp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dettagli_animale, container, false);

        Activity activity = getActivity();
        if (activity instanceof HomeVeterinarioActivity) {
            ((HomeVeterinarioActivity) activity).setCustomBackEnabled(false);
        } else if (activity instanceof HomeEnteActivity) {
            ((HomeEnteActivity) activity).setCustomBackEnabled(false);
        }else if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).setCustomBackEnabled(false);
        }

        img= view.findViewById(R.id.img_dettagli_animale);
        txtNomeAnimale=view.findViewById(R.id.txt_dettagli_nome);
        txtPadrone=view.findViewById(R.id.txt_dettagli_padrone);
        txtPreferenzaCibo=view.findViewById(R.id.txt_dettagli_preferenza_cibo);
        txtEta=view.findViewById(R.id.eta_animale);
        txtSesso=view.findViewById(R.id.sesso_animale);
        btnalbumFoto=view.findViewById(R.id.albumfoto);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //click bottone album foto
        btnalbumFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new DettagliMieiAnimali(animali,"rendiInvisibiliBottoni")).addToBackStack(null).commit();
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Animals").child(f.id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                animali = task.getResult().getValue(Animal.class);


                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(animali.imgAnimale);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(img.getContext())
                                .load(uri).circleCrop()
                                /*.placeholder(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark)
                                .circleCrop()
                                .error(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark_normal)*/
                                .into(img);
                    }
                });
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                reference2.child("Users").child(animali.padrone).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        utente = task.getResult().getValue(Utente.class);

                        txtPadrone.setText(utente.Nome + " " + utente.Cognome);

                    }

                });


                txtNomeAnimale.setText(animali.nomeAnimale);
                txtSesso.setText(animali.sesso);
                txtPreferenzaCibo.setText(animali.preferenzaCibo);
                txtEta.setText(animali.eta);

            }

        });



    }
}
