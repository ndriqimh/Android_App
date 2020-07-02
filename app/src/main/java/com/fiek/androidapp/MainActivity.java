package com.fiek.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Moti1 moti1;
    private Moti2 moti2;
    //private Moti3 moti3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle1 = new Bundle();
        bundle1.putString("button","1");
        mFirebaseAnalytics.logEvent("Klik_Veshjet", bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString("button2","1");
        mFirebaseAnalytics.logEvent("Klik_Harta", bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putString("butoni3","1");
        mFirebaseAnalytics.logEvent("Klik_Parashikimi", bundle3);


        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle1);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle2);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle3);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        moti1 = new Moti1();
        moti2 = new Moti2();
        //moti3 = new Moti3();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(moti1, "Sot");
        viewPagerAdapter.addFragment(moti2, "Të dhënat");
        //viewPagerAdapter.addFragment(moti3, "Harta");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_wb_sunny_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_wb_sunny_24);
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_wb_sunny_24);
/*
        BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
        badgeDrawable.setVisible(true);
*/
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}
