package com.training.storage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.training.R;
import com.training.common.view.MyFragmentPagerAdapt;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataTestActivity extends AppCompatActivity {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tab)
    TabLayout tab;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MyFragmentPagerAdapt adapt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);
        setupView();
    }

    private void setupView() {
        titles.add(getString(R.string.database));
//        titles.add(getString(R.string.content));
//        titles.add(getString(R.string.preference));
        titles.add(getString(R.string.file));

        fragments.add(new SQLiteFragment());
//        fragments.add(new ContentFragment());
//        fragments.add(new SharePreferenceFragment());
        fragments.add(new FileFragment());

        adapt = new MyFragmentPagerAdapt(getSupportFragmentManager(), titles, fragments);
        pager.setAdapter(adapt);
        tab.setupWithViewPager(pager);
    }
}