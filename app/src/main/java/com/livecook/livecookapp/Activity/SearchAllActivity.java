package com.livecook.livecookapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livecook.livecookapp.Adapter.AllFirebaseResturantCookAdapter;
import com.livecook.livecookapp.Adapter.AllFirebaseResturantCookAdaptersearch;
import com.livecook.livecookapp.Adapter.SearchAllFirebaseResturantCookAdapter;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.AllFirebaseModel;
import com.livecook.livecookapp.Model.Allresturantcook;
import com.livecook.livecookapp.R;

import java.util.ArrayList;

public class SearchAllActivity extends AppCompatActivity implements AllFirebaseResturantCookAdaptersearch.DataAdapterListenerr {
    private Toolbar toolbar;
    SearchView searchView;
    RecyclerView allrecycler;
    //ArrayList<Allresturantcook> data = new ArrayList<>();
    ArrayList<AllFirebaseModel> firebasedata ;

    SwipeRefreshLayout allswip;
    // AllResturantCookAdapter allResturantCookAdapter;
    private DatabaseReference mFirebaseDatabase;

    Allresturantcook allresturantcook;

    SearchAllFirebaseResturantCookAdapter allFirebaseResturantCookAdapter;
    ImageView no_result;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all);

        toolbar =  findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
        toolbar.setTitle("بحث");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        searchView = findViewById(R.id.etSearch1);

        searchView.setQueryHint("بحث");
        no_result=findViewById(R.id.textView_noresult);









        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live");
        //            mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live2").child("24_6");
        firebasedata = new ArrayList<>();
        allrecycler = findViewById(R.id.allrecycler);
        //editsearch = view.findViewById(R.id.simpleSearchView);
        allrecycler.setHasFixedSize(true);

        allswip = findViewById(R.id.allswip);
        allswip.setColorSchemeResources
                (R.color.colorPrimary, android.R.color.holo_green_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_blue_dark);
        allswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                allswip.setRefreshing(false);
            }
        });


        RecyclerView.LayoutManager manager = new GridLayoutManager(SearchAllActivity.this,2);

        allrecycler.setLayoutManager(manager);
        allswip.setRefreshing(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted




                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                allFirebaseResturantCookAdapter.getFilter().filter(query);
                allFirebaseResturantCookAdapter.notifyDataSetChanged();
                allswip.setRefreshing(false);





                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchView.clearFocus();

                return false;
            }
        });

    }



    public void onStart() {

        firebasedata.clear();
        allswip.setRefreshing(true);


        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebasedata.clear();
                for(DataSnapshot livenapshot:dataSnapshot.getChildren()) {
                    for(DataSnapshot live1snapshot:livenapshot.getChildren()) {

                        AllFirebaseModel detilas = live1snapshot.getValue(AllFirebaseModel.class);
                            firebasedata.add(detilas);



                    }


                }

                allFirebaseResturantCookAdapter = new SearchAllFirebaseResturantCookAdapter(firebasedata,SearchAllActivity.this);
                allrecycler.setAdapter(allFirebaseResturantCookAdapter);
                allFirebaseResturantCookAdapter.notifyDataSetChanged();
                allswip.setRefreshing(false);
                //allFirebaseResturantCookAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });
        super.onStart();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(SearchAllActivity.this, MainActivity.class);
            startActivity(intent);
           finish();
            return true;
        }


        if (item.getItemId() == R.id.nav_home) {
            Intent intent=new Intent(SearchAllActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




    @Override
    public void onDataSelected(AllFirebaseModel allFirebaseModel) {

    }

    public boolean isKeybordShowing(Context context, View view) {
        try {
            InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return keyboard.isActive();
        } catch (Exception ex) {
            Log.e("keyboardHide", "cannot hide keyboard", ex);
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        hideKeyboardFrom(SearchAllActivity.this,searchView);



        finish();
    }
}
