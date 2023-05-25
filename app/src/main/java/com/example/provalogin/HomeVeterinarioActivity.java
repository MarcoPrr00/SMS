package com.example.provalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.view.MenuItem;

import com.example.provalogin.Fragment.HomeVeterinarioFragment;
import com.example.provalogin.Fragment.InCaricoVeterinarioFragment;
import com.example.provalogin.Fragment.PerTeVeterinarioFragment;
import com.example.provalogin.Fragment.PetsVeterinarioFragment;
import com.example.provalogin.Fragment.ProfileVeterinarioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeVeterinarioActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_veterinario);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new PerTeVeterinarioFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.homeVet:
                    selectedFragment= new PerTeVeterinarioFragment();
                    break;
                case R.id.inCaricoVet:
                    selectedFragment= new InCaricoVeterinarioFragment();
                    break;
                case R.id.petsVet:
                    selectedFragment= new PetsVeterinarioFragment();
                    break;
                case R.id.profileVet:
                    selectedFragment= new ProfileVeterinarioFragment();
                    break;
            }
            if( selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
            return true;
        }
    };
}