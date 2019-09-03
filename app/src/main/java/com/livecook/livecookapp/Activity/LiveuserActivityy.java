package com.livecook.livecookapp.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Adapter.ResturantmenuAdapterView;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.MenuResturantModel;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveuserActivityy extends AppCompatActivity {
    private Toolbar toolbar;
    ImageView back;
    String watch_url;
    String RecordPath;
    String name;
    String title;
    ImageView reportlive;
    SharedPreferences prefs;
    String tokenfromlogin;
    String typnumer;
    String Steam_path;
    String report_message;
    int user_id;
     Dialog dialog;
    private Boolean saveLogin;
    String firebase_type;
    CircleImageView cookimage;
int cooker_resturant_firebaseid;
    int cooker_resturant_type;
    TextView cook_name;
    boolean status;
    TextView cook_live;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveuser_activityy);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        back = findViewById(R.id.bacck);
        Intent intent = getIntent();
        reportlive = findViewById(R.id.reportlive);
        cookimage=findViewById(R.id.cookimage);
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");

        typnumer = prefs.getString(Constants.TYPE, "user");

        user_id = prefs.getInt(Constants.user_id, -1);
        saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);
        cook_name=findViewById(R.id.cook_name);
        cook_live=findViewById(R.id.cook_live);
        if (intent != null) {

            watch_url = intent.getStringExtra(Constants.WatchPath);
            name = intent.getStringExtra(Constants.firebase_name);
            title = intent.getStringExtra(Constants.firebase_title);
            Steam_path = intent.getStringExtra(Constants.Steam_path);
            firebase_type=intent.getStringExtra(Constants.firebase_type);
             cooker_resturant_firebaseid = intent.getIntExtra(Constants.cooker_resturant_firebaseid, -1);
            cooker_resturant_type = intent.getIntExtra(Constants.cooker_resturant_type, -1);
            RecordPath = intent.getStringExtra(Constants.RecordPath);
            status=intent.getBooleanExtra(Constants.status,false);




        }



        if(cooker_resturant_type==6||cooker_resturant_type==7){

            getCookerprofile();

        }


        else {
            getResturantprofile();


        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        if (prefs != null) {
            typnumer = prefs.getString(Constants.TYPE, "user");


            if (typnumer.matches("cooker")) {
                reportlive.setVisibility(View.GONE);


            } else if (typnumer.matches("restaurant")) {
                reportlive.setVisibility(View.GONE);


            } else if (typnumer.matches("user")) {
                reportlive.setVisibility(View.VISIBLE);


            }

        }


        reportlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (saveLogin) {

                    dialog = new Dialog(LiveuserActivityy.this);
                    dialog.setContentView(R.layout.custome_report_dialog);

                    Button yes = dialog.findViewById(R.id.yes);
                    Button no = dialog.findViewById(R.id.no);
                    EditText livename = dialog.findViewById(R.id.livename);
                    ImageView close = dialog.findViewById(R.id.imageexitgame);

                    dialog.setCancelable(true);

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (livename.getText().toString().isEmpty()) {
                                Toast.makeText(LiveuserActivityy.this, "ادخل  النص ", Toast.LENGTH_SHORT).show();

                            } else {

                                report_message = livename.getText().toString();
                                reportlive(tokenfromlogin,user_id,firebase_type,"https://livecook-b8bbc.firebaseio.com/Live"+"/"+Steam_path,Steam_path,report_message);
                            }


                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });


                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });

                    dialog.show();

                }


                else
                {

                    Intent intent=new Intent(LiveuserActivityy.this, LoginActivity.class);
                    startActivity(intent);
                }



            }
        });


        VideoView videoView = findViewById(R.id.VideoView);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//Uri.fromFile(new File("http://167.86.71.40:89/livecook/tayeh-10.m3u8));
        if(!status){
            cook_live.setVisibility(View.GONE);
            Uri uri = Uri.parse(RecordPath);
            videoView.setVideoURI(uri);

            videoView.start();

        }

        else {
            Uri uri = Uri.parse(watch_url);
            videoView.setVideoURI(uri);

            videoView.start();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(LiveuserActivityy.this, MainActivity.class);
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

    public void reportlive(final String access_token,final int  owner_id,final  String owner_type ,
                           final String firebase_path,final String livestream_path,
                           final String text) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.report_uers_live,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(LiveuserActivityy.this, "respond"+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject register_response=new JSONObject(response);
                            boolean status=register_response.getBoolean("status");


                            if(status){
                                Toast.makeText(LiveuserActivityy.this, "تم الابلاغ عن هذا البث ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            }
                            else
                            {
                                Toast.makeText(LiveuserActivityy.this, "لم يتم الابلاغ عن هذا البث ", Toast.LENGTH_SHORT).show();

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap();
                map.put("owner_id", 32+"");
                map.put("owner_type",owner_type);
                map.put("firebase_path",firebase_path+"");
                map.put("livestream_path",livestream_path);
                map.put("text", text);
                map.put("Authorization", "Bearer" + "  " + access_token);




                return map;

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap();
                map.put("owner_id", String.valueOf(owner_id));
                map.put("owner_type",owner_type);
                map.put("firebase_path",firebase_path+"");
                map.put("livestream_path",livestream_path);
                map.put("Authorization", "Bearer" + "  " + access_token);
                map.put("text", text);





                return map;

            }
        }
                ;

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    public void getResturantprofile() {

        //showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/restaurant/" + cooker_resturant_firebaseid + "/profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HZM", response);

                try {
                    JSONObject task_respnse = new JSONObject(response);
                    JSONObject taskarray = task_respnse.getJSONObject("data");
                    String name = taskarray.getString("name");
                    String avatarURL = taskarray.getString("avatar_url");
                    cook_name.setText(name);

                    if (avatarURL.matches("") || !avatarURL.startsWith("http")) {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                        Picasso.with(LiveuserActivityy.this).load(avatarURL).error(R.drawable.ellipse)
                                // .resize(100,100)
                                .into(cookimage);
                    } else {
                        Picasso.with(LiveuserActivityy.this).load(avatarURL)
                                // .resize(100,100)
                                .error(R.drawable.ellipse)

                                .into(cookimage);


                    }





                } catch (JSONException e1) {
                    e1.printStackTrace();

                }


                //  hideDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }
    public void getCookerprofile() {

        //showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/cooker/" + cooker_resturant_firebaseid + "/profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HZM", response);

                try {
                    JSONObject task_respnse = new JSONObject(response);
                    JSONObject taskarray = task_respnse.getJSONObject("data");
                     name = taskarray.getString("name");
                    String avatarURL = taskarray.getString("avatar_url");
                    cook_name.setText(name);


                    if (avatarURL.matches("") || !avatarURL.startsWith("http")) {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                        Picasso.with(LiveuserActivityy.this).load(avatarURL).error(R.drawable.ellipse)
                                // .resize(100,100)
                                .into(cookimage);
                    } else {
                        Picasso.with(LiveuserActivityy.this).load(avatarURL)
                                // .resize(100,100)
                                .error(R.drawable.ellipse)

                                .into(cookimage);


                    }





                } catch (JSONException e1) {
                    e1.printStackTrace();

                }


                //  hideDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }



}
