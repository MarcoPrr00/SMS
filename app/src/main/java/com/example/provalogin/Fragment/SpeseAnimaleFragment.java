package com.example.provalogin.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.provalogin.R;

public class SpeseAnimaleFragment extends Fragment {


    Button aggiungiSpesa;
    AlertDialog dialog;
    LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_spese_animale, container, false);


        aggiungiSpesa = view.findViewById(R.id.aggiungiSpesa);
        layout = view.findViewById(R.id.spese_container);
        buildDialog();
        aggiungiSpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        return view;
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog,null);
        EditText spesa = view.findViewById(R.id.spesaEdit);

        builder.setView(view);
        builder.setTitle("Inserisci Spesa")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCard(spesa.getText().toString());
                    }
                })
                .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog = builder.create();
    }

    private void addCard(String spesa) {
        View view = getLayoutInflater().inflate(R.layout.spesa,null);

        TextView nameView = view.findViewById(R.id.tipoSpesa);
        Button delete = view.findViewById(R.id.cancellaSpesa);

        nameView.setText(spesa);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
            }
        });

        layout.addView(view);

    }
}