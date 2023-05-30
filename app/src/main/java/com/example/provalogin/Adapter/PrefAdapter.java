package com.example.provalogin.Adapter;

import android.content.Context;
import android.media.Image;
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
import com.example.provalogin.Model.Follow;
import com.example.provalogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PrefAdapter extends RecyclerView.Adapter<PrefAdapter.PrefViewHolder> {
    final private Context Ctx;
    final private List<Follow> animalList;

    private Animal animali;

    FirebaseUser firebaseUser;


    public PrefAdapter(Context Ctx, List<Follow> aList){
        this.Ctx = Ctx;
        this.animalList = aList;
    }



    @NonNull
    @Override
    public PrefAdapter.PrefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Ctx).inflate(R.layout.prefe_item,parent,false);

        return new PrefAdapter.PrefViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrefAdapter.PrefViewHolder holder, int position) {
        Follow f = animalList.get(position);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Animals").child(f.id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                animali = task.getResult().getValue(Animal.class);


                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(animali.imgAnimale);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(holder.immagine.getContext())
                                .load(uri).circleCrop()
                                /*.placeholder(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark)
                                .circleCrop()
                                .error(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark_normal)*/
                                .into(holder.immagine);
                    }
                });
            }
        });









            holder.nomeAnimale.setText(f.nome);






    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }




    static class PrefViewHolder extends RecyclerView.ViewHolder{
        ImageView immagine;
        TextView nomeAnimale;

        public PrefViewHolder(@NonNull View itemView) {
            super(itemView);
            immagine = itemView.findViewById(R.id.immagine);
            nomeAnimale =  itemView.findViewById(R.id.nomeAnimale);


        }
    }



}
