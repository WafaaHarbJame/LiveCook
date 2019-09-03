package com.livecook.livecookapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.livecook.livecookapp.Adapter.DatumecountryAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Fragment.AcoountSettingFragment;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.Datum;
import com.livecook.livecookapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginResturantActivity extends AppCompatActivity {

    private ScrollView mScrollView2;
    private Toolbar mToolbar;
    private TextView mCreateacoount;
    private TextView mTvmobile;
    private EditText mEdmobileNumber;
    private TextView mTxpassward;
    private EditText mEdpassward;
    private TextView mCreatenewaccount;
    private Button login;
    private CheckBox saveLoginCheckBox;
    private Boolean saveLogin;
    public String access_token;
    SharedPreferences prefs;
    int rememberme=0;
    String country_idd;
    ArrayList<Datum>countrycodelist = new ArrayList<Datum>();
    ImageView bacck;

    DatumecountryAdapter countrycodeadapter;
    int  countrycode_reg;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;




    String  country_id;
    String mobile,password;
    private static final String TAG = "tokenreg";
    Spinner countrycode ;
    String country_mobile_codee;
    ArrayList<Datum> data = new ArrayList<>();
    TextView changepassward;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_resturant);
        countrycode = findViewById(R.id.countrycode);

        //final String token = FirebaseInstanceId.getInstance().getToken();
        //Log.i(TAG, "FCM Registration Token: " + token);
        //Toast.makeText(this, ""+token, Toast.LENGTH_SHORT).show();
        countrycode = findViewById(R.id.countrycode);
        getCountrycode();
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

        editor = prefs.edit();
        progressDialog = new ProgressDialog(LoginResturantActivity.this);


        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        bacck=findViewById(R.id.bacck);
        bacck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginResturantActivity.this,LoginPageActivity.class);
                startActivity(intent);
                finish();

            }
        });



        countrycode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_mobile_codee=countrycode.getSelectedItem().toString();
                countrycode_reg=data.get(position).getId();
               // Toast.makeText(LoginResturantActivity.this, "selected"+countrycode.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(LoginResturantActivity.this, "code"+data.get(position).getCode(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(LoginResturantActivity.this, "id"+data.get(position).getId(), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                countrycode_reg=data.get(0).getId();


            }
        });




        changepassward=findViewById(R.id.changepassward);
        changepassward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologin=new Intent(LoginResturantActivity.this,ChangePasswardActivity.class);
                startActivity(gotologin);

            }
        });
        mToolbar = findViewById(R.id.toolbar);
        mCreateacoount = findViewById(R.id.createacoount);
        mTvmobile = findViewById(R.id.tvmobile);
        mEdmobileNumber = findViewById(R.id.edmobile_number);
        mTxpassward = findViewById(R.id.txpassward);
        mEdpassward = findViewById(R.id.edpassward);
        mCreatenewaccount = findViewById(R.id.createnewaccount);
        login = findViewById(R.id.login);

        mCreatenewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologin=new Intent(LoginResturantActivity.this,RegisterActivity.class);
                startActivity(gotologin);
                finish();

            }
        });

        getCountrycode();

        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        country_idd = prefs.getString(Constants.countrycode_reg, "");
        if(prefs!=null) {

            mobile = prefs.getString(Constants.mobile, "");
            password = prefs.getString(Constants.password, "");
            mEdmobileNumber.setText(mobile);
            mEdpassward.setText(password);
        }
        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        editor = prefs.edit();


        saveLoginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    rememberme=1;
                }

                else {
                    rememberme=0;


                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (mEdmobileNumber.getText().toString().matches("")) {
                    Toast.makeText(LoginResturantActivity.this, getString(R.string.enter_mobile), Toast.LENGTH_SHORT).show();
                } else if (mEdpassward.getText().toString().matches("")) {
                    Toast.makeText(LoginResturantActivity.this, getString(R.string.enter_passward), Toast.LENGTH_SHORT).show();
                }
                else {


                    if (saveLoginCheckBox.isChecked()) {
                        loginuser(countrycode_reg, mEdmobileNumber.getText().toString(), mEdpassward.getText().toString(), 1, "");


                    } else {
                        loginuser(countrycode_reg, mEdmobileNumber.getText().toString(), mEdpassward.getText().toString(), 0, "");


                    }

                }




            }
        });



    }
    public void getCountrycode(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://livecook.co/api/v1/constant/countries", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String nationality = jsonObject.getString("nationality");
                        String sort_name = jsonObject.getString("sort_name");
                        String code = jsonObject.getString("code");
                        String flag = jsonObject.getString("flag");

                        Datum datum=new Datum();
                        datum.setId(id);
                        datum.setName(name);
                        datum.setCode(code);
                        datum.setSortName(sort_name);
                        datum.setNationality(nationality);
                        datum.setFlag(flag);
                        data.add(datum);
                        countrycodelist.add(datum);


                    }
                    countrycodeadapter = new DatumecountryAdapter(LoginResturantActivity.this, countrycodelist);
                    countrycode.setAdapter(countrycodeadapter);

                    countrycodeadapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //JSONArray taskarray=response.getJSONArray("task");
                //for (int i = 0; i < taskarray.length(); i++) {



                /*
                try {
                    JSONArray array = response.getJSONArray(AppConstants.CONTACTS_KEY);
                    for(int i=0;i<array.length();i++){

                      //JSONObject jsonObject =   array.getJSONObject(i);
                      //jsonObject.get

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
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


    public void loginuser(final int  country_id,final  String mobile ,final String password,final int  remember_me,
                          final String fcm_token) {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.restaurant_login_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject register_response=new JSONObject(response);
                            JSONObject auth=register_response.getJSONObject("auth");
                            boolean status=register_response.getBoolean("status");
                            access_token=register_response.getString("access_token");
                            String type=register_response.getString("type");
                            int id=auth.getInt("id");


                            if(status){
                                editor = prefs.edit();
                                editor = prefs.edit();
                                editor.putString(Constants.access_token1, access_token);
                                editor.putString(Constants.TYPE,type);
                                editor.putInt(Constants.ressturant_id_profile_publish,id);
                                editor.putString(Constants.access_token1, access_token);
                                editor.putString(Constants.TYPE,type);
                                editor.putInt(Constants.ressturant_id_profile_publish,id);
                                editor.putInt(Constants.ressturant_id_profile_publish,id);
                                editor.putString(Constants.access_token1, access_token);
                                editor.putBoolean(Constants.ISLOGIN,true);
                                editor.putString(Constants.mobile, mEdmobileNumber.getText().toString());
                                editor.putString(Constants.password,mEdpassward.getText().toString());
                                editor.putInt(Constants.user_id,id);
                                editor.putString(Constants.mobile, mEdmobileNumber.getText().toString());
                                editor.putString(Constants.password,mEdpassward.getText().toString());
                                Intent gotologin=new Intent(LoginResturantActivity.this, MainActivity.class);
                                gotologin.putExtra(Constants.access_token1,access_token);
                                gotologin.putExtra(Constants.ressturant_id_profile_publish,id);

                                editor.apply();
                                editor.commit();

                                startActivity(gotologin);

                                hideDialog();
                                finish();



                            }
                            else
                            {
                                Toast.makeText(LoginResturantActivity.this, "لم يتم الدخول", Toast.LENGTH_SHORT).show();

                            }

                            hideDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideDialog();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap();
                map.put("country_id",country_id+"");
                map.put("mobile",mobile);
                map.put("password",password);
                map.put("remember_me",remember_me+"");
                map.put("fcm_token",fcm_token);





                return map;

            }
        }
                ;

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    public void saveObjectToPreferences(String key, Object value) {
        final SharedPreferences prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String jsonValue = new Gson().toJson(value);
        editor.putString(key, jsonValue);
        editor.apply();
    }

    public Object getObjectFromPreferences(String key, Object defaultObj) {
        final SharedPreferences prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        String value = prefs.getString(key, "");
        Object object = new Gson().fromJson(value, defaultObj.getClass());
        return object;
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(LoginResturantActivity.this);
        progressDialog.setMessage(getString(R.string.load_login));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
