package com.example.provalogin.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.provalogin.Adapter.SegnalazioniAdapter;
import com.example.provalogin.Adapter.SpeseAdapter;
import com.example.provalogin.HomeActivity;
import com.example.provalogin.HomeEnteActivity;
import com.example.provalogin.HomeVeterinarioActivity;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.Model.Spesa;
import com.example.provalogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SpeseAnimaleFragment extends Fragment {


    Button aggiungiSpesa;
    AlertDialog dialog;
    DatabaseReference reference;

    RecyclerView recyclerView;
    private SpeseAdapter speseAdapter;
    private List<Spesa> mSpese;

    Animal animale;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mSpese.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Spesa spesa = snapshot.getValue(Spesa.class);
                    mSpese.add(spesa);
                }
                speseAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

        public SpeseAnimaleFragment(Animal animal){
        this.animale=animal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_spese_animale, container, false);

        Activity activity = getActivity();
        if (activity instanceof HomeVeterinarioActivity) {
            ((HomeVeterinarioActivity) activity).setCustomBackEnabled(false);
        } else if (activity instanceof HomeEnteActivity) {
            ((HomeEnteActivity) activity).setCustomBackEnabled(false);
        }else if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).setCustomBackEnabled(false);
        }

        aggiungiSpesa = view.findViewById(R.id.aggiungiSpesa);
        //layout = view.findViewById(R.id.spese_container);

        //Logica Recycler
        recyclerView = view.findViewById(R.id.recycler_view_spese_animali);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSpese = new ArrayList<>();
        speseAdapter = new SpeseAdapter(this.getContext(), mSpese);
        recyclerView.setAdapter(speseAdapter);
        Query db= FirebaseDatabase.getInstance().getReference("Spese").orderByChild("idAnimale").equalTo(animale.id);
        db.addValueEventListener(valueEventListener);


        aggiungiSpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog();
            }
        });
        return view;
    }

    private void buildDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View view = getLayoutInflater().inflate(R.layout.dialog,null);
            EditText spesa = view.findViewById(R.id.spesaEdit);
            EditText prezzo = view.findViewById(R.id.prezzoEdit);
            EditText date = view.findViewById(R.id.calendarViewSpesa);
            spesa.setText("");
            prezzo.setText("");
            date.setText("");
            builder.setView(view);
            date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    c = Calendar.getInstance();


                    // on below line we are getting
                    // our day, month and year.
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    // on below line we are creating a variable for date picker dialog.
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            // on below line we are passing context.
                            getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // on below line we are setting date to our edit text.
                                    date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            },
                            // on below line we are passing year,
                            // month and day for selected date in our date picker.
                            year, month, day);
                    // at last we are calling show to
                    // display our date picker dialog.
                    datePickerDialog.show();
                }
            }
        });
        builder.setTitle("Inserisci Spesa")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = NewAnimal.generacodiceid();
                        Spesa holder = new Spesa();
                        holder.spesa = spesa.getText().toString();
                        holder.prezzo = prezzo.getText().toString()+"€";
                        holder.id = id;
                        holder.idAnimale=animale.id;
                        holder.idPadrone=animale.padrone;
                        holder.date = date.getText().toString();
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
                        reference = database.getReference().child("Spese").child(id);
                        reference.setValue(holder);

                    }
                })
                .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog = builder.create();
        dialog.show();
    }
}