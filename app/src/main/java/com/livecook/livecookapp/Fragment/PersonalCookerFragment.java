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
import com.livecook.livecookapp.Activity.CookPageActivity;
import com.livecook.livecookapp.Activity.LiveKotlenActivity;
import com.livecook.livecookapp.Activity.LogincookActivity;
import com.livecook.livecookapp.Activity.RegisterActivity;
import com.livecook.livecookapp.Adapter.ResturantImagetopAdapter;
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
public class PersonalCookerFragment extends Fragment {


    private ConstraintLayout mConstraintLayout;
    private ImageView mCookstar;
    private CircleImageView mCookimage;
    ProgressDialog progressDialog;

    Dialog dialog;

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
    public Button contactwahts;
    SharedPreferences prefs;
    int counter = 1;

    Date currentTime;
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
    public String full_mobile = "full_mobile";


    public int cook_id, cook_type_id, cook_counuty_id;
    ArrayList<ResturantImage> data = new ArrayList<>();
    ResturantImage resturantImage;
    RecyclerView resturenimagerecyclertop;
    private Boolean saveLogin;
    public int cook_id_profile_publish;
    private DatabaseReference mFirebaseDatabase,mFirebaseDatabaseread;
    int type_id = 6;
    String name;
    String tokenfromlogin;
    String avatarURL;
    ArrayList<AllFirebaseModel> resturantImagestop = new ArrayList<>();

    public String live_title = "live_title";
    SharedPreferences.Editor editor;


    ResturantImagetopAdapter1 resturantImagetopAdapter;


    public PersonalCookerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal, container, false);

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
        cookimagecir = view.findViewById(R.id.cookimage);
        cook_type_tv = view.findViewById(R.id.cook_type);
        contactwahts = view.findViewById(R.id.contactwahts);
        progressDialog = new ProgressDialog(getActivity());
        prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getActivity());
        setHasOptionsMenu(true);


        saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);
        cook_id_profile_publish = prefs.getInt(Constants.cook_id_profile_publish, -1);
        currentTime = Calendar.getInstance().getTime();


        resturenimagerecyclertop = view.findViewById(R.id.resturenimagerecyclertop);

        resturenimagerecyclertop = view.findViewById(R.id.resturenimagerecyclertop);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());

        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        resturenimagerecyclertop.setLayoutManager(manager2);

        mImageView2 = view.findViewById(R.id.imageView2);
        mAblePhoneLogin = view.findViewById(R.id.able_phone_login);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");


        getCookerprofile();

        getActivity().setTitle(getString(R.string.persona));
        resturenimagerecyclertop.setHasFixedSize(true);

        contactwahts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

                counter = prefs.getInt("counter", 0);


                Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        //livefun();


                        dialog = new Dialog(getActivity());
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
                                    Toast.makeText(getActivity(), "ادخل عنوان البث", Toast.LENGTH_SHORT).show();

                                } else {

                                    live_title = livename.getText().toString();
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















                        /* ... */
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        // Toast.makeText(getContext(), "token", Toast.LENGTH_SHORT).show();

                        /* ... */
                    }
                }).check();


            }
        });



        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live");

        mFirebaseDatabaseread = FirebaseDatabase.getInstance().getReference("Live").child(cook_id_profile_publish+"_"+type_id);



        // Inflate the layout for this fragment
        return view;
    }


    public void livefun() {

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


    public void getCookerprofile() {

        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://livecook.co/api/v1/cooker/" + cook_id_profile_publish + "/profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HZM", response);

                try {
                    JSONObject task_respnse = new JSONObject(response);
                    JSONObject taskarray = task_respnse.getJSONObject("data");

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
                    mCookDesc.setText(description);
                    cook_type_tv.setText(type_name);
                    mCountfollow.setText(followersNo + "");
                    mCookName.setText(name);
                    mAblePhoneLogin.setText(mobile);
                    mCityCook.setText( "المدينة  : "+ " "+cityName);
                    mCountryCook.setText("الدولة :"+" "+countryName);
                    mCityState.setText("الحي   : "+"  "+region);

                    Picasso.with(getContext()).load(avatarURL)
                            // .resize(100,100)
                            .error(R.drawable.ellipse)

                            .into(cookimagecir);


                    if (saveLogin) {
                        mAblePhoneLogin.setText(mobile);
                        mImageView2.setVisibility(View.GONE);


                    } else {
                        mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                        mImageView2.setVisibility(View.VISIBLE);

                        mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), LogincookActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });

                    }



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


                        Toast.makeText(getContext(), "تم انشاء البث بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), LiveKotlenActivity.class);
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





    public String formatDate(Date date) {
        SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
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
