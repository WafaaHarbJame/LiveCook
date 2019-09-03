package com.livecook.livecookapp.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Adapter.CookAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
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

public class FilterResultActivity extends AppCompatActivity {



    int country_id=5;
    ArrayAdapter<String> countrycodeadapter;
    List<String> countrycodelist = new ArrayList<String>();
    ArrayList<Datum> data = new ArrayList<>();
    ArrayList<Datum> city = new ArrayList<>();
    private Toolbar toolbar;
    Button restfilte;
    int type_id=6;

    private static  Boolean ISLOGIN;
    RecyclerView cookrecycler;
    ArrayList<CookModel> data2 = new ArrayList<>();
    SwipeRefreshLayout cookswip;
    CookAdapter cookAdapter;
    CookModel cookModel;
    ProgressDialog progressDialog;
    ArrayList<AllcookModel.DataBean> data1 = new ArrayList<>();
    SharedPreferences prefs;
    ImageView no_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("نتائج البحث");
        ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        no_result=findViewById(R.id.textView_noresult);


        cookrecycler = findViewById(R.id.cookrecycler);
        cookAdapter = new CookAdapter(data1, FilterResultActivity.this);
        progressDialog = new ProgressDialog(FilterResultActivity.this);
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






    public void getCooker(final  int type_id,final String country_id,final String city_id,final String region,final String name) {
        cookswip.setRefreshing(true);

        //showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://livecook.co/api/v1/cooker/all?type_id="+type_id+"&country_id="+country_id+"&city_id="
                        +city_id+"&region="+region+"&name="+name,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(CookFilterrActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                            boolean status=task_respnse.getBoolean("status");
                            // Toast.makeText(CookFilterrActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                            JSONArray taskarray = task_respnse.getJSONArray("data");
                            //Toast.makeText(CookFilterrActivity.this, ""+taskarray, Toast.LENGTH_SHORT).show();

                            for(int i=0;i<taskarray.length();i++) {

                                // Toast.makeText(CookFilterrActivity.this, "فاث قثس"+task_respnse, Toast.LENGTH_SHORT).show();
                                int id=taskarray.getJSONObject(i).getInt("id");
                                String name = taskarray.getJSONObject(i).getString("name");
                                int type_id=taskarray.getJSONObject(i).getInt("type_id");
                                String type_name = taskarray.getJSONObject(i).getString("type_name");
                                int countryId = taskarray.getJSONObject(i).getInt("country_id");
                                String countryName = taskarray.getJSONObject(i).getString("country_name");
                                int cityId = taskarray.getJSONObject(i).getInt("city_id");
                                String cityName  = taskarray.getJSONObject(i).getString("city_name");
                                String region  = taskarray.getJSONObject(i).getString("region");
                                String avatarURL  = taskarray.getJSONObject(i).getString("avatar_url");
                                String avatar= taskarray.getJSONObject(i).getString("avatar");
                                String description  = taskarray.getJSONObject(i).getString("description");
                                int followersNo  = taskarray.getJSONObject(i).getInt("followers_no");
                                AllcookModel.DataBean dataBean=new AllcookModel.DataBean();
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


                            RecyclerView.LayoutManager manager = new GridLayoutManager(FilterResultActivity.this,1);
                            cookAdapter = new CookAdapter(data1, FilterResultActivity.this);
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


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("type_id" , String.valueOf(type_id));
                headers.put("country_id" , String.valueOf(country_id));
                headers.put("city_id" , String.valueOf(city_id));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent=new Intent(this, CookFilterrActivity.class);
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
