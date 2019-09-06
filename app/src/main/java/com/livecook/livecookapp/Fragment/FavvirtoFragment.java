package com.livecook.livecookapp.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livecook.livecookapp.Adapter.FavoritetabAdapter;
import com.livecook.livecookapp.R;

import klogi.com.RtlViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavvirtoFragment extends Fragment {
    RtlViewPager viewPager;


    public FavvirtoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_favvirto, container, false);

        FavoritetabAdapter sectionsPagerAdapter = new FavoritetabAdapter(getContext(),
                getActivity().getSupportFragmentManager());
         viewPager =  (RtlViewPager)root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setSelectedTabIndicatorColor(R.color.white); // here
        tabs.setSelectedTabIndicatorColor(Color.WHITE);

        tabs.setupWithViewPager(viewPager);
        // Inflate the layout for this fragment
        return root;

    }

}
