package com.example.provalogin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class SegnalazioniAdapter extends FirebaseRecyclerAdapter<Segnalazioni,SegnalazioniAdapter.segnalazioniViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SegnalazioniAdapter(@NonNull FirebaseRecyclerOptions<Segnalazioni> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull segnalazioniViewHolder holder, int position, @NonNull Segnalazioni model) {
        holder.email.setText(model.getEmail());
        holder.città.setText(model.getCittà());
        holder.tipoSegnalazione.setText(model.getTipoSegnalazione());
        Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

    }

    @NonNull
    @Override
    public segnalazioniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.segnalazioni_item,parent,false);
        return new segnalazioniViewHolder(view);
    }

    class segnalazioniViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView email,città,tipoSegnalazione;

        public segnalazioniViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.img1);
            email = (TextView) itemView.findViewById(R.id.txt_email);
            città = (TextView) itemView.findViewById(R.id.txt_città);
            tipoSegnalazione = (TextView) itemView.findViewById(R.id.txt_tipo_segnalazione);
        }
    }


}
