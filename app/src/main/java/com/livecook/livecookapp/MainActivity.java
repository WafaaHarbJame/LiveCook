package com.livecook.livecookapp;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.os.Handler;
import android.text.InputType;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.livecook.livecookapp.Activity.CookFilterrActivity;
import com.livecook.livecookapp.Activity.LiveKotlenActivity;
import com.livecook.livecookapp.Activity.LiveResturantActivityy;
import com.livecook.livecookapp.Activity.LoginActivity;
import com.livecook.livecookapp.Activity.LoginPageActivity;
import com.livecook.livecookapp.Activity.LoginResturantActivity;
import com.livecook.livecookapp.Activity.LogincookActivity;
import com.livecook.livecookapp.Activity.RegisterActivity;
import com.livecook.livecookapp.Activity.ResturantFiltertActivity;
import com.livecook.livecookapp.Activity.ResturantPageActivity;
import com.livecook.livecookapp.Activity.SearchActivity;
import com.livecook.livecookapp.Activity.SearchAllActivity;
import com.livecook.livecookapp.Activity.SearchResturant;
import com.livecook.livecookapp.Activity.SplashActivity;
import com.livecook.livecookapp.Adapter.ResturantmenuAdapterView;
import com.livecook.livecookapp.Adapter.SearchAllFirebaseResturantCookAdapter;
import com.livecook.livecookapp.Adapter.SearchCookAdapter;
import com.livecook.livecookapp.Adapter.SectionsPagerAdapter;
import com.livecook.livecookapp.Api.AppController;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Fragment.AboutFragment;
import com.livecook.livecookapp.Fragment.AcoountSettingFragment;
import com.livecook.livecookapp.Fragment.AllFragment;
import com.livecook.livecookapp.Fragment.AllResturant_Fragment;
import com.livecook.livecookapp.Fragment.AllcookFragment;
import com.livecook.livecookapp.Fragment.ContactFragment;
import com.livecook.livecookapp.Fragment.CookFragment;
import com.livecook.livecookapp.Fragment.CookPageFragment;
import com.livecook.livecookapp.Fragment.FavvirtoFragment;
import com.livecook.livecookapp.Fragment.HomeFragment;
import com.livecook.livecookapp.Fragment.NotificationFragment;
import com.livecook.livecookapp.Fragment.PersonalCookerFragment;
import com.livecook.livecookapp.Fragment.PersonalResurantFragment;
import com.livecook.livecookapp.Fragment.PoliceFragment;
import com.livecook.livecookapp.Fragment.RegisterFragment;
import com.livecook.livecookapp.Fragment.ResturantFragment;
import com.livecook.livecookapp.Fragment.ResturantPageFragment;
import com.livecook.livecookapp.Model.AllFirebaseModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.MenuResturantModel;
import com.livecook.livecookapp.Model.MyInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFilterChangeListener {
    Toolbar toolbar;
    NavigationView navigationView;
    Dialog dialog;

    EditText searchView;
    String token;
    String fc_token;
    SharedPreferences prefs;
    String tokenfromlogin;
    
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String tooken;
    String typnumer;
    public ImageView filter;
    HomeFragment homeFragment;
    CookFragment cookFragment;
    private MyInterface listener;
    ViewPager viewPager;
    ImageView registerbut;
    FrameLayout rootView;
    private Boolean saveLogin;
    ImageView custmregister_but;
    String fcm_token;
    private long exitTime = 0;
    int counter = 1;
    public String live_title = "live_title";
    private DatabaseReference mFirebaseDatabase;
    public int cook_id_profile_publish;
    int type_id ;
    String name;
    public String full_mobile = "full_mobile";
    String avatarURL;
    public  int ressturant_id_profile_publish;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, MODE_PRIVATE);

        fcm_token = FirebaseInstanceId.getInstance().getToken();
        if(fcm_token!=null) {
            Log.d("khtwotoken", fcm_token);
        }

        if(prefs!=null) {

            typnumer = prefs.getString(Constants.TYPE, "user");
            tokenfromlogin = prefs.getString(Constants.access_token1, "default value");
            saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);
            cook_id_profile_publish = prefs.getInt(Constants.cook_id_profile_publish, -1);
            ressturant_id_profile_publish=prefs.getInt(Constants.ressturant_id_profile_publish,-1);


        }







        Intent intent = getIntent();
        if(intent!=null) {
            tooken = intent.getStringExtra(Constants.access_token1);
        }
        searchView = findViewById(R.id.simpleSearchView);
        homeFragment = new HomeFragment();
        filter = findViewById(R.id.filter);
        filter.setVisibility(View.GONE);
        searchView.setInputType(InputType.TYPE_NULL);
        hideKeyboard();
        searchView.clearFocus();

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
            }
        }
        // [END handle_data_extras]



        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, homeFragment, "HomeFragment").commit();
        Date currentTime = Calendar.getInstance().getTime();

       /* Menu menu = navigationView.getMenu();
        MenuItem register_ICON = menu.findItem(R.id.actionregister);

      if (saveLogin) {
            menu.findItem(R.id.menu_exist).setTitle("تسجيل الخروج");


        }
        else {
            menu.findItem(R.id.menu_exist).setTitle("تسجيل الدخول");
            register_ICON.setVisible(false);




        }*/
        // homeFragment.getCuuuu();
       /* if (homeFragment.getCuuuu() instanceof AllFragment) {
            filter.setVisibility(View.GONE);

        }

        if (homeFragment.getCuuuu() instanceof CookFragment) {

            filter.setVisibility(View.VISIBLE);

        }
       else  if (homeFragment.getCuuuu() instanceof ResturantFragment) {

            filter.setVisibility(View.VISIBLE);


        }*/


        searchView.setFocusable(false);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homeFragment.getCurrentf() instanceof AllFragment) {

                    searchView.setVisibility(View.VISIBLE);
                    searchView.clearFocus();
                    searchView.setInputType(InputType.TYPE_NULL);
                    searchView.setFocusableInTouchMode(false);
                    searchView.setFocusable(false);

                    hideKeyboard();

                    Intent intent = new Intent(MainActivity.this, SearchAllActivity.class);
                    startActivity(intent);
                    finish();


                    // Do something here
                } else if (homeFragment.getCurrentf() instanceof AllcookFragment) {

                    searchView.setVisibility(View.VISIBLE);
                    searchView.clearFocus();
                    searchView.setInputType(InputType.TYPE_NULL);
                    searchView.setFocusableInTouchMode(false);
                    searchView.setFocusable(false);
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                    finish();


                } else if (homeFragment.getCurrentf() instanceof AllResturant_Fragment) {
                    searchView.setVisibility(View.VISIBLE);
                    searchView.clearFocus();
                    searchView.setEnabled(false);
                    searchView.setInputType(InputType.TYPE_NULL);
                    searchView.setFocusableInTouchMode(false);
                    searchView.setFocusable(false);
                    Intent intent = new Intent(MainActivity.this, SearchResturant.class);
                    startActivity(intent);
                    finish();


                }


            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homeFragment.getCurrentf() instanceof AllcookFragment) {
                    Intent intent = new Intent(MainActivity.this, CookFilterrActivity.class);
                    startActivity(intent);
                    finish();



                } else if (homeFragment.getCurrentf() instanceof AllResturant_Fragment) {
                    Intent intent = new Intent(MainActivity.this, ResturantFiltertActivity.class);
                    startActivity(intent);
                    finish();



                }


            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);


        if (typnumer.matches("user")) {
            hidedrawermenu();

        } else if (typnumer.matches("cooker")) {
            hidedFaviritermenu();
            showawermenu();

        } else if (typnumer.matches("restaurant")) {
            showawermenu();
            hidedFaviritermenu();


        }


        Menu menu = navigationView.getMenu();
        MenuItem register_ICON = menu.findItem(R.id.actionregister);
        if (saveLogin) {


            menu.findItem(R.id.menu_exist).setTitle("تسجيل الخروج");


        } else {
            menu.findItem(R.id.menu_exist).setTitle("تسجيل الدخول");
            hidedFaviritermenu();
            hidedrawermenu();
            hideAcoountseetingmenu();


        }





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitApp();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_notification).setVisible(false);
        menu.findItem(R.id.action_createlive).setVisible(false);



        if (typnumer.matches("user")) {
                if(saveLogin){

                menu.findItem(R.id.action_notification).setVisible(true);
                    menu.findItem(R.id.action_createlive).setVisible(false);



                }
        }
        else{

            menu.findItem(R.id.action_notification).setVisible(false);
            menu.findItem(R.id.action_createlive).setVisible(false
            );



        }



        if(typnumer.matches("cooker")||typnumer.matches("restaurant")){
            if(saveLogin) {

                menu.findItem(R.id.action_createlive).setVisible(true);

            }
        }




        //  final MenuItem alertMenuItem = menu.findItem(R.id.actionregister);
        // rootView = (FrameLayout) alertMenuItem.getActionView();


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
                toolbar.setTitle(getString(R.string.notfiy));
                searchView.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new NotificationFragment()).commit();





   /*Intent intent=new Intent(MainActivity.this, LoginResturantActivity.class);
   startActivity(intent);*/

            return true;
        }
        if (id == R.id.actionregister) {
            toolbar.setTitle(getString(R.string.choose_account));
            searchView.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();


            //  getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new RegisterFragment()).commit();

            return true;
        }








        if (id == R.id.action_createlive) {

            prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

            counter = prefs.getInt("counter", 0);

            Dexter.withActivity(this).withPermissions(Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if(report.areAllPermissionsGranted()){
                        dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.custome_dialog);
                        Button yes = dialog.findViewById(R.id.yes);
                        Button no = dialog.findViewById(R.id.no);
                        EditText livename = dialog.findViewById(R.id.livename);
                        ImageView close = dialog.findViewById(R.id.imageexitgame);

                        dialog.setCancelable(true);

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (livename.getText().toString().isEmpty()) {
                                    Toast.makeText(MainActivity.this, "ادخل عنوان البث", Toast.LENGTH_SHORT).show();

                                } else {

                                    live_title = livename.getText().toString();

                                   MainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                                    livename.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            if (v == livename) {
                                                if (hasFocus) {
                                                    // Open keyboard
                                                    ((InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(livename, InputMethodManager.SHOW_FORCED);
                                                } else {
                                                    // Close keyboard
                                                    ((InputMethodManager)  MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(livename.getWindowToken(), 0);
                                                }
                                            }
                                        }
                                    });
                                    if (typnumer.matches("cooker")) {
                                        getCookerprofile(tokenfromlogin);

                                    } else if (typnumer.matches("restaurant")) {
                                        getResturantprofile();


                                    }


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

                    if(report.isAnyPermissionPermanentlyDenied()){
                        Toast.makeText(MainActivity.this, "لا يمكنك عمل بث بدون الموافقة على هذه الصلاحيات ", Toast.LENGTH_SHORT).show();


                    }

                    //end of    /* ... */
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).
                    withErrorListener(new PermissionRequestErrorListener() {
                        @Override
                        public void onError(DexterError error) {
                            Toast.makeText(MainActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onSameThread()
                    .check();

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            toolbar.setTitle(getString(R.string.menu_home));
            searchView.setVisibility(View.VISIBLE);

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, homeFragment).commit();
            filter.setVisibility(View.GONE);
            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (homeFragment.getCurrentf() instanceof AllFragment) {

                        searchView.setVisibility(View.VISIBLE);
                        searchView.clearFocus();
                        searchView.setInputType(InputType.TYPE_NULL);
                        searchView.setFocusableInTouchMode(false);
                        searchView.setFocusable(false);

                        hideKeyboard();

                        Intent intent = new Intent(MainActivity.this, SearchAllActivity.class);
                        startActivity(intent);
                        finish();


                        // Do something here
                    } else if (homeFragment.getCurrentf() instanceof AllcookFragment) {

                        searchView.setVisibility(View.VISIBLE);
                        searchView.clearFocus();
                        searchView.setInputType(InputType.TYPE_NULL);
                        searchView.setFocusableInTouchMode(false);
                        searchView.setFocusable(false);
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();


                    } else if (homeFragment.getCurrentf() instanceof AllResturant_Fragment) {
                        searchView.setVisibility(View.VISIBLE);
                        searchView.clearFocus();
                        searchView.setEnabled(false);
                        searchView.setInputType(InputType.TYPE_NULL);
                        searchView.setFocusableInTouchMode(false);
                        searchView.setFocusable(false);
                        Intent intent = new Intent(MainActivity.this, SearchResturant.class);
                        startActivity(intent);
                        finish();



                    }


                }
            });


            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (homeFragment.getCurrentf() instanceof AllcookFragment) {
                        Intent intent = new Intent(MainActivity.this, CookFilterrActivity.class);
                        startActivity(intent);
                        finish();



                    } else if (homeFragment.getCurrentf() instanceof AllResturant_Fragment) {
                        Intent intent = new Intent(MainActivity.this, ResturantFiltertActivity.class);
                        startActivity(intent);
                        finish();



                    }


                }
            });


//////////////////////////////////////////////////////

        } else if (id == R.id.nav_personal_profile) {




            if (saveLogin) {
                toolbar.setTitle(getString(R.string.persona));
                searchView.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);


                //getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new PersonalCookerFragment()).commit();
                if (typnumer.matches("cooker")) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new PersonalCookerFragment()).commit();

                } else if (typnumer.matches("restaurant")) {
                    //  Toast.makeText(this, "type "+typnumer, Toast.LENGTH_SHORT).show();

                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new PersonalResurantFragment()).commit();

                }


            } else {
                toolbar.setTitle(getString(R.string.loginword));
                searchView.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
                startActivity(intent);
                finish();
               // getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new RegisterFragment()).commit();


            }


        } else if (id == R.id.nav_search_cook) {
            toolbar.setTitle(getString(R.string.search_cook));
            searchView.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CookFragment()).commit();
            filter.setVisibility(View.VISIBLE);

            searchView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, CookFilterrActivity.class);
                    startActivity(intent);
                    finish();

                }
            });




        } else if (id == R.id.nav_search_resturant) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new ResturantFragment()).commit();

            toolbar.setTitle(getString(R.string.search_rest));
            searchView.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);
            searchView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SearchResturant.class);
                    startActivity(intent);
                    finish();


                }
            });

            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, ResturantFiltertActivity.class);
                    startActivity(intent);
                    finish();

                }
            });



        } else if (id == R.id.menu_favaoirite) {
            searchView.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);

            toolbar.setTitle(getString(R.string.menu_favaoirite));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new FavvirtoFragment()).commit();


        } else if (id == R.id.menu_police) {
            searchView.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);

            toolbar.setTitle(getString(R.string.menu_police));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new PoliceFragment()).commit();


        } else if (id == R.id.menu_about) {
            toolbar.setTitle(getString(R.string.menu_about));
            searchView.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new AboutFragment()).commit();


        } else if (id == R.id.menu_accountseeting) {
            toolbar.setTitle(getString(R.string.menu_accountseeting));
            searchView.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);

            if (saveLogin&tokenfromlogin!=null) {

                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new AcoountSettingFragment()).commit();



            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new RegisterFragment()).commit();



            }



        } else if (id == R.id.menu_contact) {
            toolbar.setTitle(getString(R.string.menu_contact));
            searchView.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);


            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new ContactFragment()).commit();


        } else if (id == R.id.menu_exist) {


            if (saveLogin) {
                Menu menu = navigationView.getMenu();
                menu.findItem(R.id.menu_exist).setTitle("تسجيل الخروج");

                getObjectFromPreferences(Constants.access_token, "");
                Intent intent = getIntent();


                if (prefs != null) {
                    // Toast.makeText(this, "type "+typnumer, Toast.LENGTH_SHORT).show();

                    if (typnumer.matches("cooker")) {
                        if(tokenfromlogin.matches("")||tokenfromlogin.equals(null)||tokenfromlogin.isEmpty()){

                            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new RegisterFragment()).commit();

                        }
                        else
                        {

                            dialog = new Dialog(MainActivity.this);
                            dialog.setContentView(R.layout.custome_dialog_exit);

                            Button yes = dialog.findViewById(R.id.yes);
                            Button no = dialog.findViewById(R.id.no);
                            EditText livename = dialog.findViewById(R.id.livename);
                            ImageView close = dialog.findViewById(R.id.imageexitgame);

                            dialog.setCancelable(true);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    logout("https://livecook.co/api/v1/cooker/logout", tokenfromlogin);



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

                    } else if (typnumer.matches("restaurant")) {
                        if(tokenfromlogin.matches("")||tokenfromlogin.equals(null)||tokenfromlogin.isEmpty()){

                            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new RegisterFragment()).commit();

                        }
                        else
                        {

                            dialog = new Dialog(MainActivity.this);
                            dialog.setContentView(R.layout.custome_dialog_exit);

                            Button yes = dialog.findViewById(R.id.yes);
                            Button no = dialog.findViewById(R.id.no);
                            EditText livename = dialog.findViewById(R.id.livename);
                            ImageView close = dialog.findViewById(R.id.imageexitgame);

                            dialog.setCancelable(true);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    logout("https://livecook.co/api/v1/restaurant/logout", tokenfromlogin);



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

                    } else if (typnumer.matches("user")) {
                        if(tokenfromlogin.matches("")||tokenfromlogin.equals(null)||tokenfromlogin.isEmpty()){

                            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new RegisterFragment()).commit();

                        }
                        else {




                            dialog = new Dialog(MainActivity.this);
                            dialog.setContentView(R.layout.custome_dialog_exit);

                            Button yes = dialog.findViewById(R.id.yes);
                            Button no = dialog.findViewById(R.id.no);
                            EditText livename = dialog.findViewById(R.id.livename);
                            ImageView close = dialog.findViewById(R.id.imageexitgame);

                            dialog.setCancelable(true);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    logout(Constants.logout_user_url, tokenfromlogin);



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

                    }

                }


            } else {

                Menu menu = navigationView.getMenu();
                menu.findItem(R.id.menu_exist).setTitle(getString(R.string.loginword));
                searchView.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);
                toolbar.setTitle(getString(R.string.loginword));

                Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
                startActivity(intent);
                finish();




            }




        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void login(final String country_id, final String mobile, final String password, final String remember_me, final String fcm_token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.user_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("mobile", mobile);
                map.put("country_id", country_id);
                map.put("password", password);
                map.put("remember_me", remember_me);
                map.put("fcm_token", fcm_token);


                return map;

            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public void logout(final String link, final String access_token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    if (status) {
                        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, MODE_PRIVATE);
                        editor = prefs.edit();
                        editor.remove(Constants.TYPE);
                        editor.clear();
                        editor.apply();
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                        intent.putExtra(Constants.ISLOGIN, false);
                        startActivity(intent);
                        finish();

                        Toast.makeText(MainActivity.this, "تم الخروج بنجاح", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "لم يتم تسجيل الخررج" + message, Toast.LENGTH_SHORT).show();

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

    public Object getObjectFromPreferences(String key, Object defaultObj) {
        final SharedPreferences prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        String value = prefs.getString(key, "");
        Object object = new Gson().fromJson(value, defaultObj.getClass());
        return object;
    }


    public Fragment currentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.mainContainer);
    }

    private void hideItem() {

        getMenuInflater().inflate(R.menu.main, navigationView.getMenu());

        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_personal_profile).setVisible(false);
    }


    private void hidedrawermenu() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_personal_profile).setVisible(false);
    }

    private void showawermenu() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_personal_profile).setVisible(true);
    }

    private void hideAcoountseetingmenu() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_personal_profile).setVisible(false);
        menu.findItem(R.id.menu_accountseeting).setVisible(false);
    }

    private void showacontsetting() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_personal_profile).setVisible(true);
    }


    private void hidedFaviritermenu() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.menu_favaoirite).setVisible(false);
    }

    private void showfaciritemenu() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.menu_favaoirite).setVisible(true);
    }

    @Override
    public void onFilterChange(boolean isVisible) {
        filter.setVisibility(isVisible ? View.VISIBLE : View.GONE);


        }







    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        MenuItem register = menu.findItem(R.id.actionregister);

        MenuItem alertMenuItem = menu.findItem(R.id.actionregister);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();
        custmregister_but = rootView.findViewById(R.id.registerbut);


        if (saveLogin) {
            register.setVisible(false);
        } else {
            register.setVisible(true);


        }


        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Menu menu = navigationView.getMenu();
                MenuItem register_ICON = menu.findItem(R.id.actionregister);

                /*if (saveLogin) {
                    menu.findItem(R.id.menu_exist).setTitle("تسجيل الخروج");
                    register_ICON.setVisible(false);


                }
                else {
                    menu.findItem(R.id.menu_exist).setTitle("تسجيل الدخول");
                    register_ICON.setVisible(true);




                }*/
                onOptionsItemSelected(alertMenuItem);
            }
        });


        return super.onPrepareOptionsMenu(menu);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }


    public void exitApp() {

        if (homeFragment != null && homeFragment.isVisible()) {
            finish();
            // add your code here
        }
        else{
            Intent intent=new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        }


    }



    public void livefuncooker() {

        //http://167.86.71.40:89/livecook/streamname/index.m3u8

//http://167.86.71.40:8384/video/61_6_4.flv.mp4
        //http://167.86.71.40:8384/video/61_6_2.jpg
        final AllFirebaseModel allFirebaseModel = new AllFirebaseModel("rtmp://167.86.71.40:1995/livecook/"
                + cook_id_profile_publish + "_" + type_id + "_" + counter,
                "http://167.86.71.40:8384/" +"video/"
                        + cook_id_profile_publish + "_" + type_id + "_" + counter + ".flv.mp4",
                "http://167.86.71.40:89/livecook/" + cook_id_profile_publish + "_" + type_id + "_" + counter + "/index.m3u8",

                counter, full_mobile, cook_id_profile_publish, name,
                true, live_title, type_id, counter, formatDate(Calendar.getInstance().getTime()),
                "http://167.86.71.40:8384/video/"+ cook_id_profile_publish + "_" + type_id + "_" + counter+".jpg");


        mFirebaseDatabase.child(cook_id_profile_publish + "_" + type_id).child(cook_id_profile_publish + type_id + "_" + counter).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Toast.makeText(getContext(), "the live exist", Toast.LENGTH_SHORT).show();

                    editor = prefs.edit();
                    editor.putInt("counter",counter);
                    editor.commit();
                    editor.apply();
                    counter=counter+1;



                    mFirebaseDatabase.child(cook_id_profile_publish + "_" + type_id).child(cook_id_profile_publish + "_" + type_id + "_" + counter).setValue(allFirebaseModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            createcooklive(dataSnapshot.getRef().toString(), "rtmp://167.86.71.40:1995/livecook/" + cook_id_profile_publish + "_" + type_id + "_" + counter, tokenfromlogin);

                            editor = prefs.edit();
                            editor.putInt("counter",counter);
                            editor.commit();
                            editor.apply();
                        }
                    });


                } else {

                    mFirebaseDatabase.child(cook_id_profile_publish + "_" + type_id).child(cook_id_profile_publish + "_" + type_id + "_" + counter).setValue(allFirebaseModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            createcooklive(dataSnapshot.getRef().toString(), "rtmp://167.86.71.40:1995/livecook/" + cook_id_profile_publish + "_" + type_id + "_" + counter, tokenfromlogin);
                        }





                    });


                }









                ++counter;

                editor = prefs.edit();
                editor.putInt("counter",counter);
                editor.commit();
                editor.apply();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public String formatDate(Date date) {
       // Locale locale = new Locale( "ar" , "SA" ) ;  // Arabic language. Saudi Arabia cultural norms.
        SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        customFormat.setLenient(false);
        customFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return customFormat.format(date);
    }


    public void createcooklive(final String firebase_path, final String livestream_path, final String access_token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.live_cook_path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");

                    if (status) {
                        int new_counter=counter-1;
                        dialog.dismiss();


                        Toast.makeText(MainActivity.this, "تم انشاء البث بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, LiveKotlenActivity.class);
                        intent.putExtra(Constants.cook_name_profile, name);
                        intent.putExtra(Constants.cookimage_profile, avatarURL);
                        // Toast.makeText(getContext(), "cokkern"+name, Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getContext(), "cokkern"+avatarURL, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), "cokkern"+avatarURL, Toast.LENGTH_SHORT).show();
                        String path = "rtmp://167.86.71.40:1995/livecook/" + cook_id_profile_publish + "_" + type_id + "_"
                                + counter;
                        // Toast.makeText(getContext()1, ""+path, Toast.LENGTH_SHORT).show();

                        intent.putExtra(Constants.rtmp_path_cooker, "rtmp://167.86.71.40:1995/livecook/" + cook_id_profile_publish + "_" + type_id + "_" + counter);
                        intent.putExtra(Constants.id_liv, cook_id_profile_publish);

                        intent.putExtra(Constants.first_child, cook_id_profile_publish + "_" + type_id);

                        intent.putExtra(Constants.second_child, cook_id_profile_publish + "_" + type_id +"_"+ new_counter);
                        startActivity(intent);





                    } else {
                        Toast.makeText(MainActivity.this, "لم يتم البث" + message, Toast.LENGTH_SHORT).show();

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
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                headers.put("firebase_path", firebase_path);
                headers.put("livestream_path", livestream_path);

                return headers;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                headers.put("firebase_path", firebase_path);
                headers.put("livestream_path", livestream_path);

                return headers;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }



    public void getCookerprofile(String access_token) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/cooker/" + cook_id_profile_publish + "/profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HZM", response);

                try {
                    JSONObject task_respnse = new JSONObject(response);
                    JSONObject taskarray = task_respnse.getJSONObject("data");
                    boolean status = task_respnse.getBoolean("status");
                    int id = taskarray.getInt("id");
                    name = taskarray.getString("name");
                    int countryId = taskarray.getInt("country_id");
                    String countryName = taskarray.getString("country_name");
                    String cityName = taskarray.getString("city_name");
                    String region = taskarray.getString("region");
                    avatarURL = taskarray.getString("avatar_url");
                    String description = taskarray.getString("description");
                    int followersNo = taskarray.getInt("followers_no");
                    String mobile = taskarray.getString("mobile");
                    type_id = taskarray.getInt("type_id");
                    String type_name = taskarray.getString("type_name");
                    type_id=taskarray.getInt("type_id");
                    if(status){
                        livefuncooker();

                    }





                } catch (JSONException e1) {
                    e1.printStackTrace();

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
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);

                return headers;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);

                return headers;
            };
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }


    public void getResturantprofile() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/restaurant/"+ressturant_id_profile_publish+"/profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                            // Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                            JSONObject taskarray = task_respnse.getJSONObject("data");
                            boolean status = task_respnse.getBoolean("status");

                            JSONArray menuarray = taskarray.getJSONArray("menu");
                            int id=taskarray.getInt("id");
                            name = taskarray.getString("name");
                            int countryId = taskarray.getInt("country_id");
                            String countryName = taskarray.getString("country_name");
                            String cityName  = taskarray.getString("city_name");
                            String region  = taskarray.getString("region");
                            avatarURL  = taskarray.getString("avatar_url");
                            String description  = taskarray.getString("description");
                            int followersNo  = taskarray.getInt("followers_no");
                            String mobile  = taskarray.getString("mobile");
                            full_mobile=taskarray.getString("full_mobile");
                            if(status){
                                livefun();

                            }


                        } catch (JSONException e1) {
                            e1.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }


    private void livefun() {

        // Toast.makeText(getContext(), "counter firesy"+counter, Toast.LENGTH_SHORT).show();
//http://167.86.71.40:8384/video/61_6_4.flv.mp4

        final AllFirebaseModel allFirebaseModel = new AllFirebaseModel("rtmp://167.86.71.40:1995/livecook/"+ressturant_id_profile_publish
                +"_0"+"_"+counter,"http://167.86.71.40:8384/video/"+ressturant_id_profile_publish
                +"_0"+"_"+counter+".flv.mp4","http://167.86.71.40:89/livecook/"+ressturant_id_profile_publish
                +"_0"+"_"+counter+"/index.m3u8",
                counter,full_mobile,ressturant_id_profile_publish,name,true,live_title,type_id
                ,counter,formatDate(Calendar.getInstance().getTime()),"http://167.86.71.40:8384/video/"+ressturant_id_profile_publish
                +"_0"+"_"+counter+".jpg");

        mFirebaseDatabase.child(ressturant_id_profile_publish+"_0")
                .child(ressturant_id_profile_publish+"_0"+"_"+counter).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    editor = prefs.edit();
                    editor.putInt("counter",counter);
                    editor.commit();
                    editor.apply();
                    counter=counter+1;
                    //  Toast.makeText(getContext(), "counter second "+counter, Toast.LENGTH_SHORT).show();

                    // Toast.makeText(getContext(), "the live exist", Toast.LENGTH_SHORT).show();
                    mFirebaseDatabase.child(ressturant_id_profile_publish+"_0")
                            .child(ressturant_id_profile_publish+"_0"+"_"+counter).setValue(allFirebaseModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            createResturantlive(dataSnapshot.getRef().toString(),"rtmp://167.86.71.40:1995/livecook/"+ressturant_id_profile_publish
                                    +"_0"+"_"+counter,tokenfromlogin);
                            editor = prefs.edit();
                            editor.putInt("counter",counter);
                            editor.commit();
                            editor.apply();

                        }
                    });

                    //  Toast.makeText(getContext(), "the live exist", Toast.LENGTH_SHORT).show();



                }

                else{

                    mFirebaseDatabase.child(ressturant_id_profile_publish+"_0")
                            .child(ressturant_id_profile_publish+"_0"+"_"+counter).setValue(allFirebaseModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            createResturantlive(dataSnapshot.getRef().toString(),"rtmp://167.86.71.40:1995/livecook/"+ressturant_id_profile_publish
                                    +"_0"+"_"+counter,tokenfromlogin);



                        }
                    });





                }

                ++counter;

                editor = prefs.edit();
                editor.putInt("counter",counter);
                editor.commit();
                editor.apply();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }




    public void createResturantlive(final String firebase_path, final String livestream_path,final String access_token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.live_restaurant_path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");

                    if (status) {
                        int new_counter=counter-1;
                        dialog.dismiss();


                        Toast.makeText(MainActivity.this, "تم انشاء البث بنجاح", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(MainActivity.this, LiveResturantActivityy.class);
                        intent.putExtra(Constants.rtmp_path_resturant,"rtmp://167.86.71.40:1995/livecook/"+ressturant_id_profile_publish
                                +"_0"+"_"+counter);
                        intent.putExtra(Constants.id_liv,ressturant_id_profile_publish);
                        //intent.putExtra(Constants.cook_name_profile,mCookName.getText().toString());
                        intent.putExtra(Constants.cookimage_profile,avatarURL);
                        intent.putExtra(Constants.first_child,ressturant_id_profile_publish+"_0");

                        intent.putExtra(Constants.second_child,ressturant_id_profile_publish+"_0"+"_"+new_counter);

                        startActivity(intent);
                        // Toast.makeText(getContext(), "counter in live"+counter, Toast.LENGTH_SHORT).show();
                        // ++counter;


                    } else {
                        Toast.makeText(MainActivity.this, "لم يتم البث" + message, Toast.LENGTH_SHORT).show();

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
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                headers.put("firebase_path",firebase_path);
                headers.put("livestream_path", livestream_path);

                return headers;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                headers.put("firebase_path",firebase_path);
                headers.put("livestream_path", livestream_path);

                return headers;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }



}
