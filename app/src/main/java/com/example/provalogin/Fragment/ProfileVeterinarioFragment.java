package com.example.provalogin.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.provalogin.R;

public class  ProfileVeterinarioFragment extends Fragment {

    TextView profileNameVeterinario, profileEmailVeterinario, profileUsernameVeterinario,profilePasswordVeterinario;
    TextView titleName, titleUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_veterinario, container, false);
        profileNameVeterinario = view.findViewById(R.id.profileName);
        profileEmailVeterinario = view.findViewById(R.id.profileEmail);
        profileUsernameVeterinario = view.findViewById(R.id.profileUsername);
        profilePasswordVeterinario = view.findViewById(R.id.profilePassword);
        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);

        showUserData();
        return view;
    }

    public void showUserData(){
        Intent intent = getActivity().getIntent();

        String nameUser = intent.getStringExtra("Name");
        String emailUser = intent.getStringExtra("email");
        String usernameUser = intent.getStringExtra("username");
        String passwordUser = intent.getStringExtra("password");

        titleName.setText(nameUser);
        titleUsername.setText(usernameUser);
        profileNameVeterinario.setText(nameUser);
        profileEmailVeterinario.setText(emailUser);
        profileUsernameVeterinario.setText(usernameUser);
        profilePasswordVeterinario.setText(passwordUser);
    }
}