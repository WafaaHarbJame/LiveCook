package com.livecook.livecookapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livecook.livecookapp.Adapter.AllFirebaseResturantCookLiveAdapter;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.AllFirebaseModel;
import com.livecook.livecookapp.Model.Allresturantcook;
import com.livecook.livecookapp.R;

import java.util.ArrayList;


public class AllcookFragment extends Fragment {


    RecyclerView allrecycler;
    //ArrayList<Allresturantcook> data = new ArrayList<>();
    ArrayList<AllFirebaseModel> firebasedata ;

    SwipeRefreshLayout allswip;
    HomeFragment homeFragment;
    // AllResturantCookAdapter allResturantCookAdapter;
    private DatabaseReference mFirebaseDatabase;

    Allresturantcook allresturantcook;


    AllFirebaseResturantCookLiveAdapter allFirebaseResturantCookAdapter;

    SearchView searchView;
    View lyt_failed;
    Button btn_failed_retry;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_allcook, container, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live");
        //            mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live2").child("24_6");
        firebasedata = new ArrayList<>();
        allrecycler = view.findViewById(R.id.allrecycler);
        //editsearch = view.findViewById(R.id.simpleSearchView);
        allrecycler.setHasFixedSize(true);
        allswip = view.findViewById(R.id.allswip);

        allswip.setColorSchemeResources
                (R.color.colorPrimary, android.R.color.holo_green_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_blue_dark);


        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(),2);


        allrecycler.setLayoutManager(manager);

        lyt_failed = view.findViewById(R.id.lyt_failed_home);
        btn_failed_retry = lyt_failed.findViewById(R.id.failed_retry);


        allswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                onStart();


            }
        });

        // allswip.setRefreshing(false);

        btn_failed_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt_failed.setVisibility(View.GONE);
                allswip.setRefreshing(false);
                allswip.setRefreshing(false);

                displayData();
            }

            public void displayData() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);

                    }
                }, 1000);
            }

        });

        btn_failed_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt_failed.setVisibility(View.GONE);
                allswip.setRefreshing(false);
                allswip.setRefreshing(false);

                displayData();
            }

            public void displayData() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);

                    }
                }, 1000);
            }

        });


        return view;
    }




    @Override
    public void onStart() {

        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            firebasedata.clear();
            allswip.setRefreshing(true);


            mFirebaseDatabase.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    firebasedata.clear();
                    for (DataSnapshot livenapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot live1snapshot : livenapshot.getChildren()) {

                            AllFirebaseModel detilas = live1snapshot.getValue(AllFirebaseModel.class);
                            assert detilas != null;
                            if (detilas.isStatus()) {
                                if (detilas.getType() == 5 || detilas.getType() == 6) {
                                    firebasedata.add(detilas);
                                }

                            }
                        }


                    }

                    allFirebaseResturantCookAdapter = new AllFirebaseResturantCookLiveAdapter(firebasedata, getActivity());
                    allrecycler.setAdapter(allFirebaseResturantCookAdapter);
                    allFirebaseResturantCookAdapter.notifyDataSetChanged();
                    allswip.setRefreshing(false);
                    allFirebaseResturantCookAdapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                }
            });
        }

        else {
            lyt_failed.setVisibility(View.VISIBLE);
            allswip.setRefreshing(false);
        }
        super.onStart();
    }
}
