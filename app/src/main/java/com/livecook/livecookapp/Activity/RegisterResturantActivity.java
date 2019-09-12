package com.livecook.livecookapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Base64;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class RegisterResturantActivity extends AppCompatActivity {
    private static final String TAG = "";
    private ImageView mAvatar;
    private Uri mCropImageUri;
    private LinearLayout mContainer;
    ImageView cameraAction;
    String file_name;

    private SessionManager mSessionManager;
    ProgressDialog progressDialog;
    String access_token;


    private Toolbar toolbar;

    private EditText edname;
    private EditText edmobileNumber;
    private EditText edpassward;
    private EditText edrepatepassward;
    private ImageView insertliscense,showimage;
    private Button createaccount;
    ArrayList<Datum> data = new ArrayList<>();
    ArrayList<Datum> city = new ArrayList<>();
    String image_cook_path;
    String imageName;
    SharedPreferences.Editor editor;
    int city_id;

    List<String> countrylist = new ArrayList<String>();
    List<String> citylist = new ArrayList<String>();
    ArrayList<Datum> countrycodelist = new ArrayList<Datum>();

    int country_id;
    int  country_codee;
    String country_mobile_codee;


    Spinner spin;
    Spinner cityname;
    Spinner countrycode;
    String fcm_token;

    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> cityadapter;
    DatumecountryAdapter countrycodeadapter;

    SharedPreferences prefs;
    File f;
    boolean country_spinner_click=false;
    boolean city_spinner_click=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);


        setContentView(R.layout.activity_register_resturant);
        initViews();

        setSupportActionBar(toolbar);
        String token = FirebaseInstanceId.getInstance().getToken();
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

        mContainer = findViewById(R.id.container);
        progressDialog = new ProgressDialog(RegisterResturantActivity.this);



        //        ActionBar actionBar = ((AppCompatActivity)getApplicationContext()).getSupportActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");

        spin = findViewById(R.id.countryname);
        cityname = findViewById(R.id.cityname);
        countrycode = findViewById(R.id.countrycode);
        insertliscense=findViewById(R.id.camera_action);
        showimage=findViewById(R.id.showimage);
        showSoftKeyboard(edname);
        fcm_token = FirebaseInstanceId.getInstance().getToken();
        if(fcm_token!=null) {
            Log.d("khtwotoken", fcm_token);
        }
        insertliscense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()
                        .setTitle("اختار صورة الرخصة ")
                        .setTitleColor(R.color.green)



                ).show(RegisterResturantActivity.this);

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

      //  getCountries();
        getCountrycode();
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edname.getText().toString().matches("")) {
                    Toast.makeText(RegisterResturantActivity.this, getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                }


                else if (edname.getText().toString().length()<3) {
                    Toast.makeText(RegisterResturantActivity.this, getString(R.string.enter_name_lengh), Toast.LENGTH_SHORT).show();
                }


                else if (edmobileNumber.getText().toString().matches("")) {
                    Toast.makeText(RegisterResturantActivity.this, getString(R.string.enter_mobile), Toast.LENGTH_SHORT).show();
                }

                else if (edmobileNumber.getText().toString().length()<9) {
                    Toast.makeText(RegisterResturantActivity.this, getString(R.string.enter_mobile_length), Toast.LENGTH_SHORT).show();
                }
                else if (edpassward.getText().toString().matches("")) {
                    Toast.makeText(RegisterResturantActivity.this, getString(R.string.enter_passward), Toast.LENGTH_SHORT).show();
                }
                else if (edrepatepassward.getText().toString().matches("")) {
                    Toast.makeText(RegisterResturantActivity.this, getString(R.string.repeatpasswward)
                            , Toast.LENGTH_SHORT).show();
                }
                else if (!country_spinner_click) {
                    Toast.makeText(RegisterResturantActivity.this, getString(R.string.enter_country), Toast.LENGTH_SHORT).show();
                }
                else if (!city_spinner_click) {
                    Toast.makeText(RegisterResturantActivity.this, getString(R.string.entercity), Toast.LENGTH_SHORT).show();
                }

                else  if ((edpassward.getText().toString()).matches(edrepatepassward.getText().toString()) ) {
                    //Toast.makeText(RegisterResturantActivity.this, ""+file_name, Toast.LENGTH_SHORT).show();
                    registeruser(edname.getText().toString(), edmobileNumber.getText().toString(), country_id,
                            edpassward.getText().toString(), edrepatepassward.getText().toString(), file_name,city_id,fcm_token);

                    /*edmobileNumber.setText("");
                    edpassward.setText("");
                    edrepatepassward.setText("");*/

                }
                else{
                    Toast.makeText(RegisterResturantActivity.this, getString(R.string.not_match_passward), Toast.LENGTH_SHORT).show();

                }

                // registeruser("wafaa","+9720598271258",5,"1234567","1234567");
            }
        });

        //  getCities(country_id);

        spin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 country_spinner_click=true;
                getCountries();
                return false;
            }
        });







        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_id = data.get(position).getId();
                //country_codee=data.get(position).getCode();
                //  getCities(country_id);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                country_id= Integer.parseInt("");


            }
        });


        cityname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                city_spinner_click=true;
                getCities(country_id);
                return false;
            }
        });
        cityname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city_id = city.get(position).getId();
                // Toast.makeText(getActivity(), ""+data.get(position).getId(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city_id = city.get(0).getId();



            }
        });

        //initiate shared preferences handler class
        mSessionManager = new SessionManager(this);

        mContainer = findViewById(R.id.container);
        mAvatar = findViewById(R.id.profile_avatar);
        cameraAction = findViewById(R.id.camera_action);

        //Check if avatar previously uploaded in preferences and load url
        if (!"hello".equalsIgnoreCase(mSessionManager.getUrl())) {

            Picasso.with(RegisterResturantActivity.this)
                    .load(mSessionManager.getUrl())
                    .error(R.drawable.ellipse)
                    // .resize(100,100)
                    .into(mAvatar);

            //Picasso library to display images
            // Picasso.get().load(mSessionManager.getUrl()).placeholder(R.drawable.lissa).into(mAvatar);
        }

        cameraAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(RegisterResturantActivity.this, RegisterActivity.class);
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


    public void registeruser(final String name, final String mobile, final int country_id, final String password, final String password_confirmation, final String municipal_license,final  int city_id,final String fcm_token) {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.restaurant_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    JSONObject auth=register_response.getJSONObject("auth");
                    int id =auth.getInt("id");
                    access_token=register_response.getString("accessToken");

                    if (status) {

                        editor = prefs.edit();
                        editor = prefs.edit();
                        editor.putString(Constants.access_token1, access_token);
                        editor.putString(Constants.TYPE,"");
                        editor.putInt(Constants.ressturant_id_profile_publish,id);
                        editor.putString(Constants.access_token1, access_token);
                        editor.putString(Constants.TYPE,"restaurant");
                        editor.putInt(Constants.ressturant_id_profile_publish,id);
                        editor.putInt(Constants.ressturant_id_profile_publish,id);
                        editor.putString(Constants.access_token1, access_token);
                        editor.putBoolean(Constants.ISLOGIN,true);
                        editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                        editor.putString(Constants.password,edpassward.getText().toString());
                        editor.putInt(Constants.user_id,id);
                        editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                        editor.putString(Constants.password,edpassward.getText().toString());
                        Intent gotologin=new Intent(RegisterResturantActivity.this, MainActivity.class);
                        gotologin.putExtra(Constants.access_token1,access_token);
                        gotologin.putExtra(Constants.ressturant_id_profile_publish,id);
                        editor.apply();
                        editor.commit();


                        loginuser(country_id,edmobileNumber.getText().toString(),edpassward.getText().toString(),0,fcm_token);

                          editor= prefs.edit();
                        editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                        editor.putString(Constants.password,edpassward.getText().toString());
                        editor.apply();





                        Toast.makeText(RegisterResturantActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterResturantActivity.this, "لم يتم التسجيل" + message, Toast.LENGTH_SHORT).show();

                    }

                    hideDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterResturantActivity.this, "هذا الجوال مستخدم مسبقا", Toast.LENGTH_SHORT).show();
hideDialog();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterResturantActivity.this, "هذا الجوال مستخدم مسبقا", Toast.LENGTH_SHORT).show();
hideDialog();

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
                map.put("municipal_license", municipal_license+"");
                map.put("city_id", city_id+"");
                map.put("fcm_token", fcm_token);




                return map;

            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


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
                        datum.setFlag(flag);

                        datum.setSortName(sort_name);
                        datum.setNationality(nationality);
                        data.add(datum);
                        countrycodelist.add(datum);


                    }
                    countrycodeadapter = new DatumecountryAdapter(RegisterResturantActivity.this, countrycodelist);
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
                    arrayAdapter = new ArrayAdapter<String>(RegisterResturantActivity.this, android.R.layout.simple_list_item_1, countrylist);
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
                    cityadapter = new ArrayAdapter<String>(RegisterResturantActivity.this, android.R.layout.simple_list_item_1, citylist);

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

    /**
     * Show image chooser options
     * Uses https://github.com/ArthurHub/Android-Image-Cropper library
     * to generate square images.
     * Replace with your own if you don't need the image cropper library
     */
    private void selectImage() {
        CropImage.startPickImageActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();
                try {
                    //Uses https://github.com/zetbaitsu/Compressor library to compress selected image
                    File file = new Compressor(this).compressToFile(new File(uri.getPath()));

                    Picasso.with(RegisterResturantActivity.this)
                            .load(file)
                            .error(R.drawable.ellipse)
                            // .resize(100,100)
                            .into(mAvatar);

                    // Picasso.get().load(file).into(mAvatar);
                    //Picasso
                   // Toast.makeText(this, "Compressed", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(this, "Failed Compress", Toast.LENGTH_SHORT).show();
                    //Picasso.get().load(uri).into(mAvatar);
                    Picasso.with(RegisterResturantActivity.this)
                            .load(uri)
                            .error(R.drawable.ellipse)
                            // .resize(100,100)
                            .into(mAvatar);

                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uploadAvatar();
                    }
                }, 1000);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //TODO handle cropping error
                //Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAllowFlipping(false)
                .setActivityTitle("Crop Image")
                .setCropMenuCropButtonIcon(R.drawable.ic_check)
                .setAllowRotation(true)
                .setInitialCropWindowPaddingRatio(0)
                .setFixAspectRatio(true)
                .setAspectRatio(1, 1)
                .setOutputCompressQuality(80)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    /**
     * Upload image selected using volley
     */
    private void uploadAvatar() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("يتم  الان تحميل الصورة الرجاء الانتظار ...");
        progressDialog.show();

        final String id = "1";
        String url = "https://livecook.co/api/v1/upload/image";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
                //Toast.makeText(RegisterResturantActivity.this, ""+resultResponse, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(resultResponse);
                    file_name=obj.getString("file_name");

                   // Toast.makeText(RegisterResturantActivity.this, ""+obj.getString("file_name"), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(RegisterResturantActivity.this, ""+obj.getString("status"), Toast.LENGTH_SHORT).show();
                    if (!obj.getBoolean("status")) {
                        String file_name = obj.getString("file_name");
                        // mSessionManager.setUrl(file_name);
                        // Picasso.get().load(avatar).placeholder(R.drawable.lissa).into(mAvatar);
                        //Toast.makeText(RegisterResturantActivity.this, "Avatar Changed", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "Response: " + resultResponse);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON Error: " + e);
                    showUploadSnackBar();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, "Volley Error: " + error);
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
                if (mAvatar == null) {
                    Log.i(TAG, "avatar null");
                }
                params.put("image", new DataPart("img_" + id + ".jpg", AppHelper.getFileDataFromDrawable(getApplicationContext(), mAvatar.getDrawable()), "image/jpg"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(multipartRequest);
    }

    /**
     * SnackBar to retry in case of network issues
     */
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
                                editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                                editor.putString(Constants.password,edpassward.getText().toString());
                                editor.putInt(Constants.user_id,id);
                                editor.putString(Constants.mobile, edmobileNumber.getText().toString());
                                editor.putString(Constants.password,edpassward.getText().toString());
                                Intent gotologin=new Intent(RegisterResturantActivity.this, MainActivity.class);
                                gotologin.putExtra(Constants.access_token1,access_token);
                                gotologin.putExtra(Constants.ressturant_id_profile_publish,id);

                                editor.apply();
                                editor.commit();

                                startActivity(gotologin);
                                finish();



                            }
                            else
                            {
                                Toast.makeText(RegisterResturantActivity.this, "قيمة الجوال مستتخدمة من قبل ", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterResturantActivity.this, "قيمة الجوال مستتخدمة من قبل ", Toast.LENGTH_SHORT).show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterResturantActivity.this, "قيمة الجوال مستتخدمة من قبل ", Toast.LENGTH_SHORT).show();


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
        progressDialog = new ProgressDialog(RegisterResturantActivity.this);
        progressDialog.setMessage(getString(R.string.load_login));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {

        if (progressDialog.isShowing()) progressDialog.dismiss();
    }
}

