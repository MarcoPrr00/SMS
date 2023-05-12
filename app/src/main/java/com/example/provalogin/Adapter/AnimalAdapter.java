
package com.example.provalogin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    final private Context mCtx;
    final private List<Animal> animalList;



    public AnimalAdapter(Context mCtx, List<Animal> aList){
        this.mCtx = mCtx;
        this.animalList = aList;
    }

    @NonNull
    @Override
    public AnimalAdapter.AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.animal_item,parent,false);
        return new AnimalAdapter.AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalAdapter.AnimalViewHolder holder, int position) {
        final Animal animali = animalList.get(position);



        holder.nome_item.setText(animali.nome_animale);
        holder.specie_item.setText(animali.specie_animale);



        Glide.with(holder.img.getContext())
                .load(R.drawable.logo)
                /*.placeholder(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark_normal)*/
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }




    static class AnimalViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView nome_item, specie_item, padrone;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            nome_item =  itemView.findViewById(R.id.nome_item);
            specie_item =  itemView.findViewById(R.id.specie_item);

        }
    }




}


