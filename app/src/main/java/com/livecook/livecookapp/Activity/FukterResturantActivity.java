package com.livecook.livecookapp.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Adapter.ResturantAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.AllResturanrModel;
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

public class FukterResturantActivity extends AppCompatActivity {

    private EditText mEdname;
    private Spinner mCountryname;
    private Spinner mCityname;
    private EditText mRegion;
    private Spinner mCountrycode;
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
    boolean country_spinner_clicked=false;
    boolean city_spinner_clicked=false;
    int type_id=0;

    private static  Boolean ISLOGIN;
    RecyclerView cookrecycler;
    ArrayList<CookModel> data2 = new ArrayList<>();
    SwipeRefreshLayout cookswip;
    ResturantAdapter cookAdapter;
    CookModel cookModel;
    ProgressDialog progressDialog;
    ArrayList<AllResturanrModel.DataBean> data1 = new ArrayList<>();
    Button btn_failed_retry;

    View lyt_failed;
    SharedPreferences prefs;
    String tokenfromlogin;
    String typnumer;
    ImageView back;
    ImageView no_result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fukter_resturant);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("نتائج البحث");
        ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        no_result=findViewById(R.id.textView_noresult);


        cookAdapter = new ResturantAdapter(data1, FukterResturantActivity.this);

        cookrecycler = findViewById(R.id.cookrecycler);
        progressDialog = new ProgressDialog(FukterResturantActivity.this);
        cookswip = findViewById(R.id.cookswip);
        cookswip.setColorSchemeResources
                (R.color.colorPrimary, android.R.color.holo_green_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_blue_dark);
        Intent intent=getIntent();
        if(intent!=null) {
            int typeid = intent.getIntExtra(Constants.filtertype_id, 0);
            String filtername = intent.getStringExtra(Constants.filtername);
            String filtercity_id = intent.getStringExtra(Constants.filtercity_id);
            String filterregion = intent.getStringExtra(Constants.filterregion);
            String filtercountry_id = intent.getStringExtra(Constants.filtercountry_id);
            getCooker(typeid,filtercountry_id,filtercity_id,filterregion,filtername);

        }










    }


    private void checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) FukterResturantActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


        }
        else

        {
            lyt_failed.setVisibility(View.VISIBLE);
            hideDialog();
        }
    }



    public void getCooker(final  int type_id,final String country_id,final String city_id,final String region,final String name) {
        cookswip.setRefreshing(true);

        //showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://livecook.co/api/v1/restaurant/all?type_id="+type_id+"&country_id="+country_id+"&city_id="
                        +city_id+"&region="+region+"&name="+name,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


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
                                String avatar= taskarray.getJSONObject(i).getString("avatar");
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
                                // Toast.makeText(CookFilterrActivity.this, ""+data1, Toast.LENGTH_SHORT).show();


                            }
                            if(data1.isEmpty()){
                                no_result.setVisibility(View.VISIBLE);


                            }




                            RecyclerView.LayoutManager manager = new GridLayoutManager(FukterResturantActivity.this,1);
                            cookAdapter = new ResturantAdapter(data1, FukterResturantActivity.this);
                            cookrecycler.setLayoutManager(manager);
                            cookrecycler.setAdapter(cookAdapter);
                            cookswip.setRefreshing(false);
                            cookAdapter.notifyDataSetChanged();


                            //  hideDialog();



                        } catch (JSONException e) {

                            e.printStackTrace();
                            //hideDialog();
                            cookswip.setRefreshing(false);


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //hideDialog();
                cookswip.setRefreshing(false);


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("type_id" , type_id+"");
                headers.put("country_id" , country_id+"");
                headers.put("city_id" , city_id+"");
                headers.put("region" ,region+"");
                headers.put("name" ,name+"");

                return headers;


            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("type_id" ,type_id+"");
                headers.put("country_id" ,country_id+"");
                headers.put("city_id" ,city_id+"");
                headers.put("region" ,region+"");
                headers.put("name" ,name+"");

                return headers;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }


    public void showDialog() {
        progressDialog = new ProgressDialog(FukterResturantActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }



















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent=new Intent(this, ResturantFiltertActivity.class);
            startActivity(intent);
            return false;

        }
        if (item.getItemId() == R.id.nav_home) {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);

    }
}
