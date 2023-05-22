package com.example.provalogin.Adapter;

import android.content.Context;
import android.media.Image;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PrefAdapter extends RecyclerView.Adapter<PrefAdapter.PrefViewHolder> {
    final private Context Ctx;
    final private List<Animal> animalList;

    FirebaseUser firebaseUser;


    public PrefAdapter(Context Ctx, List<Animal> aList){
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
        final Animal animali = animalList.get(position);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();




        holder.nomeanimale.setText(animali.nomeAnimale);

        Glide.with(holder.immagine.getContext())
                .load(R.drawable.logo)
                /*.placeholder(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark_normal)*/
                .into(holder.immagine);





    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }




    static class PrefViewHolder extends RecyclerView.ViewHolder{
        ImageView immagine;
        TextView nomeanimale;

        public PrefViewHolder(@NonNull View itemView) {
            super(itemView);
            immagine = itemView.findViewById(R.id.immagine);
            nomeanimale =  itemView.findViewById(R.id.nomeanimale);


        }
    }



}
