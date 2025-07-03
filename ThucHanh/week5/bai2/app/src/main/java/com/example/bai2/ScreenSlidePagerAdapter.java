package com.example.bai2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    public ScreenSlidePagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new FragmentOne();
        else
            return new FragmentTwo();
    }

    @Override
    public int getItemCount() {
        return 2; // số lượng trang
    }
}