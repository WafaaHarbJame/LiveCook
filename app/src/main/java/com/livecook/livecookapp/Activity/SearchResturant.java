package com.livecook.livecookapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Adapter.SearchCookAdapter;
import com.livecook.livecookapp.Adapter.SearchResturantAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.AllResturanrModel;
import com.livecook.livecookapp.Model.AllcookModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.CookModel;
import com.livecook.livecookapp.Model.Datum;
import com.livecook.livecookapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResturant extends AppCompatActivity implements SearchResturantAdapter.ContactsAdapterListener {


    private EditText mEdname;
    private Spinner mCountryname;
    private Spinner mCityname;
    private EditText mRegion;
    private Spinner mCountrycode;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    int country_id=0;
    int city_id=0;
    int country_codee;
    String type;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> cityadapter;
    ArrayAdapter<String> countrycodeadapter;

    List<String> countrylist = new ArrayList<String>();
    List<String> citylist = new ArrayList<String>();
    List<String> countrycodelist = new ArrayList<String>();
    ArrayList<Datum> data = new ArrayList<>();
    ArrayList<Datum> city = new ArrayList<>();
    private Toolbar toolbar;
    Button restfilte;
    int type_id;
    SearchView searchView;


    private static  Boolean ISLOGIN;
    RecyclerView cookrecycler;
    ArrayList<AllResturanrModel.DataBean> data2 = new ArrayList<>();
    SwipeRefreshLayout resturantswip;
    SearchResturantAdapter cookAdapter;
    CookModel cookModel;
    ProgressDialog progressDialog;
    ArrayList<AllResturanrModel.DataBean> data1 = new ArrayList<>();
    Button btn_failed_retry;

    View lyt_failed;
    SharedPreferences prefs;
    String tokenfromlogin;
    String typnumer;
    LinearLayoutManager manager;
    int  total_item_count=0;
    private  boolean isLoading=true;
    int past_visble_items, visibleItemCount,previous_total=0;
    private  int view_thershold=6;
    int page_number=1;
    private boolean loading;
    ImageView no_result;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_search_resturant);

        cookAdapter = new SearchResturantAdapter(data1, SearchResturant.this,this);
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");

        searchView = findViewById(R.id.etSearch1);
        no_result=findViewById(R.id.textView_noresult);

        searchView.setQueryHint("بحث");


        toolbar =  findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
        toolbar.setTitle("بحث");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        cookrecycler = findViewById(R.id.cookrecycler);
        //editsearch = view.findViewById(R.id.simpleSearchView);
        progressDialog = new ProgressDialog(SearchResturant.this);
        lyt_failed  = findViewById(R.id.lyt_failed_home);
        btn_failed_retry = lyt_failed.findViewById(R.id.failed_retry);
        resturantswip = findViewById(R.id.resturantswip);
        manager = new LinearLayoutManager(SearchResturant
                .this);
        cookrecycler.setLayoutManager(manager);
        cookrecycler.setHasFixedSize(true);

        resturantswip.setColorSchemeResources
                (R.color.colorPrimary, android.R.color.holo_green_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_blue_dark);

       // checkInternet();
        // getCookerforall();

        resturantswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // data1.clear();

              //  checkInternet();
                resturantswip.setRefreshing(false);



            }
        });


        checkInternet();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted

                cookAdapter.getFilter().filter(query);

                resturantswip.setRefreshing(false);






                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                // filter recycler view when text is changed

                cookAdapter.getFilter().filter(query);


                resturantswip.setRefreshing(false);

                return false;
            }
        });



        cookrecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount=manager.getChildCount();
                total_item_count=manager.getItemCount();
                past_visble_items=manager.findFirstVisibleItemPosition();
                if(dy>0){

                    if(isLoading){
                        if(total_item_count>previous_total){

                            isLoading=false;
                            previous_total=total_item_count;
                        }
                    }

                    if(!loading&&(total_item_count-visibleItemCount)<=(past_visble_items+view_thershold)){
                        if (typnumer.matches("cooker")||typnumer.matches("restaurant")){
                            getAllResturantPagination();
                            isLoading=true;
                            page_number++;

                        }

                        else  if (typnumer.matches("user")) {

                            if (ISLOGIN) {

                                getResturantPagination(tokenfromlogin);
                                isLoading=true;
                                page_number++;



                            } else {


                                getAllResturantPagination();
                                isLoading=true;
                                page_number++;




                            }




                        }


                    }

                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        btn_failed_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt_failed.setVisibility(View.GONE);
                hideDialog();
                resturantswip.setRefreshing(false);

                displayData();
            }

            public void displayData() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent= new Intent(SearchResturant.this, MainActivity.class);
                        startActivity(intent);

                    }
                }, 1000);
            }

        });


        RecyclerView.LayoutManager manager = new GridLayoutManager(SearchResturant.this,1);
        // RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());


        cookrecycler.setLayoutManager(manager);
        //  getCooker();

    }







    public void getAllResturant() {
        //showDialog();
        resturantswip.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.allrestaurant,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                            boolean status=task_respnse.getBoolean("status");
                            JSONArray taskarray = task_respnse.getJSONArray("data");
                            for(int i=0;i<taskarray.length();i++) {
                                int id=taskarray.getJSONObject(i).getInt("id");
                                String name = taskarray.getJSONObject(i).getString("name");
                                int countryId = taskarray.getJSONObject(i).getInt("country_id");
                                String countryName = taskarray.getJSONObject(i).getString("country_name");
                                int cityId = taskarray.getJSONObject(i).getInt("city_id");
                                String cityName  = taskarray.getJSONObject(i).getString("city_name");
                                String region  = taskarray.getJSONObject(i).getString("region");
                                String avatarURL  = taskarray.getJSONObject(i).getString("avatar_url");
                                String description  = taskarray.getJSONObject(i).getString("description");
                                int followersNo  = taskarray.getJSONObject(i).getInt("followers_no");

                                AllResturanrModel.DataBean dataBean=new AllResturanrModel.DataBean();
                                dataBean.setId(id);
                                dataBean.setCity_id(cityId);
                                dataBean.setAvatar_url(avatarURL);
                                dataBean.setCountry_name(countryName);
                                dataBean.setFollowers_no(followersNo);
                                dataBean.setId(id);
                                dataBean.setDescription(description);
                                dataBean.setRegion(region);
                                dataBean.setCity_name(cityName);
                                dataBean.setName(name);
                                dataBean.setCountry_id(countryId);
                                dataBean.setId(id);
                                data1.add(dataBean);

                            }


                            RecyclerView.LayoutManager manager = new LinearLayoutManager(SearchResturant.this);
                            cookrecycler.setLayoutManager(manager);
                            cookrecycler.setAdapter(cookAdapter);
                            cookAdapter.notifyDataSetChanged();
                            resturantswip.setRefreshing(false);
                            cookAdapter.notifyDataSetChanged();







                            // hideDialog();



                        } catch (JSONException e) {

                            e.printStackTrace();
                            //hideDialog();
                            resturantswip.setRefreshing(false);


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //hideDialog();
                resturantswip.setRefreshing(false);


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                return headers;


            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                return headers;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }



    public void showDialog() {
        progressDialog = new ProgressDialog(SearchResturant.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    private void checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) SearchResturant.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

           /* data1.clear();
            getCooker(tokenfromlogin);
            cookAdapter.notifyDataSetChanged();*/

            if (prefs != null) {
                typnumer = prefs.getString(Constants.TYPE, "user");
                ISLOGIN=prefs.getBoolean(Constants.ISLOGIN,false);





                if (!ISLOGIN) {
                    data1.clear();
                    getAllResturant();
                    cookAdapter.notifyDataSetChanged();
                }
                else {
                    data1.clear();
                    getResturant(tokenfromlogin);
                    cookAdapter.notifyDataSetChanged();


                }



                if (typnumer.matches("cooker")) {
                    data1.clear();
                    getAllResturant();
                    cookAdapter.notifyDataSetChanged();
                } else if (typnumer.matches("restaurant")) {

                    data1.clear();
                    getAllResturant();
                    cookAdapter.notifyDataSetChanged();

                } else if (typnumer.matches("user")) {

                    data1.clear();
                    getResturant(tokenfromlogin);
                    cookAdapter.notifyDataSetChanged();




                }

            }














        }
        else

        {
            lyt_failed.setVisibility(View.VISIBLE);
            hideDialog();
        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            finish();
            Intent intent=new Intent(SearchResturant.this,MainActivity.class);
            startActivity(intent);


            return true;

        }
        if (item.getItemId() == R.id.nav_home) {
            Intent intent=new Intent(SearchResturant.this, MainActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);

    }





    public void getResturant(final  String access_token) {
        //showDialog();
        resturantswip.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.allrestaurant,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                            boolean status=task_respnse.getBoolean("status");
                            JSONArray taskarray = task_respnse.getJSONArray("data");
                            for(int i=0;i<taskarray.length();i++) {
                                int id=taskarray.getJSONObject(i).getInt("id");
                                String name = taskarray.getJSONObject(i).getString("name");
                                int countryId = taskarray.getJSONObject(i).getInt("country_id");
                                String countryName = taskarray.getJSONObject(i).getString("country_name");
                                int cityId = taskarray.getJSONObject(i).getInt("city_id");
                                String cityName  = taskarray.getJSONObject(i).getString("city_name");
                                String region  = taskarray.getJSONObject(i).getString("region");
                                String avatarURL  = taskarray.getJSONObject(i).getString("avatar_url");
                                String description  = taskarray.getJSONObject(i).getString("description");
                                int followersNo  = taskarray.getJSONObject(i).getInt("followers_no");
                                Boolean  isFollowed=taskarray.getJSONObject(i).getBoolean("is_followed");
                                Boolean  isFavorite=taskarray.getJSONObject(i).getBoolean("is_favorite");

                                AllResturanrModel.DataBean dataBean=new AllResturanrModel.DataBean();
                                dataBean.setId(id);
                                dataBean.setCity_id(cityId);
                                dataBean.setAvatar_url(avatarURL);
                                dataBean.setCountry_name(countryName);
                                dataBean.setFollowers_no(followersNo);
                                dataBean.setId(id);
                                dataBean.setDescription(description);
                                dataBean.setRegion(region);
                                dataBean.setCity_name(cityName);
                                dataBean.setName(name);
                                dataBean.setIs_favorite(isFavorite);
                                dataBean.setIs_followed(isFollowed);
                                dataBean.setCountry_id(countryId);
                                dataBean.setId(id);
                                data1.add(dataBean);

                            }


                            RecyclerView.LayoutManager manager = new LinearLayoutManager(SearchResturant.this);
                            cookrecycler.setLayoutManager(manager);
                            cookrecycler.setAdapter(cookAdapter);
                            cookAdapter.notifyDataSetChanged();
                            resturantswip.setRefreshing(false);
                            cookAdapter.notifyDataSetChanged();








                            // hideDialog();



                        } catch (JSONException e) {

                            e.printStackTrace();
                            //hideDialog();
                            resturantswip.setRefreshing(false);


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //hideDialog();
                resturantswip.setRefreshing(false);


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                return headers;


            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                return headers;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }


    @Override
    public void onContactSelected(AllResturanrModel.DataBean contact) {
        Intent intent =new Intent(SearchResturant.this, ResturantPageActivity.class);
        intent.putExtra(Constants.cook_name,contact.getName());
        intent.putExtra(Constants.cook_address,contact.getCity_name());
        intent.putExtra(Constants.cook_desc,contact.getRegion());
        intent.putExtra(Constants.cookimage,contact.getAvatar_url());
        intent.putExtra(Constants.countfollow,contact.getFollowers_no());
        intent.putExtra(Constants.ressturant_id,contact.getId());
        intent.putExtra(Constants.cook_counuty_id,contact.getCountry_id());
        intent.putExtra(Constants.cook_county,contact.getCountry_name());
        intent.putExtra(Constants.cook_city,contact.getCity_name());
        intent.putExtra(Constants.cook_region,contact.getRegion());
        intent.putExtra(Constants.cookimage,contact.getAvatar_url());
        intent.putExtra(Constants.countfollow,contact.getFollowers_no());
        startActivity(intent);
    }


    public void getResturantPagination(final  String access_token) {
        //showDialog();
        resturantswip.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.allrestaurant+"?page="+page_number,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                            boolean status=task_respnse.getBoolean("status");
                            JSONArray taskarray = task_respnse.getJSONArray("data");
                            page_number=task_respnse.getInt("current_page");
                            total_item_count=task_respnse.getInt("total");

                            for(int i=0;i<taskarray.length();i++) {
                                int id=taskarray.getJSONObject(i).getInt("id");
                                String name = taskarray.getJSONObject(i).getString("name");
                                int countryId = taskarray.getJSONObject(i).getInt("country_id");
                                String countryName = taskarray.getJSONObject(i).getString("country_name");
                                int cityId = taskarray.getJSONObject(i).getInt("city_id");
                                String cityName  = taskarray.getJSONObject(i).getString("city_name");
                                String region  = taskarray.getJSONObject(i).getString("region");
                                String avatarURL  = taskarray.getJSONObject(i).getString("avatar_url");
                                String description  = taskarray.getJSONObject(i).getString("description");
                                int followersNo  = taskarray.getJSONObject(i).getInt("followers_no");
                                Boolean  isFollowed=taskarray.getJSONObject(i).getBoolean("is_followed");
                                Boolean  isFavorite=taskarray.getJSONObject(i).getBoolean("is_favorite");

                                AllResturanrModel.DataBean dataBean=new AllResturanrModel.DataBean();
                                dataBean.setId(id);
                                dataBean.setCity_id(cityId);
                                dataBean.setAvatar_url(avatarURL);
                                dataBean.setCountry_name(countryName);
                                dataBean.setFollowers_no(followersNo);
                                dataBean.setId(id);
                                dataBean.setDescription(description);
                                dataBean.setRegion(region);
                                dataBean.setCity_name(cityName);
                                dataBean.setName(name);
                                dataBean.setIs_favorite(isFavorite);
                                dataBean.setIs_followed(isFollowed);
                                dataBean.setCountry_id(countryId);
                                dataBean.setId(id);
                                cookAdapter.addResturant(dataBean);
                                cookAdapter.notifyDataSetChanged();
                                // data1.add(dataBean);

                            }



                            // cookrecycler.setAdapter(cookAdapter);
                            cookAdapter.notifyDataSetChanged();
                            resturantswip.setRefreshing(false);
                            cookAdapter.notifyDataSetChanged();







                            // hideDialog();



                        } catch (JSONException e) {

                            e.printStackTrace();
                            //hideDialog();
                            resturantswip.setRefreshing(false);


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //hideDialog();
                resturantswip.setRefreshing(false);


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                return headers;


            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                return headers;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }



    public void getAllResturantPagination() {
        //showDialog();
        resturantswip.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.allrestaurant+"?page="+page_number,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                            boolean status=task_respnse.getBoolean("status");
                            JSONArray taskarray = task_respnse.getJSONArray("data");
                            page_number=task_respnse.getInt("current_page");
                            total_item_count=task_respnse.getInt("total");

                            for(int i=0;i<taskarray.length();i++) {
                                int id=taskarray.getJSONObject(i).getInt("id");
                                String name = taskarray.getJSONObject(i).getString("name");
                                int countryId = taskarray.getJSONObject(i).getInt("country_id");
                                String countryName = taskarray.getJSONObject(i).getString("country_name");
                                int cityId = taskarray.getJSONObject(i).getInt("city_id");
                                String cityName  = taskarray.getJSONObject(i).getString("city_name");
                                String region  = taskarray.getJSONObject(i).getString("region");
                                String avatarURL  = taskarray.getJSONObject(i).getString("avatar_url");
                                String description  = taskarray.getJSONObject(i).getString("description");
                                int followersNo  = taskarray.getJSONObject(i).getInt("followers_no");
                                AllResturanrModel.DataBean dataBean=new AllResturanrModel.DataBean();
                                dataBean.setId(id);
                                dataBean.setCity_id(cityId);
                                dataBean.setAvatar_url(avatarURL);
                                dataBean.setCountry_name(countryName);
                                dataBean.setFollowers_no(followersNo);
                                dataBean.setId(id);
                                dataBean.setDescription(description);
                                dataBean.setRegion(region);
                                dataBean.setCity_name(cityName);
                                dataBean.setName(name);
                                dataBean.setCountry_id(countryId);
                                dataBean.setId(id);
                                // Toast.makeText(getActivity(), "page"+page_number, Toast.LENGTH_SHORT).show();

                                cookAdapter.addResturant(dataBean);
                                cookAdapter.notifyDataSetChanged();
                                // data1.add(dataBean);

                            }


                            cookAdapter.notifyDataSetChanged();
                            resturantswip.setRefreshing(false);







                            // hideDialog();



                        } catch (JSONException e) {

                            e.printStackTrace();
                            //hideDialog();
                            resturantswip.setRefreshing(false);


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //hideDialog();
                resturantswip.setRefreshing(false);


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                return headers;


            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                return headers;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }

}



