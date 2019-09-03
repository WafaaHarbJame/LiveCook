package com.livecook.livecookapp.Fragment;


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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livecook.livecookapp.Activity.LoginResturantActivity;
import com.livecook.livecookapp.Adapter.ResturantImageAdapter;
import com.livecook.livecookapp.Adapter.ResturantImagetopAdapter1;
import com.livecook.livecookapp.Adapter.ResturantmenuAdapterView;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResturantPageFragment extends Fragment {
    MenuResturantModel menuResturantModel;

    private Boolean saveLogin;
    SharedPreferences prefs;
    String full_mobile;
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
    private TextView mAblePhoneLogin;
    public CircleImageView cookimagecir;
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


    public ResturantPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resturant_page, container, false);
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
        mImageView2 = view.findViewById(R.id.imageView2);
        mAblePhoneLogin = view.findViewById(R.id.able_phone_login);
        prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

        saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Live");

        Bundle args = getArguments();
        cook_counuty_id = args.getInt("cook_counuty_id");
        cook_type_id = args.getInt("cook_type_id");
        ressturant_id = args.getInt("ressturant_id");
        getResturantprofile();
        getActivity().setTitle(getString(R.string.resturantpage));
        contactwahts = view.findViewById(R.id.contactwahts);
        menurecycler = view.findViewById(R.id.menurecylcer);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menurecycler.setLayoutManager(manager);
        menurecycler.setHasFixedSize(true);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menurecycler.setLayoutManager(manager);
        contactwahts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.contactwhats) + full_mobile);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(sendIntent, ""));
                startActivity(sendIntent);
            }
        });
        if (cookimage.matches("") || !cookimage.startsWith("http")) {////https://image.flaticon.com/icons/svg/1055/1055672.svg
            Picasso.with(getContext()).load("https://ibb.co/8ryyNh2").error(R.drawable.ellipse)
                    // .resize(100,100)
                    .into(cookimagecir);
        } else {
            Picasso.with(getContext()).load(cookimage)
                    // .resize(100,100)
                    .error(R.drawable.ellipse)

                    .into(cookimagecir);


        }


        resturenimagerecyclertop = view.findViewById(R.id.resturenimagerecyclertop);

        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());
        

        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        resturenimagerecyclertop.setLayoutManager(manager2);


        // Inflate the layout for this fragment
        return view;
    }

    public void getResturantprofile() {

        //showDialog();

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

                    mCookDesc.setText(description);
                    mCountfollow.setText(followersNo + "");
                    mCookName.setText(name);
                    mCityCook.setText("المدينة : " + cityName);
                    mAblePhoneLogin.setText(mobile);
                    mCountryCook.setText("الدولة:" + countryName);
                    mCityState.setText("المنطقة: " + region);
                    if (avatarURL.matches("") || !avatarURL.startsWith("http")) {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                        Picasso.with(getContext()).load(avatarURL).error(R.drawable.ellipse)
                                // .resize(100,100)
                                .into(cookimagecir);
                    } else {
                        Picasso.with(getContext()).load(avatarURL)
                                // .resize(100,100)
                                .error(R.drawable.ellipse)

                                .into(cookimagecir);


                    }


                    if (saveLogin) {
                        mAblePhoneLogin.setText(mobile);

                    } else {
                        mAblePhoneLogin.setText("لرؤية الهاتف يرجى تسجيل الدخول");
                        mAblePhoneLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), LoginResturantActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });

                    }


                    if (menuarray.getString(0).isEmpty()) {

                        menuimage.clear();
                        menurecycler.setVisibility(View.GONE);


                    } else {

                        for (int i = 0; i < menuarray.length(); i++) {
                            menuResturantModel = new MenuResturantModel();
                            menuResturantModel.setResturantmenu("https://livecook.co/image/" + menuarray.get(i).toString());
                            menuResturantModel.setImage_name(menuarray.get(i).toString());
                            menuimage.add(menuResturantModel);

                        }

                    }

                    resturantmenuAdapter = new ResturantmenuAdapterView(menuimage, getActivity());
                    menurecycler.setAdapter(resturantmenuAdapter);
                    resturantmenuAdapter.notifyDataSetChanged();


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


    @Override
    public void onStart() {
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resturantImagestop.clear();
                for (DataSnapshot livenapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot live1snapshot : livenapshot.getChildren()) {

                        AllFirebaseModel detilas = live1snapshot.getValue(AllFirebaseModel.class);
                        resturantImagestop.add(detilas);
                        //Toast.makeText(getContext(), "" + resturantImagestop, Toast.LENGTH_SHORT).show();



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
