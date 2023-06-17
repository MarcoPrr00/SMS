package com.example.provalogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

import com.example.provalogin.Fragment.HomeVeterinarioFragment;
import com.example.provalogin.Fragment.InCaricoVeterinarioFragment;
import com.example.provalogin.Fragment.PerTeVeterinarioFragment;
import com.example.provalogin.Fragment.PetsVeterinarioFragment;
import com.example.provalogin.Fragment.ProfileFragment;
import com.example.provalogin.Fragment.ProfileVeterinarioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeVeterinarioActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment=null;
    private static boolean isCustomBackEnabled = false;

    public static void setCustomBackEnabled(boolean isEnabled) {
        isCustomBackEnabled = isEnabled;
    }

    //Gestisco onBackPressed nell'activity per ogni fragment al quale  serve implementare questo metodo
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if(isCustomBackEnabled){
            if (fragment instanceof PerTeVeterinarioFragment) {
                ((PerTeVeterinarioFragment) fragment).onBackPressed();
            }

            if (fragment instanceof PetsVeterinarioFragment) {
                ((PetsVeterinarioFragment) fragment).onBackPressed();
            }
            if (fragment instanceof ProfileFragment) {
                ((ProfileFragment) fragment).onBackPressed();
            }

            if (fragment instanceof InCaricoVeterinarioFragment) {
                ((InCaricoVeterinarioFragment) fragment).onBackPressed();
            }
        }
        else{
            super.onBackPressed();
        }

    }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_nav, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileVeterinarioFragment()).addToBackStack(null).commit();
            return true;
        }
        if (id == R.id.logout){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("LOGOUT").setMessage("Vuoi Effettuare il logout?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseAuth.getInstance().signOut();
                            onBackPressed();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }
}