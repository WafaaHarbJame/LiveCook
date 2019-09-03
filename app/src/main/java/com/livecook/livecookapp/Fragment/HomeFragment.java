package com.livecook.livecookapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.livecook.livecookapp.Adapter.SectionsPagerAdapter;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.MyInterface;
import com.livecook.livecookapp.R;

public class HomeFragment extends Fragment implements MyInterface {


    ViewPager viewPager;
    TabLayout tabs;
    SectionsPagerAdapter sectionsPagerAdapter;
    String CURRENT;
    Fragment currentFragment;
    Fragment currentFragment1;

    public interface OnFilterChangeListener {
        void onFilterChange(boolean isVisible);
    }
    OnFilterChangeListener onFilterChangeListener;



    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_home, container, false);




         sectionsPagerAdapter = new SectionsPagerAdapter(getContext(),
                getActivity().getSupportFragmentManager());
         viewPager = root.findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    onFilterChangeListener.onFilterChange(false);
                } else  if(position == 1) {
                    onFilterChangeListener.onFilterChange(true);
                }
                else  if(position == 2) {
                    onFilterChangeListener.onFilterChange(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(sectionsPagerAdapter);
        if(viewPager!=null){
            getCurrentf();
            //getCuuuu();
        }


        tabs = root.findViewById(R.id.tabs);
        // here

        tabs.setSelectedTabIndicatorColor(Color.WHITE);


        tabs.setupWithViewPager(viewPager);
        // Inflate the layout for this fragment





        return root;






    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFilterChangeListener) {
            onFilterChangeListener = (OnFilterChangeListener) context;
        } else {
            throw new RuntimeException("OnFilterChangeListener must be implemented!");
        }
    }


    /* public void getCurrentFragment(){
        int position = viewPager.getCurrentItem();
       // AllFragment fragment =SectionsPagerAdapter.getRegisteredFragment(position);




    }*/













    @Override
    public Fragment getCurrentf() {


        return sectionsPagerAdapter.getCurrentFragment(viewPager.getCurrentItem());

        }




    @Override
    public String getCurrentFragmrnt() {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {



                Fragment currentFragment = sectionsPagerAdapter.getItem(viewPager.getCurrentItem());
                //CURRENT=sectionsPagerAdapter.getItem(viewPager.getCurrentItem()).toString();

                viewPager.getCurrentItem();
                

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return null;
    }

    @Override
    public Fragment getCuuuu() {
        return null;
    }


}