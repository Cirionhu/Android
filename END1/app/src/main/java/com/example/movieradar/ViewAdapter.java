package com.example.movieradar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewAdapter  extends FragmentStateAdapter {

    public ViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    //A kapott positiontól függően betöltjük (létrehozzuk a fragmentet)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Search() ;
            case 1:
                return new Home();
            case 2:
                return new Lists();
            default: return new Home();
        }





    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
