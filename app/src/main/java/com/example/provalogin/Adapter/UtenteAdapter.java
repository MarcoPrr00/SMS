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
    final private List<Animal> animaleList;
    private FirebaseUser firebaseUser;

    public UtenteAdapter(Context mCtx, List<Animal> utenteList){
        this.mCtx = mCtx;
        this.animaleList = utenteList;
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

        final Animal animal = animaleList.get(position);
        holder.btn_follow.setVisibility(View.VISIBLE);

        holder.nomeanimale.setText(animal.nome_animale);
        holder.specieanimale.setText(animal.specie_animale);
        //StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(animal.ImgUrl);
        //storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            /*@Override
            public void onSuccess(Uri uri) {
                Glide.with(mCtx).load(uri).into(holder.image_profile);
            }
        });*/
        isFollowing(animal.id, holder.btn_follow);

        if(animal.id.equals(firebaseUser.getUid())){
            holder.btn_follow.setVisibility(View.GONE);
        }

        holder.btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.btn_follow.getText().toString().equals("Follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(animal.id).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(animal.id)
                            .child("followers").child(firebaseUser.getUid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(animal.id).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(animal.id)
                            .child("followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return animaleList.size();
    }

    static public class UtenteViewHolder extends RecyclerView.ViewHolder{

        public TextView nomeanimale;
        public TextView specieanimale;
        public ImageView image_profile;
        public Button btn_follow;

        public UtenteViewHolder(@NonNull View itemView){
            super(itemView);

            nomeanimale = itemView.findViewById(R.id.nome);
            specieanimale = itemView.findViewById(R.id.specie);
            image_profile = itemView.findViewById(R.id.image_profile);
            btn_follow = itemView.findViewById(R.id.btn_follow);


        }
    }

    private void isFollowing(String userid, Button button){
        DatabaseReference reference = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userid).exists()){
                    button.setText("Following");
                } else {
                    button.setText("Follow");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
