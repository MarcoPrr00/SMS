package com.example.provalogin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Spesa;
import com.example.provalogin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SpeseAdapter extends RecyclerView.Adapter<SpeseAdapter.SpeseViewHolder>{

    final private Context mCtx;

    final private List<Spesa> speseList;


    public SpeseAdapter(Context mCtx, List<Spesa> aList){
        this.mCtx = mCtx;
        this.speseList = aList;
    }

    @NonNull
    @Override
    public SpeseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.spesa,parent,false);
        return new SpeseAdapter.SpeseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeseViewHolder holder, int position) {
        final Spesa spesa = speseList.get(position);
        holder.nameView.setText(spesa.spesa);
        holder.prezzoSpesa.setText(spesa.prezzo);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference;
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
                reference = database.getReference().child("Spese").child(spesa.id);
                reference.removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return speseList.size();
    }

    static class SpeseViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        TextView prezzoSpesa;
        Button delete;

        public SpeseViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.tipoSpesa);
            prezzoSpesa = itemView.findViewById(R.id.PrezzoSpesa);
            delete = itemView.findViewById(R.id.cancellaSpesa);

        }
    }


}