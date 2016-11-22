package com.training.common.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by chenqiuyi on 16/10/25.
 */

public class MyFragmentPagerAdapt extends FragmentPagerAdapter {
    private List<String> tabTitles;
    private List<Fragment> frags;

    public MyFragmentPagerAdapt(FragmentManager fm, List<String> tabTitles, List<Fragment> frags) {
        super(fm);
        this.tabTitles = tabTitles;
        this.frags = frags;
    }

    @Override
    public Fragment getItem(int position) {
        return frags.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tabTitles == null) {
            return null;
        } else {
            return tabTitles.get(position);
        }
    }
}
