package com.training.network.activity;

import android.os.Bundle;
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

public class NetWorkTestActivity extends AppCompatActivity {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tab)
    TabLayout tab;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);

//        titles.add(getString(R.string.volley));
        titles.add(getString(R.string.retrofit));
        titles.add(getString(R.string.original));

//        fragments.add(new VolleyTestFragment());
        fragments.add(new RetrofitTestFragment());
        fragments.add(new OriginalHttpTestFragment());

        MyFragmentPagerAdapt adapt =
                new MyFragmentPagerAdapt(getSupportFragmentManager(),
                        titles, fragments);
        pager.setAdapter(adapt);
        tab.setupWithViewPager(pager);
    }

}
