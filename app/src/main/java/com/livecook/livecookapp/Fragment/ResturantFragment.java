package com.livecook.livecookapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Adapter.ResturantAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.AllResturanrModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.CookModel;
import com.livecook.livecookapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ResturantFragment extends Fragment {
    RecyclerView cookrecycler;
    ArrayList<CookModel> data = new ArrayList<>();
    SwipeRefreshLayout resturantswip;
    ResturantAdapter cookAdapter;
    CookModel cookModel;
    ProgressDialog progressDialog;
    ArrayList<AllResturanrModel.DataBean> data1 = new ArrayList<>();
    Button btn_failed_retry;
    SharedPreferences prefs;
    String tokenfromlogin;

    View lyt_failed;
    String typnumer;
    private static  Boolean ISLOGIN;
    LinearLayoutManager manager;
    int  total_item_count=0;
    private  boolean isLoading=true;
    int past_visble_items, visibleItemCount,previous_total=0;
    private  int view_thershold=6;
    int page_number=1;
    private boolean loading;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resturant, container, false);
        progressDialog = new ProgressDialog(getActivity());

        cookrecycler = view.findViewById(R.id.cookrecycler);
        //editsearch = view.findViewById(R.id.simpleSearchView);
        cookAdapter = new ResturantAdapter(data1, getActivity());
        lyt_failed  = view.findViewById(R.id.lyt_failed_home);
        btn_failed_retry = lyt_failed.findViewById(R.id.failed_retry);
        resturantswip = view.findViewById(R.id.resturantswip);
        resturantswip.setColorSchemeResources
                (R.color.colorPrimary, android.R.color.holo_green_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_blue_dark);

        prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");
        manager = new LinearLayoutManager(getActivity());
        cookrecycler.setLayoutManager(manager);
        cookrecycler.setHasFixedSize(true);


        resturantswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resturantswip.setRefreshing(true);

                checkInternet();



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
                        Intent intent= new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                       // Fragment myFragment = new CookFragment();
                        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, myFragment).addToBackStack(null).commit();


                    }
                }, 1000);
            }

        });
        checkInternet();

        return view;
    }

    @Override
    public void onStart() {
        cookAdapter.notifyDataSetChanged();



        super.onStart();
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
                                    data1.add(dataBean);

                                }



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
                                data1.add(dataBean);

                            }


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



    public void showDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }
    private void checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {



            if (prefs != null) {
                typnumer = prefs.getString(Constants.TYPE, "user");
                ISLOGIN=prefs.getBoolean(Constants.ISLOGIN,false);
                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();


                //Toast.makeText(getActivity(), "type " + typnumer, Toast.LENGTH_SHORT).show();




                 if (typnumer.matches("cooker")) {
                    data1.clear();
                    getAllResturant();
                    cookAdapter.notifyDataSetChanged();
                } else if (typnumer.matches("restaurant")) {

                    data1.clear();
                    getAllResturant();
                    cookAdapter.notifyDataSetChanged();

                } else if (typnumer.matches("user")) {


                     if (ISLOGIN) {
                         data1.clear();
                         getResturant(tokenfromlogin);
                         cookAdapter.notifyDataSetChanged();
                     }
                     else {

                         data1.clear();
                         getAllResturant();
                         cookAdapter.notifyDataSetChanged();
                     }



                }

            }
            //getTask();
        }
        else

        {
            lyt_failed.setVisibility(View.VISIBLE);
            hideDialog();
        }
    }


}
