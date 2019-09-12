package com.livecook.livecookapp.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Adapter.CookAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Fragment.CookFilterFragment;
import com.livecook.livecookapp.Fragment.CookFiltererrFragment;
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

public class CookFilterrActivity extends AppCompatActivity {

    private EditText mEdname;
    private Spinner mCountryname;
    private Spinner mCityname;
    private EditText mRegion;
    private Spinner mCountrycode;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    int country_id=5;
    int city_id=0;
    int country_codee;
    boolean country_spinner_clicked=false;
    boolean city_spinner_clicked=false;
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
    Button  restfilte;
    int type_id=6;

    private static  Boolean ISLOGIN;
    RecyclerView cookrecycler;
    ArrayList<CookModel> data2 = new ArrayList<>();
    SwipeRefreshLayout cookswip;
    CookAdapter cookAdapter;
    CookModel cookModel;
    ProgressDialog progressDialog;
    ArrayList<AllcookModel.DataBean> data1 = new ArrayList<>();
    Button btn_failed_retry;

    View lyt_failed;
    SharedPreferences prefs;
    String tokenfromlogin;
    String typnumer;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_cook_filterr);

       // hideKeyboard(CookFilterrActivity.this);
        toolbar =  findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(toolbar);


        toolbar.setTitle(getString(R.string.search_cook));
        mEdname = findViewById(R.id.edname);
        mCountryname =findViewById(R.id.countryname);
        mCityname = findViewById(R.id.cityname);
        mRegion = findViewById(R.id.mRegion);
        radioGroup=findViewById(R.id.radiogroup);
        restfilte=findViewById(R.id.restfilte);

        back = findViewById(R.id.bacck);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(CookFilterrActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });









        prefs= getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");


        cookrecycler = findViewById(R.id.cookrecycler);
        cookAdapter = new CookAdapter(data1, CookFilterrActivity.this);

        //editsearch = view.findViewById(R.id.simpleSearchView);
        progressDialog = new ProgressDialog(CookFilterrActivity.this);
        lyt_failed  =findViewById(R.id.lyt_failed_home);
        btn_failed_retry = lyt_failed.findViewById(R.id.failed_retry);
        cookswip = findViewById(R.id.cookswip);
        cookswip.setColorSchemeResources
                (R.color.colorPrimary, android.R.color.holo_green_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_blue_dark);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final RadioButton radioButton=findViewById(checkedId);
                type=radioButton.getText().toString();
                if(type.matches("ذبائح")){

               type_id=5;
                   // Toast.makeText(CookFilterrActivity.this, ""+type_id, Toast.LENGTH_SHORT).show();
                }
                else if(type.matches("مستقل")){
                    type_id=6;
                   // Toast.makeText(CookFilterrActivity.this, ""+type_id, Toast.LENGTH_SHORT).show();


                }



            }
        });




mCountryname.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        country_spinner_clicked=true;

        getCountries();
        return false;
    }
});





   mCountryname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_spinner_clicked=true;
             country_id = data.get(position).getId();
             country_codee = Integer.parseInt(data.get(position).getCode());
            //  Toast.makeText(CookFilterrActivity.this, ""+country_id, Toast.LENGTH_SHORT).show();

             //getCities(country_id);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                country_id= Integer.parseInt("");

            }
        });

        mCityname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                city_spinner_clicked=true;
                getCities(country_id);
                return false;
            }
        });

        mCityname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city_spinner_clicked=true;
                city_id = city.get(position).getId();
               // Toast.makeText(CookFilterrActivity.this, ""+city.get(position).getId(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city_id= Integer.parseInt("");

            }
        });


        restfilte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if (!mEdname.getText().toString().isEmpty()) {
                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, mEdname.getText().toString() + "");
                            intent.putExtra(Constants.filtercity_id, "");
                            intent.putExtra(Constants.filtercountry_id, "");
                            intent.putExtra(Constants.filterregion, "");
                            startActivity(intent);
                            finish();
                            //getCooker(type_id,"","","",mEdname.getText().toString()+"");


                        } else if (country_spinner_clicked) {
                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, "");
                            intent.putExtra(Constants.filtercity_id, "");
                            intent.putExtra(Constants.filterregion, "");
                            intent.putExtra(Constants.filtercountry_id, country_id + "");
                            startActivity(intent);
                            finish();
                            //getCooker(type_id,country_id+"","","","");


                        } else if (city_spinner_clicked) {
                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtercountry_id, "");
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, "");
                            intent.putExtra(Constants.filtercity_id, city_id + "");
                            intent.putExtra(Constants.filterregion, "");
                            intent.putExtra(Constants.filtercountry_id, "");
                            startActivity(intent);
                            finish();
                            // getCooker(type_id,"",city_id+"","","");


                        } else if (!mRegion.getText().toString().isEmpty()) {
                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtercountry_id, "");
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, "");
                            intent.putExtra(Constants.filtercity_id, city_id + "");
                            intent.putExtra(Constants.filterregion, mRegion.getText().toString());
                            intent.putExtra(Constants.filtercountry_id, "");
                            startActivity(intent);
                            finish();


                            // getCooker(type_id,"",city_id+"",mRegion.getText().toString(),"");


                        } else if (country_spinner_clicked && city_spinner_clicked) {
                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtercountry_id, country_id + "");
                            intent.putExtra(Constants.filtertype_id, 0);
                            intent.putExtra(Constants.filtername, mEdname.getText().toString() + "");
                            intent.putExtra(Constants.filtercity_id, city_id + "");
                            intent.putExtra(Constants.filterregion, mRegion.getText().toString());
                            intent.putExtra(Constants.filtercountry_id, country_id + "");
                            startActivity(intent);
                            finish();
                            //getCooker(0,country_id+"",city_id+"",mRegion.getText().toString()+"",
                            //  mEdname.getText().toString()+"");

                        } else if (country_spinner_clicked && city_spinner_clicked & !mRegion.getText().toString().isEmpty() & !mEdname.getText().toString().isEmpty()) {


                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtercountry_id, country_id + "");
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, mEdname.getText().toString() + "");
                            intent.putExtra(Constants.filtercity_id, city_id + "");
                            intent.putExtra(Constants.filterregion, mRegion.getText().toString());
                            intent.putExtra(Constants.filtercountry_id, country_id + "");
                            startActivity(intent);
                            finish();

                            // getCooker(type_id,country_id+"",city_id+"",
                            //    mRegion.getText().toString()+"",mEdname.getText().toString()+"");

                        } else if (!country_spinner_clicked && !city_spinner_clicked) {
                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtercountry_id, "");
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, mEdname.getText().toString() + "");
                            intent.putExtra(Constants.filtercity_id, "");
                            intent.putExtra(Constants.filterregion, mRegion.getText().toString());
                            intent.putExtra(Constants.filtercountry_id, country_id + "");
                            startActivity(intent);
                            finish();


                            //getCooker(type_id,"","",mRegion.getText().toString()+"",mEdname.getText().toString()+"");

                        } else if (country_spinner_clicked) {

                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtercountry_id, country_id + "");
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, mEdname.getText().toString() + "");
                            intent.putExtra(Constants.filtercity_id, "");
                            intent.putExtra(Constants.filterregion, mRegion.getText().toString());
                            intent.putExtra(Constants.filtercountry_id, country_id + "");
                            startActivity(intent);
                            finish();

                            //getCooker(type_id,country_id+"", "",mRegion.getText().toString()+"",mEdname.getText().toString()+"");


                        } else if (city_spinner_clicked) {
                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtercountry_id, "");
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, mEdname.getText().toString() + "");
                            intent.putExtra(Constants.filtercity_id, "");
                            intent.putExtra(Constants.filterregion, mRegion.getText().toString());
                            intent.putExtra(Constants.filtercountry_id, "");
                            startActivity(intent);
                            finish();

                            // getCooker(type_id,"","",mRegion.getText().toString()+"",mEdname.getText().toString()+"");


                        } else if (mEdname != null) {
                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtercountry_id, "");
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, mEdname.getText().toString() + "");
                            intent.putExtra(Constants.filtercity_id, "");
                            intent.putExtra(Constants.filterregion, mRegion.getText().toString());
                            intent.putExtra(Constants.filtercountry_id, "");
                            startActivity(intent);
                            finish();


                            //getCooker(type_id,"","",mRegion.getText().toString()+"",mEdname.getText().toString());


                        } else if (mRegion != null) {

                            Intent intent = new Intent(CookFilterrActivity.this, FilterResultActivity.class);
                            intent.putExtra(Constants.filtercountry_id, "");
                            intent.putExtra(Constants.filtertype_id, type_id);
                            intent.putExtra(Constants.filtername, "");
                            intent.putExtra(Constants.filtercity_id, "");
                            intent.putExtra(Constants.filterregion, mRegion.getText().toString());
                            intent.putExtra(Constants.filtercountry_id, "");
                            startActivity(intent);
                            finish();

                            // getCooker(type_id,"","",mRegion.getText().toString()+"","");


                        }


                }
















        });




    }


    private void checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) CookFilterrActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

           /* data1.clear();
            getCooker(tokenfromlogin);
            cookAdapter.notifyDataSetChanged();*/

            if (prefs != null) {
                typnumer = prefs.getString(Constants.TYPE, "user");
                ISLOGIN=prefs.getBoolean(Constants.ISLOGIN,false);
               // Toast.makeText(CookFilterrActivity.this, "", Toast.LENGTH_SHORT).show();


              //  Toast.makeText(CookFilterrActivity.this, "type " + typnumer, Toast.LENGTH_SHORT).show();



               /* if (!ISLOGIN) {
                    data1.clear();
                    getCookerforall();
                    cookAdapter.notifyDataSetChanged();
                }
                else {
                    data1.clear();
                    getCooker(tokenfromlogin);
                    cookAdapter.notifyDataSetChanged();


                }*/



                if (typnumer.matches("cooker")) {
                    data1.clear();
                    getCooker(type_id,country_id+"",city_id+"",mEdname.getText().toString(),mRegion.getText().toString());
                    cookAdapter.notifyDataSetChanged();
                } else if (typnumer.matches("restaurant")) {

                    data1.clear();
                    getCooker(type_id,country_id+"",city_id+"",mEdname.getText().toString(),mRegion.getText().toString());
                    cookAdapter.notifyDataSetChanged();

                } else if (typnumer.matches("user")) {

                    data1.clear();
                    getCooker(type_id,country_id+"",city_id+"",mEdname.getText().toString(),mRegion.getText().toString());
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


    public void getCookerforall() {
        cookswip.setRefreshing(true);

        //showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.allcooker,
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

                            }


                            RecyclerView.LayoutManager manager = new LinearLayoutManager(CookFilterrActivity.this);
                            cookAdapter = new CookAdapter(data1, CookFilterrActivity.this);
                            cookrecycler.setLayoutManager(manager);
                            cookrecycler.setAdapter(cookAdapter);
                            cookAdapter.notifyDataSetChanged();
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


                            RecyclerView.LayoutManager manager = new GridLayoutManager(CookFilterrActivity.this,1);
                            cookAdapter = new CookAdapter(data1, CookFilterrActivity.this);
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


    public void showDialog() {
        progressDialog = new ProgressDialog(CookFilterrActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }


    public void getCountries() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://livecook.co/api/v1/constant/countries", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String nationality = jsonObject.getString("nationality");
                        String sort_name = jsonObject.getString("sort_name");
                        String code = jsonObject.getString("code");
                        Datum datum = new Datum();
                        datum.setId(id);
                        datum.setName(name);
                        datum.setCode(code);
                        datum.setSortName(sort_name);
                        datum.setNationality(nationality);
                        data.add(datum);
                        countrylist.add(name);


                    }
                    arrayAdapter = new ArrayAdapter<String>(CookFilterrActivity.this,
                            android.R.layout.simple_list_item_1, countrylist);
                    mCountryname.setAdapter(arrayAdapter);

                    arrayAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }



    public void getEmptyCountries() {

                countrylist.add(" ");

                    arrayAdapter = new ArrayAdapter<String>(CookFilterrActivity.this,android.R.layout.simple_list_item_1, countrylist);
                    mCountryname.setAdapter(arrayAdapter);

                    arrayAdapter.notifyDataSetChanged();


    }


    public void getCities(final int country_id) {
        citylist.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.cities_url+country_id, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        Datum cities = new Datum();
                        cities.setId(id);
                        cities.setName(name);
                        city.add(cities);
                        citylist.add(name);


                    }
                    cityadapter = new ArrayAdapter<String>(CookFilterrActivity.this, android.R.layout.simple_list_item_1, citylist);

                    mCityname.setAdapter(cityadapter);
                    cityadapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }













    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
