package com.example.provalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.provalogin.Fragment.FavorietesFragment;
import com.example.provalogin.Fragment.HomeEnteFragment;
import com.example.provalogin.Fragment.HomeFragment;
import com.example.provalogin.Fragment.ProfileFragment;
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
                case R.id.ic_home:
                    selectedFragment= new HomeEnteFragment();
                    break;
                case R.id.ic_search:
                    selectedFragment= new SearchFragment();
                    break;
                case R.id.ic_favorites:
                    selectedFragment= new FavorietesFragment();
                    break;
                case R.id.ic_profile:
                    SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    editor.apply();
                    selectedFragment= new ProfileFragment();
                    break;
            }
            if( selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
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
        /*
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();*/

    }
}