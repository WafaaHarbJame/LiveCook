package com.livecook.livecookapp.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Activity.ChangePasswardActivity;
import com.livecook.livecookapp.Adapter.CookAdapter;
import com.livecook.livecookapp.Adapter.NotificationAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Model.AllcookModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.Notification;
import com.livecook.livecookapp.Model.NotificationModel;
import com.livecook.livecookapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    RecyclerView nOtificationrecycler;
    SwipeRefreshLayout nOtificationreswip;
    NotificationAdapter notificationAdapter;
    Notification notification;
    ArrayList<NotificationModel.DataBean> data1 = new ArrayList<>();
    SharedPreferences prefs;
    String tokenfromlogin;
    private Boolean saveLogin;
    String typnumer;
    ImageView no_result;





    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);

        nOtificationrecycler = view.findViewById(R.id.nOtificationrecycler);
        //editsearch = view.findViewById(R.id.simpleSearchView);

        nOtificationreswip = view.findViewById(R.id.nOtificationreswip);
        nOtificationreswip.setColorSchemeResources
                (R.color.colorPrimary, android.R.color.holo_green_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_blue_dark);
        prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");

        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);

        nOtificationrecycler.setLayoutManager(manager);
        no_result=view.findViewById(R.id.textView_noresult);



        if (prefs != null) {
            typnumer = prefs.getString(Constants.TYPE, "user");

             if (typnumer.matches("user")) {
                getUserNotification(Constants.user_notification,tokenfromlogin);




            }

        }





        return view;
    }





    public void getUserNotification(final  String link,final  String access_token) {
        nOtificationreswip.setRefreshing(true);

        //showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                          //  Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                            boolean status=task_respnse.getBoolean("status");
                            JSONArray taskarray = task_respnse.getJSONArray("data");
                            for(int i=0;i<taskarray.length();i++) {
                                String  message=taskarray.getJSONObject(i).getString("message");
                                String type = taskarray.getJSONObject(i).getString("type");
                                String created_at = taskarray.getJSONObject(i).getString("created_at");
                                String avatar_url = taskarray.getJSONObject(i).getString("avatar_url");
                                String title = taskarray.getJSONObject(i).getString("title");
                                String avatar = taskarray.getJSONObject(i).getString("avatar");

                                NotificationModel.DataBean  dataBean=new NotificationModel.DataBean();
                                dataBean.setMessage(message);
                                dataBean.setCreated_at(created_at);
                                dataBean.setTitle(title);
                                dataBean.setAvatar_url(avatar_url);
                                dataBean.setAvatar(avatar);
                                data1.add(dataBean);

                              //  Toast.makeText(getActivity(), ""+data1, Toast.LENGTH_SHORT).show();



                            }

                            if(data1.isEmpty()){
                                no_result.setVisibility(View.VISIBLE);


                            }


                            RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);
                            notificationAdapter = new NotificationAdapter(data1, getActivity());
                            nOtificationrecycler.setLayoutManager(manager);
                            nOtificationrecycler.setAdapter(notificationAdapter);
                            nOtificationreswip.setRefreshing(false);
                           notificationAdapter.notifyDataSetChanged();





                        } catch (JSONException e) {

                            e.printStackTrace();
                            //hideDialog();
                            nOtificationreswip.setRefreshing(false);
                            no_result.setVisibility(View.VISIBLE);



                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //hideDialog();
                nOtificationreswip.setRefreshing(false);


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









}
