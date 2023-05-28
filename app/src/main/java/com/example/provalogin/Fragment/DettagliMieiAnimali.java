package com.example.provalogin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Follow;
import com.example.provalogin.R;

public class DettagliMieiAnimali extends Fragment {

    public DettagliMieiAnimali(Animal tmp) {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_animal, container, false);

        return view;
    }
}
