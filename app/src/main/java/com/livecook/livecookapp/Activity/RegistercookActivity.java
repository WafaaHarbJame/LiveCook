package com.livecook.livecookapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.livecook.livecookapp.Adapter.DatumecountryAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.AppHelper;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.Datum;
import com.livecook.livecookapp.Model.SessionManager;
import com.livecook.livecookapp.Model.VolleyMultipartRequest;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class RegistercookActivity extends AppCompatActivity implements IPickResult {

    private Toolbar toolbar;

    private EditText edname;
    private EditText edmobileNumber;
    private EditText edpassward;
    private EditText edrepatepassward;
    private ImageView insertliscense,showimage;
    SharedPreferences.Editor editor;
    private Button createaccount;
    ArrayList<Datum> data = new ArrayList<>();
    ArrayList<Datum> city = new ArrayList<>();
    String image_cook_path;
    String imageName;
    ProgressDialog progressDialog;

    List<String> countrylist = new ArrayList<String>();
    List<String> citylist = new ArrayList<String>();
    ArrayList<Datum> countrycodelist = new ArrayList<Datum>();

    int country_id;
    int  country_codee;
    String country_mobile_codee;
    public String access_token;
    SharedPreferences prefs;




    Spinner spin;
    Spinner cityname;
    Spinner countrycode;

    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> cityadapter;
    DatumecountryAdapter countrycodeadapter;

    File f;
    private SessionManager mSessionManager;
    private LinearLayout mContainer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);


        setContentView(R.layout.activity_registercook);
        initViews();

        setSupportActionBar(toolbar);
        String token = FirebaseInstanceId.getInstance().getToken();
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(RegistercookActivity.this);


        mContainer = findViewById(R.id.container);


        //        ActionBar actionBar = ((AppCompatActivity)getApplicationContext()).getSupportActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");

        spin = findViewById(R.id.countryname);
        cityname = findViewById(R.id.cityname);
        countrycode = findViewById(R.id.countrycode);
        insertliscense=findViewById(R.id.camera_action);
        showimage=findViewById(R.id.showimage);

        insertliscense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()
                .setTitle("اختار صورة الرخصة ")
                        .setTitleColor(R.color.green)



                ).show(RegistercookActivity.this);

            }
        });

        countrycode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_mobile_codee=countrycode.getSelectedItem().toString();
                //   Toast.makeText(ClientRegisterActivity.this, ""+countrycode.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getCountries();
        getCountrycode();

        showSoftKeyboard(edname);

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edname.getText().toString().matches("")) {
                    Toast.makeText(RegistercookActivity.this, getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                }
                else if (edmobileNumber.getText().toString().matches("")) {
                    Toast.makeText(RegistercookActivity.this, getString(R.string.enter_mobile), Toast.LENGTH_SHORT).show();
                }
                else if (edpassward.getText().toString().matches("")) {
                    Toast.makeText(RegistercookActivity.this, getString(R.string.enter_passward), Toast.LENGTH_SHORT).show();
                }
                else if (edrepatepassward.getText().toString().matches("")) {
                    Toast.makeText(RegistercookActivity.this, getString(R.string.repeatpasswward)
                            , Toast.LENGTH_SHORT).show();
                }
                else  if ((edpassward.getText().toString()).matches(edrepatepassward.getText().toString()) ) {
             registeruser(edname.getText().toString(),
                         edmobileNumber.getText().toString(),
                         country_id,edpassward.getText().toString(),edrepatepassward.getText().toString());

                    /*edmobileNumber.setText("");
                    edpassward.setText("");
                    edrepatepassward.setText("");*/

                }
                else{
                    Toast.makeText(RegistercookActivity.this, getString(R.string.not_match_passward), Toast.LENGTH_SHORT).show();

                }

                // registeruser("wafaa","+9720598271258",5,"1234567","1234567");
            }
        });

        //  getCities(country_id);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_id = data.get(position).getId();
                //country_codee=data.get(position).getCode();

                getCities(country_id);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getContext());
        //SpinnermobileAdapter customAdapter=new SpinnermobileAdapter(getActivity(),flags,countryNames);
        // spin.setAdapter(customAdapter);
      /*  new VolleyRequests().setiReceiveData(new VolleyRequests.IReceiveData() {
            @Override
            public void onDataReceived(Object posts) {
                //tvJSON.setText(((List<Post>)posts).get(0).getBody());
                Toast.makeText(ClientRegisterActivity.this, ""+((Countries)posts).getData().get(0).getName(), Toast.LENGTH_SHORT).show();


        }


        }).getContacts();*/


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
                    countrycodeadapter = new DatumecountryAdapter(RegistercookActivity.this, countrycodelist);
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

    public void getCountries(){
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
                        Datum datum=new Datum();
                        datum.setId(id);
                        datum.setName(name);
                        datum.setCode(code);
                        datum.setSortName(sort_name);
                        datum.setNationality(nationality);
                        data.add(datum);
                        countrylist.add(name);


                    }
                    arrayAdapter = new ArrayAdapter<String>(RegistercookActivity.this, android.R.layout.simple_list_item_1, countrylist);
                    spin.setAdapter(arrayAdapter);

                    arrayAdapter.notifyDataSetChanged();

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

    public void getCities(final int  country_id){
        citylist.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.cities_url+country_id, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        Datum cities=new Datum();
                        cities.setId(id);
                        cities.setName(name);
                        city.add(cities);
                        citylist.add(name);



                    }
                    cityadapter = new ArrayAdapter<String>(RegistercookActivity.this, android.R.layout.simple_list_item_1, citylist);

                    cityname.setAdapter(cityadapter);
                    cityadapter.notifyDataSetChanged();


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
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        edmobileNumber = findViewById(R.id.edmobile_number);
        edname=findViewById(R.id.edname);
        edpassward = findViewById(R.id.edpassward);
        createaccount = findViewById(R.id.createaccount);
        edrepatepassward= findViewById(R.id.edrepatepassward);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(RegistercookActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();            return true;

        }
        if (item.getItemId() == R.id.nav_home) {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);

    }

    public void uploadimage(final String  file) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.upload_image,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject register_response=new JSONObject(response);
                            boolean status=register_response.getBoolean("status");
                            String message=register_response.getString("message");
                            String token=register_response.getString("accessToken");

                           // Toast.makeText(RegistercookActivity.this, ""+status, Toast.LENGTH_SHORT).show();

                            if(status){
                                Toast.makeText(RegistercookActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(RegistercookActivity.this, "لم يتم التسجيل"+message, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap();
                map.put("file",file);





                return map;

            }
        }
                ;

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }
    public void registeruser(final String  name,final  String mobile ,final int country_id,final String password,
                             final String password_confirmation) {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.cooker_register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject register_response=new JSONObject(response);
                            boolean status=register_response.getBoolean("status");
                            String message=register_response.getString("message");
                            access_token=register_response.getString("accessToken");
                            JSONObject auth=register_response.getJSONObject("auth");
                            int id=auth.getInt("id");



                            if(status){
                                Toast.makeText(RegistercookActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();


                                editor = prefs.edit();
                                editor.putString(Constants.access_token1, access_token);
                                editor.putString(Constants.TYPE,"cooker");
                                editor.putInt(Constants.cook_id_profile_publish,id);
                                editor.putString(Constants.access_token1, access_token);
                                editor.putString(Constants.TYPE,"cooker");
                                editor.putBoolean(Constants.ISLOGIN,true);
                                editor.putInt(Constants.cook_id_profile_publish,id);
                                editor.putString(Constants.TYPE, "cooker");
                                editor.apply();
                                editor.commit();
                                loginuser(country_id,mobile,edpassward.getText().toString(),0,access_token);

                            }
                            else
                            {
                                Toast.makeText(RegistercookActivity.this, "قيمة الجوال مستتخدمة من قبل ", Toast.LENGTH_SHORT).show();

                            }

                            hideDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideDialog();
                            Toast.makeText(RegistercookActivity.this, "قيمة الجوال مستتخدمة من قبل ", Toast.LENGTH_SHORT).show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(RegistercookActivity.this, "قيمة الجوال مستتخدمة من قبل ", Toast.LENGTH_SHORT).show();


            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap();
                map.put("name",name);
                map.put("mobile",mobile);
                map.put("country_id",country_id+"");
                map.put("password",password);
                map.put("password_confirmation",password_confirmation);




                return map;

            }
        }
                ;

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }


    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            showimage.setImageBitmap(r.getBitmap());
            File file = null;
            try {
                file = new Compressor(this).compressToFile(new File(r.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Picasso.with(RegistercookActivity.this)
                    .load(file)
                    .error(R.drawable.ellipse)
                    // .resize(100,100)
                    .into((showimage));

            uploadAvatar();

            //Image path
          r.getPath();
             f = new File(r.getPath());
             imageName = f.getName();
            Toast.makeText(this, "the imageName"+imageName, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "the file"+f, Toast.LENGTH_LONG).show();

            Toast.makeText(this, "the r"+r.getUri(), Toast.LENGTH_LONG).show();

            Toast.makeText(this, ""+r.getPath()+""+r.getBitmap()+r.getUri(), Toast.LENGTH_LONG).show();

            image_cook_path=r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
           // getBase64FromFile(image_cook_path);
        }
    }

    public  String getBase64FromFile(String path)
    {
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        byte[] baat = null;
        String encodeString = null;
        try
        {
            bmp = BitmapFactory.decodeFile(path);
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            baat = baos.toByteArray();
            encodeString = Base64.encodeToString(baat, Base64.DEFAULT);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return encodeString;
    }


    private void uploadAvatar() {
        final ProgressDialog progressDialog = new ProgressDialog(RegistercookActivity.this);
        progressDialog.setMessage("Uploading Avatar...");
        progressDialog.show();

        final String id = "1";
        String url = "https://livecook.co/api/v1/upload/image";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
               // Toast.makeText(RegistercookActivity.this, ""+resultResponse, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(resultResponse);

                  //  Toast.makeText(RegistercookActivity.this, ""+obj.getString("file_name"), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(RegistercookActivity.this, ""+obj.getString("status"), Toast.LENGTH_SHORT).show();
                    if (!obj.getBoolean("status")) {
                        String file_name = obj.getString("file_name");
                        mSessionManager.setUrl(file_name);

                        Picasso.with(RegistercookActivity.this)
                                .load(file_name)
                                .error(R.drawable.ellipse)
                                // .resize(100,100)
                                .into((showimage));

                        // Picasso.get().load(avatar).placeholder(R.drawable.lissa).into(mAvatar);
                        //Toast.makeText(RegistercookActivity.this, "Avatar Changed", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("tagg", "Response: " + resultResponse);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("tag", "JSON Error: " + e);
                    showUploadSnackBar();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("tag", "Volley Error: " + error);
                showUploadSnackBar();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (showimage == null) {
                    Log.i("tag", "avatar null");
                }
                params.put("image", new DataPart("img_" + id + ".jpg", AppHelper.getFileDataFromDrawable(getApplicationContext(), showimage.getDrawable()), "image/jpg"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(multipartRequest);
    }

    private void showUploadSnackBar() {
        Snackbar.make(mContainer, "Network Error. Failed to upload avatar", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadAvatar();
                    }
                }).show();
    }




    public void loginuser(final int  country_id,final  String mobile ,final String password,final int  remember_me,
                          final String fcm_token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.cooker_login_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject register_response=new JSONObject(response);
                            JSONObject auth=register_response.getJSONObject("auth");
                            boolean status=register_response.getBoolean("status");
                            int id=auth.getInt("id");
                            access_token=register_response.getString("access_token");
                            String type=register_response.getString("type");

                            if(status){
                                editor = prefs.edit();
                                editor.putString(Constants.access_token1, access_token);
                                editor.putString(Constants.TYPE,type);
                                editor.putInt(Constants.cook_id_profile_publish,id);
                                editor.putString(Constants.access_token1, access_token);
                                editor.putString(Constants.TYPE,type);
                                editor.putBoolean(Constants.ISLOGIN,true);
                                editor.putInt(Constants.cook_id_profile_publish,id);
                                editor.putString(Constants.access_token1, access_token);
                                editor.putString(Constants.TYPE,type);
                                editor.putBoolean(Constants.ISLOGIN,true);
                                editor.putInt(Constants.user_id,id);
                                editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                                editor.putString(Constants.password,edpassward.getText().toString());
                                editor.apply();
                                editor.commit();
                                Intent gotologin=new Intent(RegistercookActivity.this, MainActivity.class);
                                gotologin.putExtra(Constants.access_token1,access_token);
                                gotologin.putExtra(Constants.cook_id_profile_publish,id);
                                startActivity(gotologin);
                                finish();




                            }
                            else
                            {
                                Toast.makeText(RegistercookActivity.this, "لم يتم الدخول", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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


    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(RegistercookActivity.this);
        progressDialog.setMessage(getString(R.string.load_login));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {

        if (progressDialog.isShowing()) progressDialog.dismiss();
    }






}