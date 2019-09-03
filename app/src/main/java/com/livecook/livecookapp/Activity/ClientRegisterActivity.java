package com.livecook.livecookapp.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.livecook.livecookapp.Adapter.DatumecountryAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.Cities;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.Datum;
import com.livecook.livecookapp.R;
import com.vikktorn.picker.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText edname;
    private EditText edmobileNumber;
    private EditText edpassward;
    private EditText edrepatepassward;
    private ImageView insertliscense;
    SharedPreferences.Editor editor;
    public String access_token;
    Datum datum;

    private Button createaccount;
    ArrayList<Datum> data = new ArrayList<>();
    ArrayList<Datum> city = new ArrayList<>();

    List<String> countrylist = new ArrayList<String>();
    List<String> citylist = new ArrayList<String>();
    ArrayList<Datum> countrycodelist = new ArrayList<Datum>();

    int country_id;
    int country_codee;
    String country_mobile_codee;
    int countrycode_reg;


    Spinner spin;
    Spinner cityname;
    Spinner countrycode;

    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> cityadapter;
    DatumecountryAdapter  countrycodeadapter;
    SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);


        setContentView(R.layout.register_user);
        initViews();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");

        spin = findViewById(R.id.countryname);
        cityname = findViewById(R.id.cityname);
        countrycode = findViewById(R.id.countrycode);
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

        editor = prefs.edit();
        showSoftKeyboard(edname);

        countrycode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_mobile_codee = countrycode.getSelectedItem().toString();
                countrycode_reg = data.get(position).getId();
               // Toast.makeText(ClientRegisterActivity.this, "selected" + countrycode.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(ClientRegisterActivity.this, "code" + data.get(position).getCode(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(ClientRegisterActivity.this, "id" + data.get(position).getId(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getCountries();
        getCountrycode();

         createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (edname.getText().toString().matches("")) {
                    Toast.makeText(ClientRegisterActivity.this, getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                }
                if (edmobileNumber.getText().toString().matches("")) {
                    Toast.makeText(ClientRegisterActivity.this, getString(R.string.enter_mobile), Toast.LENGTH_SHORT).show();
                } else if (edpassward.getText().toString().matches("")) {
                    Toast.makeText(ClientRegisterActivity.this, getString(R.string.enter_passward), Toast.LENGTH_SHORT).show();
                }



                else {
                    registeruser(edname.getText().toString(),  edmobileNumber.getText().toString(), countrycode_reg, edpassward.getText().toString(), edpassward.getText().toString());


                }


            }
        });


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_id = data.get(position).getId();
                country_codee = data.get(position).getId();
             Toast.makeText(ClientRegisterActivity.this, "" + data.get(position).getCode(), Toast.LENGTH_SHORT).show();
               Toast.makeText(ClientRegisterActivity.this, "" + data.get(position).getId(), Toast.LENGTH_SHORT).show();

                getCities(country_id);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void getCountrycode() {
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
                        String flag = jsonObject.getString("flag");

                         datum = new Datum();
                        datum.setId(id);
                        datum.setName(name);
                        datum.setCode(code);
                        datum.setSortName(sort_name);
                        datum.setNationality(nationality);
                        datum.setFlag(flag);
                        data.add(datum);
                        countrycodelist.add(datum);


                    }
                    countrycodeadapter = new DatumecountryAdapter(ClientRegisterActivity.this, countrycodelist);
                    countrycode.setAdapter(countrycodeadapter);

                    countrycodeadapter.notifyDataSetChanged();


                   // spin.setSelection(countrycodeadapter.getItemIndexById("972"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //JSONArray taskarray=response.getJSONArray("task");

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
                    arrayAdapter = new ArrayAdapter<String>(ClientRegisterActivity.this, android.R.layout.simple_list_item_1, countrylist);
                    spin.setAdapter(arrayAdapter);

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

    public void getCities(final int country_id) {
        citylist.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.cities_url + country_id, null, new Response.Listener<JSONObject>() {

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
                    cityadapter = new ArrayAdapter<String>(ClientRegisterActivity.this, android.R.layout.simple_list_item_1, citylist);

                    cityname.setAdapter(cityadapter);
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

    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        edmobileNumber = findViewById(R.id.edmobile_number);
        edname = findViewById(R.id.edname);
        edpassward = findViewById(R.id.edpassward);
        createaccount = findViewById(R.id.createaccount);
        edrepatepassward = findViewById(R.id.edrepatepassward);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(ClientRegisterActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
            return true;

        }

        if (item.getItemId() == R.id.nav_home) {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);

    }


    public void registeruser(final String name, final String mobile, final int country_id, final String password, final String password_confirmation) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.user_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    JSONObject auth=register_response.getJSONObject("auth");
                    int user_id=auth.getInt("id");
                    String mobile=auth.getString("mobile");
                    access_token=register_response.getString("accessToken");

                    if (status) {
                        Toast.makeText(ClientRegisterActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();

                        editor = prefs.edit();
                        editor.putString(Constants.password,edpassward.getText().toString());
                        editor.putString(Constants.passwordintent,edpassward.getText().toString());
                        editor.putString(Constants.access_token1, access_token);
                        editor.putString(Constants.TYPE, "user");
                        editor.putInt(Constants.user_id, user_id);
                        editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                        editor.putString(Constants.password, edpassward.getText().toString());
                        editor.putInt(Constants.user_id, user_id);
                        editor.putString(Constants.access_token1, access_token);
                        editor.putString(Constants.TYPE, "user");
                        editor.putBoolean(Constants.ISLOGIN, true);
                        editor.putInt(Constants.user_id, user_id);
                        editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                        editor.putString(Constants.password, edpassward.getText().toString());
                        editor.apply();
                        editor.commit();

                        loginuser(country_id,edmobileNumber.getText().toString(),edpassward.getText().toString(),0,access_token);
                        //startActivity(gotologin);

                    } else {
                        Toast.makeText(ClientRegisterActivity.this, "قيمة الجوال مستتخدمة من قبل ", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ClientRegisterActivity.this, "قيمة الجوال مستتخدمة من قبل ", Toast.LENGTH_SHORT).show();



                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ClientRegisterActivity.this, "قيمة الجوال مستتخدمة من قبل ", Toast.LENGTH_SHORT).show();



            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("name", name);
                map.put("mobile", mobile);
                map.put("country_id", country_id + "");
                map.put("password", password);
                map.put("password_confirmation", password_confirmation);


                return map;

            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }




    public void loginuser(final int country_id, final String mobile, final String password, final int remember_me, final String fcm_token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    JSONObject auto = register_response.getJSONObject("auth");

                    boolean status = register_response.getBoolean("status");
                    access_token = register_response.getString("access_token");
                    String type = register_response.getString("type");
                    int user_id = auto.getInt("id");
                    if (status) {
                        Intent gotologin = new Intent(ClientRegisterActivity.this, MainActivity.class);
                        editor = prefs.edit();
                        editor.putString(Constants.access_token1, access_token);
                        editor.putString(Constants.TYPE, type);
                        editor.putInt(Constants.user_id, user_id);
                        editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                        editor.putString(Constants.password, edpassward.getText().toString());
                        gotologin.putExtra(Constants.access_token1, access_token);
                        gotologin.putExtra(Constants.TYPE, type);
                        editor.putInt(Constants.user_id, user_id);
                        editor.putString(Constants.access_token1, access_token);
                        editor.putString(Constants.TYPE, type);
                        editor.putBoolean(Constants.ISLOGIN, true);
                        editor.putInt(Constants.user_id, user_id);
                        editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                        editor.putString(Constants.password, edpassward.getText().toString());
                        editor.apply();
                        editor.commit();
                        startActivity(gotologin);
                        finish();


                    } else {
                        Toast.makeText(ClientRegisterActivity.this, "لم يتم الدخول", Toast.LENGTH_SHORT).show();

                    }
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("country_id", country_id + "");
                map.put("mobile", mobile);
                map.put("password", password);
                map.put("remember_me", remember_me + "");
                map.put("fcm_token", fcm_token);


                return map;

            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }





    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


    private int indexOf(final Adapter adapter, Object value)
    {
        for (int index = 0, count = adapter.getCount(); index < count; ++index)
        {
            if (adapter.getItem(index).equals(value))
            {
                return index;
            }
        }
        return -1;
    }

    public int getItemIndexById(String id) {
        for (Datum item : this.countrycodelist) {
            if(item.getCode().equals(id)){
                return this.countrycodelist.indexOf(item);
            }
        }
        return 0;
    }


}