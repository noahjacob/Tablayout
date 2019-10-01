package com.example.tablayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    private final ArrayList<Fragment> FragmentList = new ArrayList<>();
    private final ArrayList<String> FragmentTitle = new ArrayList<>();



    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentList.get(position);

    }

    @Override
    public int getCount() {
        return FragmentList.size();

    }
    public void addfragment(Fragment fragment, String title){
        FragmentList.add(fragment);
        FragmentTitle.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentTitle.get(position);

    }
}
