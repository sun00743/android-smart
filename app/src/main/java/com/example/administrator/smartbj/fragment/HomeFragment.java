package com.example.administrator.smartbj.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.smartbj.R;
import com.example.administrator.smartbj.ui.tabstrip.PagerSlidingTabStrip;


public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private PagerSlidingTabStrip tabStrip;
    private String[] TITLES = {"北京", "中国", "国际", "体育"};

    public Context getInstance(){
        return HomeFragment.this.getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentPagerAdapter adapter = new ViewAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

//        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
//                getResources().getDisplayMetrics());
//        viewPager.setPageMargin(pageMargin);

        tabStrip.setViewPager(viewPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabStrip);
        return view;
    }


    class ViewAdapter extends FragmentPagerAdapter {

        public ViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BeiJingFragment();
                case 1:
                    return new ChinaFragment();
                case 2:
                    return new InternationalFragment();
                case 3:
                    return new SportFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position % TITLES.length];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
}
