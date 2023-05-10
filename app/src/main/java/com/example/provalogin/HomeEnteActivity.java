package com.example.provalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.provalogin.Adapter.MyViewPagerAdapter;
import com.example.provalogin.Fragment.FavorietesFragment;
import com.example.provalogin.Fragment.HomeEnteFragment;
import com.example.provalogin.Fragment.HomeFragment;
import com.example.provalogin.Fragment.InCaricoVeterinarioFragment;
import com.example.provalogin.Fragment.PerTeVeterinarioFragment;
import com.example.provalogin.Fragment.PetsVeterinarioFragment;
import com.example.provalogin.Fragment.ProfileFragment;
import com.example.provalogin.Fragment.ProfileVeterinarioFragment;
import com.example.provalogin.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeEnteActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment=null;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.homeEnte:
                    selectedFragment= new PerTeVeterinarioFragment();
                    break;
                case R.id.inCarico:
                    selectedFragment= new InCaricoVeterinarioFragment();
                    break;
                case R.id.petsEnte:
                    selectedFragment= new PetsVeterinarioFragment();
                    break;
                case R.id.profileEnte:
                    selectedFragment= new ProfileVeterinarioFragment();
                    break;
            }
            if( selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_ente,
                        selectedFragment).commit();
            }


            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ente);

        bottomNavigationView=findViewById(R.id.bottom_navigation_ente);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_ente,
                new PerTeVeterinarioFragment()).commit();


    }
}