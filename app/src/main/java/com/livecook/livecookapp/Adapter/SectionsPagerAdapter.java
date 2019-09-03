package com.livecook.livecookapp.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.livecook.livecookapp.Fragment.AllFragment;
import com.livecook.livecookapp.Fragment.AllResturant_Fragment;
import com.livecook.livecookapp.Fragment.AllcookFragment;
import com.livecook.livecookapp.Fragment.CookFragment;
import com.livecook.livecookapp.Fragment.HomeFragment;
import com.livecook.livecookapp.Fragment.ResturantFragment;
import com.livecook.livecookapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private SparseArray<Fragment> fragments;
    private Fragment mCurrentFragment;


    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        fragments = new SparseArray<>();
    }




    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        if(position==0) {
            fragment = new AllFragment();

        }
        if(position ==1)
            fragment=new AllcookFragment();
        if(position==2)
            fragment=new AllResturant_Fragment();

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    /*@Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }
*/
    public Fragment getCurrentFragment(int index) {
        if(fragments.size() > 0)
            return fragments.get(index);
        else
            return null;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        fragments.remove(position);
        super.destroyItem(container, position, object);
    }
}


/*
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        for (int i = 0; i < fragments.size(); i++) {

            fragments.add(position,fragment);
            appAdp.removeAll(fragments);
            fragments.notify();



        }


        return fragment;
    }
 */