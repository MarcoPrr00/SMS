package com.example.provalogin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.provalogin.Fragment.QrcodeScanFragment;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class InStalloAdapter extends RecyclerView.Adapter<InStalloAdapter.AnimalViewHolder>{

    final private Context mCtx;

    final private List<Animal> animalList;

    FirebaseUser firebaseUser;


    public InStalloAdapter(Context mCtx, List<Animal> aList){
        this.mCtx = mCtx;
        this.animalList = aList;
    }

    @NonNull
    @Override
    public InStalloAdapter.AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.installo_item,parent,false);
        return new InStalloAdapter.AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InStalloAdapter.AnimalViewHolder holder, int position) {
        final Animal animali = animalList.get(position);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        holder.nome_item.setText(animali.nomeAnimale);
        holder.specie_item.setText(animali.specie);
        holder.btn_cancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            new AlertDialog.Builder(holder.btn_cancella.getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("ELIMINAZIONE STALLO").setMessage("Sei sicuro di voler eliminare?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reference;
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
                        reference = database.getReference().child("Animals").child(animali.id).child("idStallo");
                        reference.setValue("no Stallo");


                }
            })
                    .setNegativeButton("No", null)
                    .show();
            }

        });



        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(animali.imgAnimale);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.img.getContext())
                        .load(uri)
                        /*.placeholder(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark)
                        .circleCrop()
                        .error(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark_normal)*/
                        .into(holder.img);
            }
        });






    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }




    static class AnimalViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView nome_item, specie_item;
        Button btn_cancella;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img2);
            nome_item =  itemView.findViewById(R.id.nome_animale_item);
            specie_item =  itemView.findViewById(R.id.specie_animale_item);
            btn_cancella = itemView.findViewById(R.id.btn_cancella_stallo);

        }
    }




}
