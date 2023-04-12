package com.example.provalogin.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.provalogin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewAnimal extends Fragment {


    ImageView immaginedacaricare;
    FloatingActionButton nuovaimmagine;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_newanimal, container, false);
       immaginedacaricare = view.findViewById(R.id.img);
       nuovaimmagine = view.findViewById(R.id.nuovaimmagine);

       return view;
   }
}
