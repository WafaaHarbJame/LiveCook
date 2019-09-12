package com.livecook.livecookapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.util.Log;
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
import com.livecook.livecookapp.Fragment.CookPageFragment;
import com.livecook.livecookapp.Fragment.RegisterFragment;
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

public class LoginActivity extends AppCompatActivity {

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
    SharedPreferences.Editor editor;
    private Boolean saveLogin;
    public String access_token;
    SharedPreferences prefs;
    ImageView back;
    String country_idd;
    ArrayList<Datum>countrycodelist = new ArrayList<Datum>();

    DatumecountryAdapter countrycodeadapter;

    ProgressDialog progressDialog;
    String mobile_number;
    String passward;
    int remember_me;

    int countrycode_reg;

    String country_id;
    String mobile, passwordintent;
    private static final String TAG = "tokenreg";
    Spinner countrycode;
    String country_mobile_codee;
    ArrayList<Datum> data = new ArrayList<>();
    TextView changepassward;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        countrycode = findViewById(R.id.countrycode);
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        changepassward = findViewById(R.id.changepassward);
        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        mToolbar = findViewById(R.id.toolbar);
        mCreateacoount = findViewById(R.id.createacoount);
        mTvmobile = findViewById(R.id.tvmobile);
        mEdmobileNumber = findViewById(R.id.edmobile_number);
        mTxpassward = findViewById(R.id.txpassward);
        mEdpassward = findViewById(R.id.edpassward);
        mCreatenewaccount = findViewById(R.id.createnewaccount);
        login = findViewById(R.id.login);
        back=findViewById(R.id.bacck);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  FragmentManager fragmentManager=getSupportFragmentManager();
                RegisterFragment registerFragment=new RegisterFragment();
                fragmentManager.beginTransaction().replace(android.R.id.content,registerFragment).commit();*/
            //  getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new RegisterFragment()).commit();
                Intent intent = new Intent(LoginActivity.this, LoginPageActivity.class);
                startActivity(intent);
                finish();


            }
        });



        changepassward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologin = new Intent(LoginActivity.this, ChangePasswardActivity.class);
                startActivity(gotologin);
                finish();

            }
        });

        mCreatenewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        countrycode = findViewById(R.id.countrycode);
        getCountrycode();

        countrycode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_mobile_codee = countrycode.getSelectedItem().toString();
                countrycode_reg = data.get(position).getId();
                /*Toast.makeText(LoginActivity.this, "selected"+countrycode.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "code"+data.get(position).getCode(), Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "id"+data.get(position).getId(), Toast.LENGTH_SHORT).show();*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                countrycode_reg=data.get(0).getId();

            }
        });




        getCountrycode();
        progressDialog = new ProgressDialog(LoginActivity.this);


        access_token = prefs.getString(Constants.access_token1, access_token);
        // Toast.makeText(this, "from shared"+mobile, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "frm shared"+access_token, Toast.LENGTH_SHORT).show();

        saveLoginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    remember_me = 1;

                } else {

                    remember_me = 0;
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (mEdmobileNumber.getText().toString().matches("")) {
                    Toast.makeText(LoginActivity.this, getString(R.string.enter_mobile), Toast.LENGTH_SHORT).show();
                }
                else if (mEdmobileNumber.getText().toString().length()<9) {
                    Toast.makeText(LoginActivity.this, getString(R.string.enter_mobile_length), Toast.LENGTH_SHORT).show();
                }


                else if (mEdpassward.getText().toString().matches("")) {
                    Toast.makeText(LoginActivity.this, getString(R.string.enter_passward), Toast.LENGTH_SHORT).show();
                }

                else {


                    if (saveLoginCheckBox.isChecked()) {
                        loginuser(countrycode_reg, mEdmobileNumber.getText().toString(), mEdpassward.getText().toString(), remember_me, "");


                    } else {
                        loginuser(countrycode_reg, mEdmobileNumber.getText().toString(), mEdpassward.getText().toString(), remember_me, "");


                    }

                }


                // Toast.makeText(LoginActivity.this, ""+country_id, Toast.LENGTH_SHORT).show();


            }
        });




      /*  Intent intent=getIntent();
        if(intent!=null){
            mobile=intent.getStringExtra(Constants.mobile);
            passwordintent=intent.getStringExtra(Constants.passwordintent);
            mEdmobileNumber.setText(mobile);
            mEdpassward.setText(passward);


        }*/


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

                        Datum datum = new Datum();
                        datum.setId(id);
                        datum.setName(name);
                        datum.setCode(code);
                        datum.setSortName(sort_name);
                        datum.setNationality(nationality);
                        datum.setFlag(flag);
                        data.add(datum);
                        countrycodelist.add(datum);


                    }
                    countrycodeadapter = new DatumecountryAdapter(LoginActivity.this, countrycodelist);
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


    public void loginuser(final int country_id, final String mobile, final String password, final int remember_me, final String fcm_token) {
        showDialog();
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
                        Intent gotologin = new Intent(LoginActivity.this, MainActivity.class);
                        editor = prefs.edit();
                        editor = prefs.edit();
                        editor.putString(Constants.access_token1, access_token);
                        editor.putString(Constants.TYPE, type);
                        editor.putInt(Constants.user_id, user_id);
                        editor.putString(Constants.mobile, mEdmobileNumber.getText().toString());
                        editor.putString(Constants.password, mEdpassward.getText().toString());
                        gotologin.putExtra(Constants.access_token1, access_token);
                        gotologin.putExtra(Constants.TYPE, type);
                        editor.putInt(Constants.user_id, user_id);
                        editor.putString(Constants.access_token1, access_token);
                        editor.putString(Constants.TYPE, type);
                        editor.putBoolean(Constants.ISLOGIN, true);
                        editor.putInt(Constants.user_id, user_id);
                        editor.putString(Constants.mobile, mEdmobileNumber.getText().toString());
                        editor.putString(Constants.password, mEdpassward.getText().toString());
                        editor.apply();
                        editor.commit();
                        startActivity(gotologin);
                        finish();


                    } else {
                        hideDialog();
                        login.setEnabled(false);
                        Toast.makeText(LoginActivity.this, "رقم الجوال او كلمة المرور غير صحيحة ", Toast.LENGTH_SHORT).show();
                        login.setEnabled(true);

                    }



                    hideDialog();


                } catch (JSONException e) {
                    e.printStackTrace();
                    hideDialog();
                    login.setEnabled(false);
                    Toast.makeText(LoginActivity.this, "رقم الجوال او كلمة المرور غير صحيحة ", Toast.LENGTH_SHORT).show();
                    login.setEnabled(true);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                hideDialog();
                login.setEnabled(false);
                Toast.makeText(LoginActivity.this, "رقم الجوال او كلمة المرور غير صحيحة ", Toast.LENGTH_SHORT).show();
                login.setEnabled(true);


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

    public void showDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this);
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
