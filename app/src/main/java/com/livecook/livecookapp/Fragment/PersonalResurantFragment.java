package com.livecook.livecookapp.Fragment;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.livecook.livecookapp.Activity.LiveResturantActivityy;
import com.livecook.livecookapp.Activity.LoginResturantActivity;
import com.livecook.livecookapp.Adapter.ResturantImageAdapter;
import com.livecook.livecookapp.Adapter.ResturantImagetopAdapter;
import com.livecook.livecookapp.Adapter.ResturantImagetopAdapter1;
import com.livecook.livecookapp.Adapter.ResturantmenuAdapterView;
import com.livecook.livecookapp.Adapter.ResturantmenuAdapterforprofile;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Model.AllFirebaseModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.MenuResturantModel;
import com.livecook.livecookapp.Model.ResturantImage;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalResurantFragment extends Fragment {
    MenuResturantModel menuResturantModel;

    String tokenfromlogin;
     Dialog dialog;
     TextView  menuword;


    private ConstraintLayout mConstraintLayout;
    ImageView view_line;

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
    public Button contactwahts ;
    ArrayList<MenuResturantModel> menuimage = new ArrayList<>();
    int type_id;
    ResturantImagetopAdapter1 resturantImagetopAdapter;



    SharedPreferences.Editor editor;
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
    public  String live_title= "live_title";
    ProgressDialog progressDialog;

    Date currentTime;
    ArrayList<AllFirebaseModel> resturantImagestop = new ArrayList<>();


    RecyclerView resturenimagerecycler;
    ArrayList<ResturantImage> data = new ArrayList<>();
    ResturantImageAdapter resturantImageAdapter;
    ResturantImage resturantImage;
    RecyclerView resturenimagerecyclertop;
    public  int ressturant_id,cook_type_id,cook_counuty_id;
    ResturantmenuAdapterView resturantmenuAdapter;
    RecyclerView menurecycler;
    public  int ressturant_id_profile_publish;
    private Boolean saveLogin;
    SharedPreferences prefs;
    private DatabaseReference mFirebaseDatabase,mFirebaseDatabaseread;
    int counter = 1;
    String full_mobile;
    String name;
    String avatarURL;





    public PersonalResurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_personal_resurant, container, false);

        mCookstar = view.findViewById(R.id.cookstar);
        mCookimage = view.findViewById(R.id.cookimage);
        mCookName = view.findViewById(R.id.cook_name);
        mCookDesc = view.findViewById(R.id.cook_desc);
        mFollower = view.findViewById(R.id.follower);
        mCountfollow = view.findViewById(R.id.countfollow);
        mFollowingbut = view.findViewById(R.id.followingbut);
        mCountryCook = view.findViewById(R.id.country_cook);
        mCityCook = view.findViewById(R.id.city_cook);
        mCityState = view.findViewById(R.id.city_state);
        mCookPhone = view.findViewById(R.id.cook_phone);
        cookimagecir=view.findViewById(R.id.cookimage);
        mImageView2 = view.findViewById(R.id.imageView2);
        progressDialog = new ProgressDialog(getActivity());

        mAblePhoneLogin = view.findViewById(R.id.able_phone_login);
        currentTime = Calendar.getInstance().getTime();
        getActivity().setTitle(getString(R.string.persona));
        contactwahts=view.findViewById(R.id.contactwahts);
        menurecycler = view.findViewById(R.id.menurecylcer);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menurecycler.setLayoutManager(manager);

        menurecycler.setHasFixedSize(true);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menurecycler.setLayoutManager(manager);
        menurecycler.setHasFixedSize(true);
        menuword=view.findViewById(R.id.menuword);
        view_line=view.findViewById(R.id.view_line);

        setHasOptionsMenu(true);

        prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");


        if(prefs!=null){

            ressturant_id_profile_publish=prefs.getInt(Constants.ressturant_id_profile_publish,-1);
          // Toast.makeText(getContext(), "the id ressturant_id_profile_publish "+ressturant_id_profile_publish, Toast.LENGTH_SHORT).show();
            getResturantprofile();


        }

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live");

        mFirebaseDatabaseread = FirebaseDatabase.getInstance().getReference("Live").child(ressturant_id_profile_publish+"_"+0);



        contactwahts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

                 counter = prefs.getInt("counter", 0);

               // Toast.makeText(getActivity(), "First"+counter, Toast.LENGTH_SHORT).show();
                Dexter.withActivity(getActivity())
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.RECORD_AUDIO
                        ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        dialog=new Dialog(getActivity());
                        dialog.setContentView(R.layout.custome_dialog);

                        Button yes=dialog.findViewById(R.id.yes);
                        Button no=dialog.findViewById(R.id.no);
                        EditText livename=dialog.findViewById(R.id.livename);
                        ImageView close=dialog.findViewById(R.id.imageexitgame);

                        dialog.setCancelable(true);

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(livename.getText().toString().isEmpty()){
                                    Toast.makeText(getActivity(), "ادخل عنوان البث", Toast.LENGTH_SHORT).show();

                                }
                                else {

                                    live_title=livename.getText().toString();
                                    livefun();
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









                        /* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();





            }
        });
        if(cookimage.matches("") || !cookimage.startsWith("http"))
        {////https://image.flaticon.com/icons/svg/1055/1055672.svg
            Picasso.with(getContext())
                    .load("https://ibb.co/8ryyNh2")
                    .error(R.drawable.ellipse)
                    // .resize(100,100)
                    .into(cookimagecir);
        }
        else {
            Picasso.with(getContext()).load(cookimage)
                    // .resize(100,100)
                    .error(R.drawable.ellipse)

                    .into(cookimagecir);


        }


        resturenimagerecyclertop = view.findViewById(R.id.resturenimagerecyclertop);


        resturenimagerecyclertop=view.findViewById(R.id.resturenimagerecyclertop);

        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());

        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        resturenimagerecyclertop.setLayoutManager(manager2);






        // Inflate the layout for this fragment
        return view;
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

    public void getResturantprofile() {

        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/restaurant/"+ressturant_id_profile_publish+"/profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HZM",response);

                        try {
                            JSONObject task_respnse=new JSONObject(response);
                           // Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                            JSONObject taskarray = task_respnse.getJSONObject("data");
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

                            mCookDesc.setText(description);
                            mCountfollow.setText(followersNo+"");
                            mCookName.setText(name);

                            mAblePhoneLogin.setText(mobile);

                            mCityCook.setText( "المدينة  : "+ " "+cityName);
                            mCountryCook.setText("الدولة :"+" "+countryName);
                            mCityState.setText("الحي   : "+"  "+region);
                            if(avatarURL.matches("") || !avatarURL.startsWith("http"))
                            {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                                Picasso.with(getContext())
                                        .load(avatarURL)
                                        .error(R.drawable.ellipse)
                                        // .resize(100,100)
                                        .into(cookimagecir);
                            }
                            else {
                                Picasso.with(getContext()).load(avatarURL)
                                        // .resize(100,100)
                                        .error(R.drawable.ellipse)

                                        .into(cookimagecir);


                            }


                            if(menuarray.getString(0).isEmpty()){

                                menuimage.clear();
                                menurecycler.setVisibility(View.GONE);
                                menurecycler.setVisibility(View.GONE);
                                menuword.setVisibility(View.GONE);
                                view_line.setVisibility(View.GONE);




                            }

                            else {
                                menuword.setVisibility(View.VISIBLE);
                                view_line.setVisibility(View.VISIBLE);

                                for (int i = 0; i < menuarray.length(); i++) {
                                    menuResturantModel = new MenuResturantModel();
                                    menuResturantModel.setResturantmenu("https://livecook.co/image/" + menuarray.get(i).toString());
                                    menuResturantModel.setImage_name(menuarray.get(i).toString());
                                    menuimage.add(menuResturantModel);

                                }
                            }

                            resturantmenuAdapter =new ResturantmenuAdapterView(menuimage,getActivity());
                            menurecycler.setAdapter(resturantmenuAdapter);
                            resturantmenuAdapter.notifyDataSetChanged();





                            if (saveLogin) {
                                mAblePhoneLogin.setText(mobile);

                            }
                            else {
                                mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                                mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(getActivity(), LoginResturantActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });


                            }





hideDialog();



                        } catch (JSONException e1) {
                            e1.printStackTrace();

                        }



                         hideDialog();





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();



            }
        });

        MyApplication.getInstance().addToRequestQueue(stringRequest);

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


                        Toast.makeText(getContext(), "تم انشاء البث بنجاح", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(getActivity(), LiveResturantActivityy.class);
                        intent.putExtra(Constants.rtmp_path_resturant,"rtmp://167.86.71.40:1995/livecook/"+ressturant_id_profile_publish
                                +"_0"+"_"+counter);
                        intent.putExtra(Constants.id_liv,ressturant_id_profile_publish);

                        intent.putExtra(Constants.cook_name_profile,mCookName.getText().toString());
                        intent.putExtra(Constants.cookimage_profile,avatarURL);
                        intent.putExtra(Constants.first_child,ressturant_id_profile_publish+"_0");

                        intent.putExtra(Constants.second_child,ressturant_id_profile_publish+"_0"+"_"+new_counter);

                        startActivity(intent);
                       // Toast.makeText(getContext(), "counter in live"+counter, Toast.LENGTH_SHORT).show();
                       // ++counter;


                    } else {
                        Toast.makeText(getContext(), "لم يتم البث" + message, Toast.LENGTH_SHORT).show();

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

    public String formatDate(Date date)
    {
        SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.ENGLISH);
        customFormat.setLenient(false);
        return customFormat.format(date);
    }



    @Override
    public void onStart() {

        mFirebaseDatabaseread.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    if (dataSnapshot.hasChildren()) {
                        resturantImagestop.clear();
                        for (DataSnapshot livenapshot : dataSnapshot.getChildren()) {
                            AllFirebaseModel detilas = livenapshot.getValue(AllFirebaseModel.class);
                            resturantImagestop.add(detilas);
                        }


                        resturantImagetopAdapter = new ResturantImagetopAdapter1(resturantImagestop, getActivity());
                        resturenimagerecyclertop.setAdapter(resturantImagetopAdapter);
                        resturantImagetopAdapter.notifyDataSetChanged();

                    }
                }
                else {
                        resturenimagerecyclertop.setVisibility(View.GONE);
                        resturantImagestop.clear();
                    }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
        super.onStart();
    }
    public void showDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.load));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.editmenu, menu);
        menu.findItem(R.id.action_edit).setVisible(true);



        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new AcoountSettingFragment ()).commit();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
