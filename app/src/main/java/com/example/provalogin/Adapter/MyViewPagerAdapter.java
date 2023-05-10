package com.example.provalogin.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.provalogin.Fragment.HomeEnteFragment;
import com.example.provalogin.Fragment.HomeVeterinarioFragment;
import com.example.provalogin.Fragment.InCaricoVeterinarioFragment;
import com.example.provalogin.Fragment.PerTeVeterinarioFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {


    public MyViewPagerAdapter(@NonNull HomeVeterinarioFragment fragmentActivity) {
        super(fragmentActivity);
    }
    public MyViewPagerAdapter(@NonNull HomeEnteFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PerTeVeterinarioFragment();
            case 1 :
                return new InCaricoVeterinarioFragment();
            default:
                return new PerTeVeterinarioFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

