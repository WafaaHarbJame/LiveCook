package com.livecook.livecookapp.Adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.livecook.livecookapp.Fragment.CookFavvoriteFragment;
import com.livecook.livecookapp.Fragment.ResturantFavioriteFragment;
import com.livecook.livecookapp.R;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class FavoritetabAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{ R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;

    public FavoritetabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        if(position==0)
            fragment=new CookFavvoriteFragment();
        else if(position ==1)
            fragment=new ResturantFavioriteFragment();

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
        return 2;
    }
}