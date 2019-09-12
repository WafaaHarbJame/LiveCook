package com.livecook.livecookapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.AllFirebaseModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.ResturantImage;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CookPageActivity extends AppCompatActivity {
    ProgressDialog progressDialog;




    private DatabaseReference mFirebaseDatabase;
    Boolean  isFollowed,isFavorite=false;
    boolean mFollowing=true;
    boolean mFavorite=true;

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
    TextView cook_type_tv;
    private TextView mAblePhoneLogin;
    public CircleImageView cookimagecir;
    public Button contactwahts ;
    SharedPreferences prefs;
    String full_mobile;
    int ressturant_id;
    private Button mButton;
    public String cookimage = "cookimage";
    public String cook_name = "cook_name";
    public  String cook_desc= "cook_desc";
    public  String cook_address= "cook_address";
    public  String countfollow= "countfollow";
    public  String cook_country= "cook_country";
    public String cook_city= "cook_city";
    public String cook_state= "cook_state";
    public  String cook_phone= "cook_phone";
    public  int cook_id,cook_type_id=6;
            public int cook_counuty_id;
    ResturantImagetopAdapter1 resturantImagetopAdapter;
    ArrayList<ResturantImage> data = new ArrayList<>();
    RecyclerView resturenimagerecyclertop;
    private Boolean saveLogin;
    Toolbar toolbar;
    String tokenfromlogin;
    String typnumer;

    ArrayList<AllFirebaseModel> resturantImagestop = new ArrayList<>();

    SwipeRefreshLayout cookswip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_page);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
      toolbar.setTitle("صفحة الطباخ  ");
        cookswip = findViewById(R.id.cookswip);
        progressDialog = new ProgressDialog(CookPageActivity.this);

        cookswip.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mCookstar = findViewById(R.id.cookstar);
        mCookimage =findViewById(R.id.cookimage);
        mCookName =findViewById(R.id.cook_name);
        mCookDesc = findViewById(R.id.cook_desc);
        mFollower =findViewById(R.id.follower);
        mCountfollow = findViewById(R.id.countfollow);

        mFollowingbut = findViewById(R.id.followingbut);
        mCountryCook =findViewById(R.id.country_cook);
        mCityCook =findViewById(R.id.city_cook);
        mCityState = findViewById(R.id.city_state);
        mCookPhone = findViewById(R.id.cook_phone);
        cookimagecir=findViewById(R.id.cookimage);
        cook_type_tv=findViewById(R.id.cook_type);
        contactwahts=findViewById(R.id.contactwahts);
        progressDialog = new ProgressDialog(CookPageActivity.this);

        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

        saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);

        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");
        typnumer = prefs.getString(Constants.TYPE, "user");


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
                        Toast.makeText(CookPageActivity.this, "Please install whatsApp", Toast.LENGTH_SHORT).show();
                    }

                }


                else {

                    Intent intent = new Intent(CookPageActivity.this, LoginPageActivity.class);
                    startActivity(intent);
                    finish();
                    /*
                          if(typnumer!=null) {

                        if (typnumer.matches("user")) {

                            Intent intent = new Intent(CookPageActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        if (typnumer.matches("cooker")) {

                            Intent intent = new Intent(CookPageActivity.this, LogincookActivity.class);
                            startActivity(intent);

                        }
                        if (typnumer.matches("restaurant")) {

                            Intent intent = new Intent(CookPageActivity.this, LoginResturantActivity.class);
                            startActivity(intent);

                        }

                    }
                     */




                }



            }

        });



        resturenimagerecyclertop = findViewById(R.id.resturenimagerecyclertop);

        //editsearch = view.findViewById(R.id.simpleSearchView);
        resturenimagerecyclertop=findViewById(R.id.resturenimagerecyclertop);
        //RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(),1);
        LinearLayoutManager manager = new LinearLayoutManager(CookPageActivity.this);
        LinearLayoutManager manager2 = new LinearLayoutManager(CookPageActivity.this);

        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        resturenimagerecyclertop.setLayoutManager(manager2);



        mImageView2 = findViewById(R.id.imageView2);
        mAblePhoneLogin = findViewById(R.id.able_phone_login);
        Intent intent=getIntent();
        //Bundle args = getArguments();
        cook_counuty_id = intent.getIntExtra(Constants.cook_counuty_id,-1);
      //  cook_type_id = intent.getIntExtra(Constants.cook_type_id,6);
         ressturant_id = intent.getIntExtra(Constants.cook_id, -1);

        cook_counuty_id=intent.getIntExtra(Constants.cook_counuty_id,-1);
       // cook_type_id=intent.getIntExtra(Constants.cook_type_id,-1);
        cook_id=intent.getIntExtra(Constants.cook_id,-1);


        if (typnumer.matches("cooker")||typnumer.matches("restaurant")) {

            getprofileForcookerorresturart();

        }
        if (typnumer.matches("user")) {
            if (saveLogin) {


                getCookerprofileiflogin(tokenfromlogin);


            }
            else{

                getCookerprofile();



            }







        }

        resturenimagerecyclertop.setHasFixedSize(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live").child(cook_id+"_"+cook_type_id);
        //Toast.makeText(this, "cook_id"+cook_id,Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "cook_type"+cook_type_id, Toast.LENGTH_SHORT).show();


        setTitle(getString(R.string.cookpage));

    }






    public void getCookerprofile() {

        cookswip.setRefreshing(true);


        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/cooker/"+cook_id+"/profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                            //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                            JSONObject taskarray = task_respnse.getJSONObject("data");

                            int id=taskarray.getInt("id");
                            String name = taskarray.getString("name");
                            int countryId = taskarray.getInt("country_id");
                            String countryName = taskarray.getString("country_name");
                            String cityName  = taskarray.getString("city_name");
                            String region  = taskarray.getString("region");
                            String avatarURL  = taskarray.getString("avatar_url");
                            String description  = taskarray.getString("description");
                            int followersNo  = taskarray.getInt("followers_no");
                            String mobile  = taskarray.getString("mobile");
                            cook_type_id=taskarray.getInt("type_id");
                            String type_name = taskarray.getString("type_name");
                            full_mobile=taskarray.getString("full_mobile");

                            mCookDesc.setText(description);
                            cook_type_tv.setText(type_name);
                            mCountfollow.setText(followersNo+"");
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


                            mCountfollow.setText(followersNo+"");

                            if(avatarURL.matches("") || !avatarURL.startsWith("http"))
                            {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                                Picasso.with(CookPageActivity.this)
                                        .load(avatarURL)
                                        .error(R.drawable.ellipse)
                                        // .resize(100,100)
                                        .into(cookimagecir);
                            }
                            else {
                                Picasso.with(CookPageActivity.this).load(avatarURL)
                                        // .resize(100,100)
                                        .error(R.drawable.ellipse)

                                        .into(cookimagecir);


                            }


                            YoYo.with(Techniques.SlideInDown)
                                    .duration(1000)

                                    .playOn(findViewById(R.id.cookimage));





                            mCookstar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (!saveLogin) {
                                        Intent intent = new Intent(CookPageActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }

                                }
                            });

                            mFollowingbut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!saveLogin) {
                                        Intent intent = new Intent(CookPageActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }

                                }
                            });




                            if (saveLogin) {
                                mAblePhoneLogin.setText(mobile);
                                mImageView2.setVisibility(View.GONE);

                            }
                            else {
                                mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                                mImageView2.setVisibility(View.VISIBLE);
                                mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(CookPageActivity.this, LogincookActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }

                            cookswip.setRefreshing(false);












                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            cookswip.setRefreshing(false);


                        }


                         hideDialog();





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();
                cookswip.setRefreshing(false);



            }
        });

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }


    public void getprofileForcookerorresturart() {

        cookswip.setRefreshing(true);


        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/cooker/"+cook_id+"/profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                            //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                            JSONObject taskarray = task_respnse.getJSONObject("data");

                            int id=taskarray.getInt("id");
                            String name = taskarray.getString("name");
                            int countryId = taskarray.getInt("country_id");
                            String countryName = taskarray.getString("country_name");
                            String cityName  = taskarray.getString("city_name");
                            String region  = taskarray.getString("region");
                            String avatarURL  = taskarray.getString("avatar_url");
                            String description  = taskarray.getString("description");
                            int followersNo  = taskarray.getInt("followers_no");
                            String mobile  = taskarray.getString("mobile");
                            cook_type_id=taskarray.getInt("type_id");
                            String type_name = taskarray.getString("type_name");
                            full_mobile=taskarray.getString("full_mobile");
                            mCookDesc.setText(description);
                            //toolbar.setTitle(name);
                            cook_type_tv.setText(type_name);
                            mCountfollow.setText(followersNo+"");
                            mCookName.setText(name);

                            mAblePhoneLogin.setText(mobile);
                            mCountryCook.setText("الدولة :"+"  "+countryName);






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








                            mCookstar.setVisibility(View.GONE);
                            mFollowingbut.setVisibility(View.GONE);
                            mFollowingbut.setVisibility(View.GONE);
                            if(avatarURL.matches("") || !avatarURL.startsWith("http"))
                            {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                                Picasso.with(CookPageActivity.this)
                                        .load(avatarURL)
                                        .error(R.drawable.ellipse)
                                        // .resize(100,100)
                                        .into(cookimagecir);
                            }
                            else {
                                Picasso.with(CookPageActivity.this).load(avatarURL)
                                        // .resize(100,100)
                                        .error(R.drawable.ellipse)

                                        .into(cookimagecir);


                            }

                            mCookstar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (!saveLogin) {
                                        Intent intent = new Intent(CookPageActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }

                                }
                            });

                            mFollowingbut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!saveLogin) {
                                        Intent intent = new Intent(CookPageActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }

                                }
                            });




                            if (saveLogin) {
                                mImageView2.setVisibility(View.GONE);
                                mAblePhoneLogin.setText(mobile);

                            }
                            else {
                                mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                                mImageView2.setVisibility(View.VISIBLE);

                                mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(CookPageActivity.this, LogincookActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }

                            cookswip.setRefreshing(false);












                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            cookswip.setRefreshing(false);


                        }


                        hideDialog();





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();
                cookswip.setRefreshing(false);



            }
        });

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }

    public void showDialog() {
        progressDialog = new ProgressDialog(CookPageActivity.this);
      progressDialog.setMessage(getString(R.string.load));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }


    @Override
    public void onStart() {
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    resturantImagestop.clear();
                    for (DataSnapshot livenapshot : dataSnapshot.getChildren()) {
                        cookswip.setRefreshing(true);
                        AllFirebaseModel detilas = livenapshot.getValue(AllFirebaseModel.class);
                        resturantImagestop.add(detilas);


                    }
                }



                resturantImagetopAdapter = new ResturantImagetopAdapter1(resturantImagestop, CookPageActivity.this);
                resturenimagerecyclertop.setAdapter(resturantImagetopAdapter);
                resturantImagetopAdapter.notifyDataSetChanged();
                cookswip.setRefreshing(false);


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

        else {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


        }

        if (item.getItemId() == R.id.nav_home) {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;

        }



        return super.onOptionsItemSelected(item);

    }








    public void getCookerprofileiflogin(final  String access_token) {

        cookswip.setRefreshing(true);


        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/cooker/"+cook_id+"/profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                            //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                            JSONObject taskarray = task_respnse.getJSONObject("data");

                            int id=taskarray.getInt("id");
                            String name = taskarray.getString("name");
                            int countryId = taskarray.getInt("country_id");
                            String countryName = taskarray.getString("country_name");
                            String cityName  = taskarray.getString("city_name");
                            String region  = taskarray.getString("region");
                            String avatarURL  = taskarray.getString("avatar_url");
                            String description  = taskarray.getString("description");
                            int followersNo  = taskarray.getInt("followers_no");
                            String mobile  = taskarray.getString("mobile");
                            int type_id=taskarray.getInt("type_id");
                            String type_name = taskarray.getString("type_name");
                            full_mobile=taskarray.getString("full_mobile");

                           // toolbar.setTitle(name);

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
                                            followcooker(tokenfromlogin,id);
                                            mFollowingbut.setImageDrawable(getResources().getDrawable(R.drawable.unfollowindetils));



                                        }
                                        else
                                        {
                                            mFollowing = false;
                                            unfollowfollowcooker(tokenfromlogin,id);
                                            mFollowingbut.setImageDrawable(getResources().getDrawable(R.drawable.followindetils));




                                        }

                                    }
                                    else {
                                        Intent intent=new Intent(CookPageActivity.this, LoginActivity.class);
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
                                            favoritecooker(tokenfromlogin,id);

                                           mCookstar.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black));

                                            //followDrawable.startTransition(transitionDuration);
                                        } else {
                                            mFavorite = false;
                                            unfavoritecooker(tokenfromlogin,id);

                                         mCookstar.setImageDrawable(getResources().getDrawable(R.drawable.ic_not_favorite_border));


                                        }


                                    }

                                    else
                                    {
                                        Intent intent=new Intent(CookPageActivity.this, LoginActivity.class);
                                        startActivity(intent);

                                    }

                                }
                            });



                            mCookDesc.setText(description);
                            cook_type_tv.setText(type_name);
                            mCountfollow.setText(followersNo+"");
                            mCookName.setText(name);

                            mAblePhoneLogin.setText(mobile);
                            mCountryCook.setText("الدولة:"+countryName);
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
                            if(avatarURL.matches("") || !avatarURL.startsWith("http"))
                            {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                                Picasso.with(CookPageActivity.this)
                                        .load(avatarURL)
                                        .error(R.drawable.ellipse)
                                        // .resize(100,100)
                                        .into(cookimagecir);
                            }
                            else {
                                Picasso.with(CookPageActivity.this).load(avatarURL)
                                        // .resize(100,100)
                                        .error(R.drawable.ellipse)

                                        .into(cookimagecir);


                            }



                            if (saveLogin) {
                                mAblePhoneLogin.setText(mobile);
                                mImageView2.setVisibility(View.GONE);

                            }
                            else {
                                mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                                mImageView2.setVisibility(View.VISIBLE);
                                mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(CookPageActivity.this, LogincookActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }






                            cookswip.setRefreshing(false);


// her


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            cookswip.setRefreshing(false);


                        }

  hideDialog();





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cookswip.setRefreshing(false);


                hideDialog();


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



    public void followcooker (  final String access_token,final int Cooker_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/follow/cooker/"+Cooker_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    //Toast.makeText(CookPageActivity.this, "تمت المتابعة  " , Toast.LENGTH_SHORT).show();



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cookswip.setRefreshing(false);


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





    public void unfollowfollowcooker (  final String access_token,final int Cooker_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/favorite/cooker/"+Cooker_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    //Toast.makeText(CookPageActivity.this, "تمت المتابعة  " , Toast.LENGTH_SHORT).show();



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cookswip.setRefreshing(false);


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


    public void favoritecooker (  final String access_token,final int Cooker_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/favorite/cooker/"+Cooker_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                   // Toast.makeText(CookPageActivity.this, "تمت الاضافة الى المفضلة  " , Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cookswip.setRefreshing(false);


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

    public void unfavoritecooker (  final String access_token,final int Cooker_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/unfavorite/cooker/"+Cooker_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                  //  Toast.makeText(CookPageActivity.this, "تمت الازالة من المفضلة  " , Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cookswip.setRefreshing(false);


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
