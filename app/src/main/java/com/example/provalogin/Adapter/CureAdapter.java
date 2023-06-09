package com.example.provalogin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.provalogin.Model.Cure;
import com.example.provalogin.Model.Spesa;
import com.example.provalogin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CureAdapter extends RecyclerView.Adapter<CureAdapter.CureViewHolder> {
    final private Context mCtx;

    final private List<Cure> cureList;


    public CureAdapter(Context mCtx, List<Cure> aList){
        this.mCtx = mCtx;
        this.cureList = aList;
    }

    @NonNull
    @Override
    public CureAdapter.CureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.cure,parent,false);
        return new CureAdapter.CureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CureViewHolder holder, int position) {
        final Cure cure = cureList.get(position);
        holder.nameView.setText(cure.cura);
        holder.prezzoSpesa.setText(cure.prezzo);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference;
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
                reference = database.getReference().child("Cure").child(cure.id);
                reference.removeValue();
            }
        });

    }


    @Override
    public int getItemCount() {
        return cureList.size();
    }

    static class CureViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        TextView prezzoSpesa;
        Button delete;

        public CureViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.tipoSpesa);
            prezzoSpesa = itemView.findViewById(R.id.PrezzoSpesa);
            delete = itemView.findViewById(R.id.cancellaSpesa);

        }
    }


}
