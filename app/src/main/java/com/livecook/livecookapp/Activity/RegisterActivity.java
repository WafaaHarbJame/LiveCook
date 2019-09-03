package com.livecook.livecookapp.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.livecook.livecookapp.Api.AppController;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Fragment.AboutFragment;
import com.livecook.livecookapp.Fragment.AcoountSettingFragment;
import com.livecook.livecookapp.Fragment.AllFragment;
import com.livecook.livecookapp.Fragment.AllResturant_Fragment;
import com.livecook.livecookapp.Fragment.AllcookFragment;
import com.livecook.livecookapp.Fragment.ContactFragment;
import com.livecook.livecookapp.Fragment.CookFragment;
import com.livecook.livecookapp.Fragment.FavvirtoFragment;
import com.livecook.livecookapp.Fragment.HomeFragment;
import com.livecook.livecookapp.Fragment.NotificationFragment;
import com.livecook.livecookapp.Fragment.PersonalCookerFragment;
import com.livecook.livecookapp.Fragment.PersonalResurantFragment;
import com.livecook.livecookapp.Fragment.PoliceFragment;
import com.livecook.livecookapp.Fragment.RegisterFragment;
import com.livecook.livecookapp.Fragment.ResturantFragment;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.MyInterface;
import com.livecook.livecookapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFilterChangeListener {
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
    private long exitTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle(getString(R.string.choose_account));

        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, MODE_PRIVATE);



        if(prefs!=null) {

            typnumer = prefs.getString(Constants.TYPE, "user");
            tokenfromlogin = prefs.getString(Constants.access_token1, "default value");
            saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);


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






        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new RegisterFragment(), "HomeFragment").addToBackStack(null).commit();
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
        searchView.setVisibility(View.GONE);

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

                    Intent intent = new Intent(RegisterActivity.this, SearchAllActivity.class);
                    startActivity(intent);

                    // Do something here
                } else if (homeFragment.getCurrentf() instanceof AllcookFragment) {

                    searchView.setVisibility(View.VISIBLE);
                    searchView.clearFocus();
                    searchView.setInputType(InputType.TYPE_NULL);
                    searchView.setFocusableInTouchMode(false);
                    searchView.setFocusable(false);
                    Intent intent = new Intent(RegisterActivity.this, SearchActivity.class);
                    startActivity(intent);

                } else if (homeFragment.getCurrentf() instanceof AllResturant_Fragment) {
                    searchView.setVisibility(View.VISIBLE);
                    searchView.clearFocus();
                    searchView.setEnabled(false);
                    searchView.setInputType(InputType.TYPE_NULL);
                    searchView.setFocusableInTouchMode(false);
                    searchView.setFocusable(false);
                    Intent intent = new Intent(RegisterActivity.this, SearchResturant.class);
                    startActivity(intent);


                }


            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homeFragment.getCurrentf() instanceof AllcookFragment) {
                    Intent intent = new Intent(RegisterActivity.this, CookFilterrActivity.class);
                    startActivity(intent);


                } else if (homeFragment.getCurrentf() instanceof AllResturant_Fragment) {
                    Intent intent = new Intent(RegisterActivity.this, ResturantFiltertActivity.class);
                    startActivity(intent);


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
            finish();

        }
    }



    public void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        if(saveLogin){
            menu.findItem(R.id.action_notification).setVisible(true);



        }
        else{

            menu.findItem(R.id.action_notification).setVisible(false);

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
            Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();



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

                        Intent intent = new Intent(RegisterActivity.this, SearchAllActivity.class);
                        startActivity(intent);

                        // Do something here
                    } else if (homeFragment.getCurrentf() instanceof AllcookFragment) {

                        searchView.setVisibility(View.VISIBLE);
                        searchView.clearFocus();
                        searchView.setInputType(InputType.TYPE_NULL);
                        searchView.setFocusableInTouchMode(false);
                        searchView.setFocusable(false);
                        Intent intent = new Intent(RegisterActivity.this, SearchActivity.class);
                        startActivity(intent);

                    } else if (homeFragment.getCurrentf() instanceof AllResturant_Fragment) {
                        searchView.setVisibility(View.VISIBLE);
                        searchView.clearFocus();
                        searchView.setEnabled(false);
                        searchView.setInputType(InputType.TYPE_NULL);
                        searchView.setFocusableInTouchMode(false);
                        searchView.setFocusable(false);
                        Intent intent = new Intent(RegisterActivity.this, SearchResturant.class);
                        startActivity(intent);


                    }


                }
            });


            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (homeFragment.getCurrentf() instanceof AllcookFragment) {
                        Intent intent = new Intent(RegisterActivity.this, CookFilterrActivity.class);
                        startActivity(intent);


                    } else if (homeFragment.getCurrentf() instanceof AllResturant_Fragment) {
                        Intent intent = new Intent(RegisterActivity.this, ResturantFiltertActivity.class);
                        startActivity(intent);


                    }


                }
            });


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
                toolbar.setTitle(getString(R.string.choose_account));
                searchView.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);
                Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

            }


        } else if (id == R.id.nav_search_cook) {
            toolbar.setTitle(getString(R.string.search_cook));
            searchView.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CookFragment()).commit();
            filter.setVisibility(View.VISIBLE);

            searchView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegisterActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            });

            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(RegisterActivity.this, CookFilterrActivity.class);
                    startActivity(intent);
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
                    Intent intent = new Intent(RegisterActivity.this, SearchResturant.class);
                    startActivity(intent);

                }
            });

            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(RegisterActivity.this, ResturantFiltertActivity.class);
                    startActivity(intent);
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

                            dialog = new Dialog(RegisterActivity.this);
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

                            dialog = new Dialog(RegisterActivity.this);
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




                            dialog = new Dialog(RegisterActivity.this);
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
                menu.findItem(R.id.menu_exist).setTitle("تسجيل الدخول");
                searchView.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);
                toolbar.setTitle(getString(R.string.loginword));

                Intent intent = new Intent(RegisterActivity.this, LoginPageActivity.class);
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

                //map.put("date",date);
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
                        // editor.putBoolean(Constants.ISLOGIN,false);
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(RegisterActivity.this, SplashActivity.class);
                        intent.putExtra(Constants.ISLOGIN, false);
                        startActivity(intent);
                        finish();

                        Toast.makeText(RegisterActivity.this, "تم الخروج بنجاح", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "لم يتم تسجيل الخررج" + message, Toast.LENGTH_SHORT).show();

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


}
