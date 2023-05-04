package com.example.provalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.provalogin.Fragment.FavorietesFragment;
import com.example.provalogin.Fragment.HomeFragment;
import com.example.provalogin.Fragment.HomeFragmentVeterinario;
import com.example.provalogin.Fragment.ProfileFragment;
import com.example.provalogin.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeVeterinarioActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationVeterinario;
Fragment selectedFragment;
BottomNavigationView.OnNavigationItemSelectedListener navigazione = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ic_home:
                selectedFragment= new HomeFragmentVeterinario();
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
        return false;
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_veterinario);
        bottomNavigationVeterinario = findViewById(R.id.bar_Navigator_Veterinario);
        bottomNavigationVeterinario.setOnNavigationItemSelectedListener(navigazione);
    }


}