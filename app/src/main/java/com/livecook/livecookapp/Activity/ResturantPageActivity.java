package com.livecook.livecookapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livecook.livecookapp.Adapter.ResturantImagetopAdapter1;
import com.livecook.livecookapp.Adapter.ResturantmenuAdapterView;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.AllFirebaseModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.MenuResturantModel;
import com.livecook.livecookapp.Model.ResturantImage;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResturantPageActivity extends AppCompatActivity {
    MenuResturantModel menuResturantModel;

    private Boolean saveLogin;
    SharedPreferences prefs;
    String full_mobile;
    Toolbar toolbar;
    String tokenfromlogin;
    Boolean  isFollowed,isFavorite=false;
    boolean mFollowing=true;
    boolean mFavorite=true;
    ProgressDialog progressDialog;
    ScrollView scrollView;
    TextView menuword;


    private DatabaseReference mFirebaseDatabase,mFirebaseDatabaseread;
    private ConstraintLayout mConstraintLayout;
    private ImageView mCookstar;
    private CircleImageView mCookimage;
    private TextView mCookName;
    private TextView mCookDesc;
    private ImageView mFollower;
    private TextView mCountfollow;
    private ImageView mFollowingbut;
    private TextView mCountryCook;
    private TextView mCityCook;
    private TextView mCityState;
    private TextView mCookPhone;
    private ImageView mImageView2;
    private TextView mAblePhoneLogin;
    public CircleImageView cookimagecir;
    ImageView view_line;
    public Button contactwahts;
    ArrayList<MenuResturantModel> menuimage = new ArrayList<>();
    private Button mButton;
    public String cookimage = "cookimage";
    public String cook_name = "cook_name";
    public String cook_desc = "cook_desc";
    public String cook_address = "cook_address";
    public String countfollow = "countfollow";
    public String cook_country = "cook_country";
    public String cook_city = "cook_city";
    public String cook_state = "cook_state";
    public String cook_phone = "cook_phone";
    ArrayList<ResturantImage> data = new ArrayList<>();
    ArrayList<AllFirebaseModel> resturantImagestop = new ArrayList<>();
    ResturantImagetopAdapter1 resturantImagetopAdapter;
    RecyclerView resturenimagerecyclertop;
    public int ressturant_id, cook_type_id, cook_counuty_id;
    ResturantmenuAdapterView resturantmenuAdapter;
    RecyclerView menurecycler;
    String typnumer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_page);
        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
      toolbar.setTitle("صفحة المطعم");
      scrollView=findViewById(R.id.scrollView2);
     view_line=findViewById(R.id.view_line);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mCookstar = findViewById(R.id.cookstar);
        mCookimage = findViewById(R.id.cookimage);
        mCookName = findViewById(R.id.cook_name);
        mCookDesc = findViewById(R.id.cook_desc);
        mFollower = findViewById(R.id.follower);
        mCountfollow =findViewById(R.id.countfollow);
        mFollowingbut = findViewById(R.id.followingbut);
        view_line=findViewById(R.id.view_line);

        mCountryCook =findViewById(R.id.country_cook);
        mCityCook = findViewById(R.id.city_cook);
        mCityState = findViewById(R.id.city_state);
        mCookPhone = findViewById(R.id.cook_phone);
        cookimagecir = findViewById(R.id.cookimage);
        mImageView2 = findViewById(R.id.imageView2);
        mAblePhoneLogin = findViewById(R.id.able_phone_login);
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        menuword=findViewById(R.id.menuword);
        saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);

        typnumer = prefs.getString(Constants.TYPE, "user");

        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");

        Intent intent=getIntent();
        if(intent!=null) {
            //Bundle args = getArguments();
            cook_counuty_id = intent.getIntExtra(Constants.cook_counuty_id, -1);
            cook_type_id = intent.getIntExtra(Constants.cook_type_id, -1);
            ressturant_id = intent.getIntExtra(Constants.ressturant_id, -1);
        }



        if (typnumer.matches("cooker")||typnumer.matches("restaurant")) {


            getprofileForretsurantandcooker();
        }
        if (typnumer.matches("user")) {
            if (saveLogin) {
                getResturantprofileiflogin(tokenfromlogin);

            }
            else{

                getResturantprofile();

        }







        }


        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live").child(ressturant_id+"_"+0);
        mFirebaseDatabaseread = FirebaseDatabase.getInstance().getReference("Live").child(ressturant_id+"_"+0);


        ResturantPageActivity.this.setTitle(getString(R.string.resturantpage));
        contactwahts = findViewById(R.id.contactwahts);
        menurecycler = findViewById(R.id.menurecylcer);
        LinearLayoutManager manager = new LinearLayoutManager(ResturantPageActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menurecycler.setLayoutManager(manager);
        menurecycler.setHasFixedSize(true);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menurecycler.setLayoutManager(manager);
        contactwahts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveLogin) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.whatsapp");
                    intent.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", full_mobile)));
                    if (getPackageManager().resolveActivity(intent, 0) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(ResturantPageActivity.this, "Please install whatsApp", Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Intent intent = new Intent(ResturantPageActivity.this,LoginPageActivity.class );
                    startActivity(intent);
                    finish();
                  /*  if(typnumer.matches("user")) {

                        Intent intent = new Intent(ResturantPageActivity.this,LoginActivity.class );
                        startActivity(intent);

                    }
                    if(typnumer.matches("cooker")) {

                        Intent intent = new Intent(ResturantPageActivity.this,LogincookActivity.class );
                        startActivity(intent);

                    }
                    if(typnumer.matches("restaurant")) {

                        Intent intent = new Intent(ResturantPageActivity.this,LoginResturantActivity.class );
                        startActivity(intent);

                    }*/




                }



            }
        });
        if (cookimage.matches("") || !cookimage.startsWith("http")) {////https://image.flaticon.com/icons/svg/1055/1055672.svg
            Picasso.with(ResturantPageActivity.this).load("https://ibb.co/8ryyNh2").error(R.drawable.ellipse)
                    // .resize(100,100)
                    .into(cookimagecir);
        } else {
            Picasso.with(ResturantPageActivity.this).load(cookimage)
                    // .resize(100,100)
                    .error(R.drawable.ellipse)

                    .into(cookimagecir);


        }
        YoYo.with(Techniques.SlideInDown)
                .duration(1000)
                .playOn(cookimagecir);



        resturenimagerecyclertop = findViewById(R.id.resturenimagerecyclertop);

        LinearLayoutManager manager2 = new LinearLayoutManager(ResturantPageActivity.this);


        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        resturenimagerecyclertop.setLayoutManager(manager2);

    }

    private void getResturantprofileiflogin(String access_token) {
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/restaurant/" + ressturant_id + "/profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HZM", response);

                try {
                    JSONObject task_respnse = new JSONObject(response);
                    JSONObject taskarray = task_respnse.getJSONObject("data");
                    JSONArray menuarray = taskarray.getJSONArray("menu");

                    int id = taskarray.getInt("id");
                    String name = taskarray.getString("name");
                    int countryId = taskarray.getInt("country_id");
                    String countryName = taskarray.getString("country_name");
                    String cityName = taskarray.getString("city_name");
                    String region = taskarray.getString("region");
                    String avatarURL = taskarray.getString("avatar_url");
                    String description = taskarray.getString("description");
                    int followersNo = taskarray.getInt("followers_no");
                    String mobile = taskarray.getString("mobile");
                    full_mobile = taskarray.getString("full_mobile");
                    //toolbar.setTitle(name);
                      isFollowed=taskarray.getBoolean("is_followed");
                      isFavorite=taskarray.getBoolean("is_favorite");
                    if(isFavorite){
                        mCookstar.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black));

                    }
                    else
                    {
                        mCookstar.setImageDrawable(getResources().getDrawable(R.drawable.ic_not_favorite_border));


                    }



                    if(isFollowed){
                        mFollowingbut.setImageDrawable(getResources().getDrawable(R.drawable.unfollowindetils));

                    }
                    else
                    {
                        mFollowingbut.setImageDrawable(getResources().getDrawable(R.drawable.followindetils));


                    }


                    mFollowingbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (saveLogin) {



                                if (!isFollowed)
                                {
                                    isFollowed = true;
                                    followResturant(tokenfromlogin,id);
                                    mFollowingbut.setImageDrawable(getResources().getDrawable(R.drawable.unfollowindetils));



                                }
                                else
                                {
                                    mFollowing = false;
                                    unfollowResturant(tokenfromlogin,id);
                                    mFollowingbut.setImageDrawable(getResources().getDrawable(R.drawable.followindetils));




                                }

                            }
                            else {
                                Intent intent=new Intent(ResturantPageActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }


                        }
                    });

                    mCookstar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (saveLogin) {

                                if (!isFavorite) {
                                    isFavorite = true;
                                    favoriteResturant(tokenfromlogin,id);

                                    mCookstar.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black));

                                    //followDrawable.startTransition(transitionDuration);
                                } else {
                                    mFavorite = false;
                                    unfavoriteResturant(tokenfromlogin,id);

                                    mCookstar.setImageDrawable(getResources().getDrawable(R.drawable.ic_not_favorite_border));


                                }


                            }

                            else
                            {
                                Intent intent=new Intent(ResturantPageActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }

                        }
                    });




                    mCookDesc.setText(description);
                    mCountfollow.setText(followersNo + "");
                    mCookName.setText(name);
                    mAblePhoneLogin.setText(mobile);
                    mCountryCook.setText("الدولة :"+"  "+countryName);
                    if(region.isEmpty() ||region.matches("") ||region.matches("غير محدد")  ){
                        mCityState.setVisibility(View.GONE);

                    }
                    else {
                        mCityState.setVisibility(View.GONE);

                    }

                    // city
                    if(cityName.isEmpty() ||cityName.matches("") ||cityName.matches("غير محدد")  ){
                        mCityCook.setVisibility(View.GONE);
                        setMargins(mAblePhoneLogin,0,70,0,0);
                        setMargins(mImageView2,0,75,0,0);
                        setMargins(mCookPhone,0,70,0,0);


                    }
                    else {
                        mCityCook.setText(  "المدينة : "+"  "+cityName);

                    }



                    if (avatarURL.matches("") || !avatarURL.startsWith("http")) {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                        Picasso.with(ResturantPageActivity.this).load(avatarURL).error(R.drawable.ellipse)
                                // .resize(100,100)
                                .into(cookimagecir);
                    } else {
                        Picasso.with(ResturantPageActivity.this).load(avatarURL)
                                // .resize(100,100)
                                .error(R.drawable.ellipse)

                                .into(cookimagecir);


                    }

                    YoYo.with(Techniques.SlideInDown)
                            .duration(1000)
                            .playOn(findViewById(R.id.cookimage));



                    if (saveLogin) {
                        mAblePhoneLogin.setText(mobile);
                        mImageView2.setVisibility(View.GONE);

                    } else {
                        mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                        mImageView2.setVisibility(View.VISIBLE);
                        mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ResturantPageActivity.this, LoginResturantActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }


                    if (menuarray.getString(0).isEmpty()) {


                        menuimage.clear();
                        menurecycler.setVisibility(View.GONE);
                        menuword.setVisibility(View.GONE);
                        view_line.setVisibility(View.GONE);




                    } else {

                        for (int i = 0; i < menuarray.length(); i++) {
                            menuResturantModel = new MenuResturantModel();
                            menuResturantModel.setResturantmenu("https://livecook.co/image/" + menuarray.get(i).toString());
                            menuResturantModel.setImage_name(menuarray.get(i).toString());
                            menuimage.add(menuResturantModel);

                        }

                    }

                    resturantmenuAdapter = new ResturantmenuAdapterView(menuimage, ResturantPageActivity.this);
                    menurecycler.setAdapter(resturantmenuAdapter);
                    resturantmenuAdapter.notifyDataSetChanged();


                } catch (JSONException e1) {
                    e1.printStackTrace();

                }


                 hideDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        })

        {

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
        }


                ;

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }


    public void getResturantprofile() {

        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/restaurant/" + ressturant_id + "/profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HZM", response);

                try {
                    JSONObject task_respnse = new JSONObject(response);
                    JSONObject taskarray = task_respnse.getJSONObject("data");
                    JSONArray menuarray = taskarray.getJSONArray("menu");

                    int id = taskarray.getInt("id");
                    String name = taskarray.getString("name");
                    int countryId = taskarray.getInt("country_id");
                    String countryName = taskarray.getString("country_name");
                    String cityName = taskarray.getString("city_name");
                    String region = taskarray.getString("region");
                    String avatarURL = taskarray.getString("avatar_url");
                    String description = taskarray.getString("description");
                    int followersNo = taskarray.getInt("followers_no");
                    String mobile = taskarray.getString("mobile");
                    full_mobile = taskarray.getString("full_mobile");
                   // toolbar.setTitle(name);

                    mCookDesc.setText(description);
                    mCountfollow.setText(followersNo + "");
                    mCookName.setText(name);
                    mAblePhoneLogin.setText(mobile);
                    mCountryCook.setText("الدولة:" + countryName);
                    if(region.isEmpty() ||region.matches("") ||region.matches("غير محدد")  ){
                        mCityState.setVisibility(View.GONE);

                    }
                    else {
                        mCityState.setVisibility(View.GONE);


                    }

                    // city
                    if(cityName.isEmpty() ||cityName.matches("") ||cityName.matches("غير محدد")  ){
                        mCityCook.setVisibility(View.GONE);
                        setMargins(mAblePhoneLogin,0,70,0,0);
                        setMargins(mImageView2,0,75,0,0);
                        setMargins(mCookPhone,0,70,0,0);



                    }
                    else {
                        mCityCook.setText(  "المدينة : "+"  "+cityName);

                    }
                    if (avatarURL.matches("") || !avatarURL.startsWith("http")) {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                        Picasso.with(ResturantPageActivity.this).load(avatarURL).error(R.drawable.ellipse)
                                // .resize(100,100)
                                .into(cookimagecir);
                    } else {
                        Picasso.with(ResturantPageActivity.this).load(avatarURL)
                                // .resize(100,100)
                                .error(R.drawable.ellipse)

                                .into(cookimagecir);


                    }

                    YoYo.with(Techniques.SlideInDown)
                            .duration(1000)
                            .playOn(cookimagecir);




                    mCookstar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!saveLogin) {
                                Intent intent = new Intent(ResturantPageActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                        }
                    });

                    mFollowingbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(ResturantPageActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    });



                    if (saveLogin) {
                        mAblePhoneLogin.setText(mobile);
                        mImageView2.setVisibility(View.GONE);

                    } else {
                        mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                        mImageView2.setVisibility(View.VISIBLE);
                        mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ResturantPageActivity.this, LoginResturantActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }


                    if (menuarray.getString(0).isEmpty()) {

                        menuimage.clear();
                        menurecycler.setVisibility(View.GONE);
                        menuword.setVisibility(View.GONE);
                        view_line.setVisibility(View.GONE);



                    } else {

                        for (int i = 0; i < menuarray.length(); i++) {
                            menuResturantModel = new MenuResturantModel();
                            menuResturantModel.setResturantmenu("https://livecook.co/image/" + menuarray.get(i).toString());
                            menuResturantModel.setImage_name(menuarray.get(i).toString());
                            menuimage.add(menuResturantModel);

                        }

                    }

                    resturantmenuAdapter = new ResturantmenuAdapterView(menuimage, ResturantPageActivity.this);
                    menurecycler.setAdapter(resturantmenuAdapter);
                    resturantmenuAdapter.notifyDataSetChanged();


                } catch (JSONException e1) {
                    e1.printStackTrace();

                }


                 hideDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }



    public void getprofileForretsurantandcooker() {

        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/restaurant/" + ressturant_id + "/profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HZM", response);

                try {
                    JSONObject task_respnse = new JSONObject(response);
                    JSONObject taskarray = task_respnse.getJSONObject("data");
                    JSONArray menuarray = taskarray.getJSONArray("menu");

                    int id = taskarray.getInt("id");
                    String name = taskarray.getString("name");
                    int countryId = taskarray.getInt("country_id");
                    String countryName = taskarray.getString("country_name");
                    String cityName = taskarray.getString("city_name");
                    String region = taskarray.getString("region");
                    String avatarURL = taskarray.getString("avatar_url");
                    String description = taskarray.getString("description");
                    int followersNo = taskarray.getInt("followers_no");
                    String mobile = taskarray.getString("mobile");
                    full_mobile = taskarray.getString("full_mobile");
                    //toolbar.setTitle(name);
                    mCookDesc.setText(description);
                    mCountfollow.setText(followersNo + "");
                    mCookName.setText(name);
                    mAblePhoneLogin.setText(mobile);
                    mCountryCook.setText("الدولة : "+"   "+countryName);

                    if(region.isEmpty() ||region.matches("") ||region.matches("غير محدد")  ){
                        mCityState.setVisibility(View.GONE);

                    }
                    else {
                        mCityState.setText("الحي  : " + "  " + region);

                    }

                    // city
                    if(cityName.isEmpty() ||cityName.matches("") ||cityName.matches("غير محدد")  ){
                        mCityCook.setVisibility(View.GONE);

                    }
                    else {
                        mCityCook.setText(  "المدينة : "+"  "+cityName);

                    }
                    if (avatarURL.matches("") || !avatarURL.startsWith("http")) {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                        Picasso.with(ResturantPageActivity.this).load(avatarURL).error(R.drawable.ellipse)
                                // .resize(100,100)
                                .into(cookimagecir);
                    } else {
                        Picasso.with(ResturantPageActivity.this).load(avatarURL)
                                // .resize(100,100)
                                .error(R.drawable.ellipse)

                                .into(cookimagecir);


                    }

                    YoYo.with(Techniques.SlideInDown)
                            .duration(1000)
                            .playOn(cookimagecir);



                    mCookstar.setVisibility(View.GONE);
                    mFollowingbut.setVisibility(View.GONE);


                    mCookstar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!saveLogin) {
                                Intent intent = new Intent(ResturantPageActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                        }
                    });

                    mFollowingbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(ResturantPageActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    });



                    if (saveLogin) {
                        mAblePhoneLogin.setText(mobile);
                        mImageView2.setVisibility(View.GONE);

                    } else {
                        mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                        mImageView2.setVisibility(View.VISIBLE);

                        mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ResturantPageActivity.this, LoginResturantActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }


                    if (menuarray.getString(0).isEmpty()) {

                        menuimage.clear();
                        menurecycler.setVisibility(View.GONE);
                        menuword.setVisibility(View.GONE);
                        view_line.setVisibility(View.GONE);




                    } else {

                        for (int i = 0; i < menuarray.length(); i++) {
                            menuResturantModel = new MenuResturantModel();
                            menuResturantModel.setResturantmenu("https://livecook.co/image/" + menuarray.get(i).toString());
                            menuResturantModel.setImage_name(menuarray.get(i).toString());
                            menuimage.add(menuResturantModel);

                        }

                    }

                    resturantmenuAdapter = new ResturantmenuAdapterView(menuimage, ResturantPageActivity.this);
                    menurecycler.setAdapter(resturantmenuAdapter);
                    resturantmenuAdapter.notifyDataSetChanged();


                } catch (JSONException e1) {
                    e1.printStackTrace();

                }


                hideDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }


    @Override
    public void onStart() {
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                resturantImagestop.clear();
                for (DataSnapshot livenapshot : dataSnapshot.getChildren()) {

                    AllFirebaseModel detilas = livenapshot.getValue(AllFirebaseModel.class);
                    resturantImagestop.add(detilas);

                }

                }

                resturantImagetopAdapter = new ResturantImagetopAdapter1(resturantImagestop, ResturantPageActivity.this);
                resturenimagerecyclertop.setAdapter(resturantImagetopAdapter);
                resturantImagetopAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
        super.onStart();
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
            return true;

        }

        if (item.getItemId() == R.id.nav_home) {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;

        }


        if (item.getItemId() == R.id.nav_home) {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);

    }


    public void followResturant (  final String access_token,final int Resturant_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/follow/restaurant/"+Resturant_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    //Toast.makeText(ResturantPageActivity.this, "تمت المتابعة  ", Toast.LENGTH_SHORT).show();


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
                map.put("Authorization", "Bearer" + " " + access_token);



                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + " " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }

    public void unfollowResturant (  final String access_token,final  int Resturant_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH,
                "https://livecook.co/api/v1/user/unfollow/restaurant/"+Resturant_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    //Toast.makeText(ResturantPageActivity.this, "تمت المتابعة  " , Toast.LENGTH_SHORT).show();


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
                map.put("Authorization", "Bearer" + " " + access_token);



                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + " " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }


    public void favoriteResturant(  final String access_token,final int Resturant_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/favorite/restaurant/"+Resturant_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    //Toast.makeText(ResturantPageActivity.this, "تمت الاضافة الى المفضلة  " , Toast.LENGTH_SHORT).show();


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
                map.put("Authorization", "Bearer" + " " + access_token);



                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + " " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }

    public void unfavoriteResturant (  final String access_token,final int Resturant_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/unfavorite/restaurant/"+Resturant_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    //Toast.makeText(ResturantPageActivity.this, "تمت الازالة من المفضلة  " , Toast.LENGTH_SHORT).show();


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
                map.put("Authorization", "Bearer" + " " + access_token);



                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + " " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }

    public void showDialog() {

        progressDialog = new ProgressDialog(ResturantPageActivity.this);
         progressDialog.setMessage(getString(R.string.load));
        progressDialog.setCancelable(false);


        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())

            progressDialog.dismiss();

    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            final float scale = getResources().getDisplayMetrics().density;
            // convert the DP into pixel
            int l =  (int)(left * scale + 0.5f);
            int r =  (int)(right * scale + 0.5f);
            int t =  (int)(top * scale + 0.5f);
            int b =  (int)(bottom * scale + 0.5f);

            p.setMargins(l, t, r, b);
            view.requestLayout();
        }
    }


}
