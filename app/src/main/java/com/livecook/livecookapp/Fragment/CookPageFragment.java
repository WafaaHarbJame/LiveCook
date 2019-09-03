package com.livecook.livecookapp.Fragment;


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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livecook.livecookapp.Activity.LogincookActivity;
import com.livecook.livecookapp.Adapter.ResturantImagetopAdapter;
import com.livecook.livecookapp.Adapter.ResturantImagetopAdapter1;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Model.AllFirebaseModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.ResturantImage;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CookPageFragment extends Fragment {
    private DatabaseReference mFirebaseDatabase;





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
    public  int cook_id,cook_type_id,cook_counuty_id;
    ResturantImagetopAdapter1 resturantImagetopAdapter;
    ArrayList<ResturantImage> data = new ArrayList<>();
    RecyclerView resturenimagerecyclertop;
    ProgressDialog progressDialog;
    private Boolean saveLogin;

    ArrayList<AllFirebaseModel> resturantImagestop = new ArrayList<>();




    public CookPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cook_page, container, false);
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
        cook_type_tv=view.findViewById(R.id.cook_type);
        contactwahts=view.findViewById(R.id.contactwahts);
        progressDialog = new ProgressDialog(getActivity());
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live");

        prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

        saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);

        contactwahts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.contactwhats)+full_mobile);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(sendIntent, ""));
                startActivity(sendIntent);
            }
        });



        resturenimagerecyclertop = view.findViewById(R.id.resturenimagerecyclertop);

        //editsearch = view.findViewById(R.id.simpleSearchView);
        resturenimagerecyclertop=view.findViewById(R.id.resturenimagerecyclertop);
        //RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(),1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());

        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        resturenimagerecyclertop.setLayoutManager(manager2);



        mImageView2 = view.findViewById(R.id.imageView2);
        mAblePhoneLogin = view.findViewById(R.id.able_phone_login);
        Bundle args = getArguments();

        cook_counuty_id=args.getInt("cook_counuty_id");
        cook_type_id=args.getInt("cook_type_id");
        cook_id=args.getInt(Constants.cook_id);
        getCookerprofile();

        getActivity().setTitle(getString(R.string.cookpage));
        resturenimagerecyclertop.setHasFixedSize(true);




        // Inflate the layout for this fragment
        return view;
    }



    public void getCookerprofile() {

        //showDialog();

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



                            mCookDesc.setText(description);
                            cook_type_tv.setText(type_name);
                            mCountfollow.setText(followersNo+"");
                            mCookName.setText(name);
                            mCityCook.setText( "المدينة : "+cityName);
                            mAblePhoneLogin.setText(mobile);
                            mCountryCook.setText("الدولة:"+countryName);
                            mCityState.setText("المنطقة: "+region);
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



                            if (saveLogin) {
                                mAblePhoneLogin.setText(mobile);

                            }
                            else {
                                mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                                mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(getActivity(), LogincookActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });

                            }











                        } catch (JSONException e1) {
                            e1.printStackTrace();

                        }


                        //  hideDialog();





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();


            }
        });

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }
    public void showDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));
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
                resturantImagestop.clear();
                for(DataSnapshot livenapshot:dataSnapshot.getChildren()) {
                    for(DataSnapshot live1snapshot:livenapshot.getChildren()) {

                        AllFirebaseModel detilas = live1snapshot.getValue(AllFirebaseModel.class);
                        resturantImagestop.add(detilas);

                    }


                }

                resturantImagetopAdapter = new ResturantImagetopAdapter1(resturantImagestop, getActivity());
                resturenimagerecyclertop.setAdapter(resturantImagetopAdapter);
                resturantImagetopAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
        super.onStart();
    }






}
