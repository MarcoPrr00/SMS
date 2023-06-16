package com.example.provalogin.Adapter;

import android.content.Context;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.provalogin.Model.Animal;

import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.List;

public class UtenteAdapter extends RecyclerView.Adapter<UtenteAdapter.UtenteViewHolder> {

    final private Context mCtx;
    final private List<Utente> utenteList;
    private FirebaseUser firebaseUser;

    public UtenteAdapter(Context mCtx, List<Utente> utenteList){
        this.mCtx = mCtx;
        this.utenteList = utenteList;
    }



    @NonNull
    @Override
    public UtenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.user_item, parent, false);
        return new UtenteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtenteViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(animal.ImgUrl);
        //storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            /*@Override
            public void onSuccess(Uri uri) {
                Glide.with(mCtx).load(uri).into(holder.image_profile);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return utenteList.size();
    }

    static public class UtenteViewHolder extends RecyclerView.ViewHolder{

        public TextView nomeanimale;
        public TextView specieanimale;
        public ImageView image_profile;
       // public Button btn_follow;

        public UtenteViewHolder(@NonNull View itemView){
            super(itemView);

            nomeanimale = itemView.findViewById(R.id.nome);
            specieanimale = itemView.findViewById(R.id.specie);
            image_profile = itemView.findViewById(R.id.image_profile);
            //btn_follow = itemView.findViewById(R.id.btn_follow);


        }
    }



}
