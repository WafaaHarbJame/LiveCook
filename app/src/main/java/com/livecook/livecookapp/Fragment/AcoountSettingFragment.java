package com.livecook.livecookapp.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.hbb20.CountryCodePicker;
import com.livecook.livecookapp.Activity.ChangePasswardActivity;
import com.livecook.livecookapp.Activity.ClientRegisterActivity;
import com.livecook.livecookapp.Activity.CookPageActivity;
import com.livecook.livecookapp.Activity.LiveuserActivityy;
import com.livecook.livecookapp.Adapter.CustomSpinnerAdapter;
import com.livecook.livecookapp.Adapter.DatumecountryAdapter;
import com.livecook.livecookapp.Adapter.ResturantmenuAdapter;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Model.Allcountries;
import com.livecook.livecookapp.Model.AppHelper;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.Datum;
import com.livecook.livecookapp.Model.MenuResturantModel;
import com.livecook.livecookapp.Model.Profile;
import com.livecook.livecookapp.Model.SessionManager;
import com.livecook.livecookapp.Model.VolleyMultipartRequest;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;
import com.vikktorn.picker.Country;
import com.vikktorn.picker.CountryPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.os.Handler;

import java.util.Objects;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static java.lang.String.valueOf;


/**
 * A simple {@link Fragment} subclass.
 */
public class AcoountSettingFragment extends Fragment implements IPickResult {
    CountryCodePicker ccpcountry;
    int cityspinnerPosition,spinnerPosition;
    int seletedcity_id;
    int country_id_1;


    // Pickers
    private CountryPicker countryPicker;
    String city_name;
    Datum datum;
    private EditText mEdname;
    private Spinner mCountryname;
    private Spinner mCityname;
    private EditText mRegion;
    private EditText mEddesc;
    private EditText mEdmobileNumber;
    private Spinner mCountrycode;
    private TextView mEdpassward;
    private Switch mSwitch1;
    String encodedImage;
    private Button mSave;
    int country_id=191;
    int city_id;
    String[] COOKER_TYPE = { "مستقل",  "ذبائح "};


    String country_codee;
    SharedPreferences prefs;
    String image_cook_path;
    TextView region;
    TextView tcdescpription;
    private ImageView insertliscense, showimage;
    File f;
    String imageName;
    int is_activaited_value = 0;
    TextView uploadimage;
    int type_id=6;
//image

    private static final String TAG = "tag";
    private ImageView mAvatar;
    private Uri mCropImageUri;
    private LinearLayout mContainer;
    private LinearLayout containermenu;

    String file_name = "";
    List<String> menuPhotos = new LinkedList<>();
    String liscen_avatar;
    private SessionManager mSessionManager;
    ImageView licen_avatar1;
    String municipalLicense = "";
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> cityadapter;

    List<String> countrylist = new ArrayList<String>();

    List<String> citylist = new ArrayList<String>();
    ArrayList<Datum> data = new ArrayList<>();
    ArrayList<Datum> city = new ArrayList<>();
    String typnumer;
    String tokenfromlogin;
    ArrayList<String> mStringList = new ArrayList<String>();
    ImageView inserlicen;
    ImageView licen_avatar;
    String menu_item;
    Activity activity;
    View view;
    ResturantmenuAdapter resturantmenuAdapter;
    ArrayList<MenuResturantModel> menuimage = new ArrayList<>();
    MenuResturantModel menuResturantModel;
    RecyclerView menurecycler;
    ProgressDialog progressDialog;
    String image_menu_item = "";
    List<String> list = new ArrayList<String>();
    TextView uploadcookerimage;
    ImageView uploadcookerimage_avatar;
    String uploadcookimageename = "";
    ImageView uploaResturantiprofile_avatar;
    String uploadprofileresturanrimageename = "";
    TextView uploaResturantiprofile;
    TextView imagecookernameforupdate;
    TextView imageresturantnameforupdate;
    TextView imagermenunameforupdate;
    TextView imagerlicensemeforupdate;
    boolean insertlicenseclicked = false;
    boolean insertresturantprofile = false;
    boolean insertcookerprofile = false;
    boolean insertmenuprofile = false;
    List<String> menuitem;
    Spinner spinner;
    private CustomSpinnerAdapter spinnerAdapter;
    private ArrayList<Allcountries.DataBean> datawithfalg=new ArrayList<Allcountries.DataBean>();
    ArrayList<Datum> countrycodelist = new ArrayList<Datum>();
    DatumecountryAdapter countrycodeadapter;
    TextView citytv;
    TextView typetext;
    Spinner spinnertype;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_acoount_setting, container, false);
        uploadcookerimage = view.findViewById(R.id.uploadcookerimage);
        uploadcookerimage_avatar = view.findViewById(R.id.uploadcookerimage_avatar);

        uploaResturantiprofile = view.findViewById(R.id.uploaResturantiprofile);
        uploaResturantiprofile_avatar = view.findViewById(R.id.uploaResturantiprofile_avatar);
        mEdname = view.findViewById(R.id.edname);
        mCountryname = view.findViewById(R.id.countryname);
        mCityname = view.findViewById(R.id.cityname);
        mRegion = view.findViewById(R.id.mRegion);
        mContainer = view.findViewById(R.id.container);
        mEddesc = view.findViewById(R.id.eddesc);
        mEdmobileNumber = view.findViewById(R.id.edmobile_number);
        mCountrycode = view.findViewById(R.id.countrycode);
        mEdpassward = view.findViewById(R.id.edpassward);
        mSwitch1 = view.findViewById(R.id.switch1);
        Button save = view.findViewById(R.id.two);
        region = view.findViewById(R.id.region);
        tcdescpription = view.findViewById(R.id.tcdescpription);
        mSessionManager = new SessionManager(getActivity());
        mContainer = view.findViewById(R.id.container);
        uploadimage = view.findViewById(R.id.uploadimage);
        mAvatar = view.findViewById(R.id.profile_avatar);
        inserlicen = view.findViewById(R.id.inserlicen);
        licen_avatar = view.findViewById(R.id.licen_avatar);
        menurecycler = view.findViewById(R.id.menurecylcer);
        imagecookernameforupdate = view.findViewById(R.id.imagecookernameforupdate);
        imagermenunameforupdate = view.findViewById(R.id.imagermenunameforupdate);
        imagerlicensemeforupdate = view.findViewById(R.id.imagerlicensemeforupdate);
        imageresturantnameforupdate = view.findViewById(R.id.imageresturantnameforupdate);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menurecycler.setLayoutManager(manager);
        citytv=view.findViewById(R.id.city);

        menurecycler.setHasFixedSize(true);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menurecycler.setLayoutManager(manager);
        menurecycler.setHasFixedSize(true);
        prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");
        progressDialog = new ProgressDialog(getActivity());

        spinner=view.findViewById(R.id.spinner);
        typetext=view.findViewById(R.id.typetext);
        spinnertype=view.findViewById(R.id.spinnertype);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,COOKER_TYPE);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnertype.setAdapter(aa);



        getCountries();
        getCountrieswithflag();

       spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(COOKER_TYPE[position].matches(getString(R.string.mostaql))){
                   type_id=6;

               }
               else {
                   type_id=7;


               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });


        if (prefs != null) {
            typnumer = prefs.getString(Constants.TYPE, "-1");

            if (typnumer.matches("cooker")) {
                licen_avatar.setVisibility(View.GONE);
                inserlicen.setVisibility(View.GONE);
                uploadimage.setVisibility(View.GONE);
                mAvatar.setVisibility(View.GONE);
                menurecycler.setVisibility(View.GONE);
                uploaResturantiprofile.setVisibility(View.GONE);
                uploaResturantiprofile_avatar.setVisibility(View.GONE);
                spinnertype.setVisibility(View.VISIBLE);
                typetext.setVisibility(View.VISIBLE);
                mSwitch1.setVisibility(View.GONE);
                mRegion.setVisibility(View.GONE);

                getprofiledata(Constants.get_cooker_profile, tokenfromlogin);



            } else if (typnumer.matches("restaurant")) {

                uploadcookerimage.setVisibility(View.GONE);
                uploaResturantiprofile_avatar.setVisibility(View.VISIBLE);

                mAvatar.setVisibility(View.GONE);
                spinnertype.setVisibility(View.GONE);
                typetext.setVisibility(View.GONE);

                uploadcookerimage_avatar.setVisibility(View.GONE);

                mSwitch1.setVisibility(View.GONE);
                mRegion.setVisibility(View.GONE);


                getprofilResturant(Constants.get_restaurant_profile, tokenfromlogin);

            } else if (typnumer.matches("user")) {

                mEddesc.setVisibility(View.GONE);
                tcdescpription.setVisibility(View.GONE);
                spinnertype.setVisibility(View.GONE);
                typetext.setVisibility(View.GONE);
                region.setVisibility(View.GONE);
                mRegion.setVisibility(View.GONE);
                uploadimage.setVisibility(View.GONE);
                mSwitch1.setVisibility(View.GONE);
                licen_avatar.setVisibility(View.GONE);
                inserlicen.setVisibility(View.GONE);
                mAvatar.setVisibility(View.GONE);
                uploadcookerimage.setVisibility(View.GONE);
                uploadcookerimage_avatar.setVisibility(View.GONE);
                uploaResturantiprofile.setVisibility(View.GONE);
                uploaResturantiprofile_avatar.setVisibility(View.GONE);
                mCityname.setVisibility(View.GONE);
                citytv.setVisibility(View.GONE);
                getuserprofile(Constants.get_user_profile, tokenfromlogin);


            }

        }

        mEdpassward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefs != null) {
                    typnumer = prefs.getString(Constants.TYPE, "user");

                    if (typnumer.matches("cooker")) {
                        Intent gotologin = new Intent(getActivity(), ChangePasswardActivity.class);

                        startActivity(gotologin);

                    } else if (typnumer.matches("restaurant")) {
                        Intent gotologin = new Intent(getActivity(), ChangePasswardActivity.class);
                        startActivity(gotologin);


                    } else if (typnumer.matches("user")) {
                        Intent gotologin = new Intent(getActivity(), ChangePasswardActivity.class);
                        startActivity(gotologin);


                    }

                }
            }
        });


        uploaResturantiprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertresturantprofile = true;

                PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {

                        try {
                            File file = new Compressor(Objects.requireNonNull(getActivity())).compressToFile(new File(r.getPath()));

                            Picasso.with(getActivity()).load(file).error(R.drawable.ellipse)
                                    // .resize(100,100)
                                    .into(uploaResturantiprofile_avatar);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Picasso.with(getActivity()).load(r.getUri()).error(R.drawable.ellipse)
                                    // .resize(100,100)
                                    .into(uploaResturantiprofile_avatar);
                        }


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadResturantimageprofile();
                            }
                        }, 1000);


                        if (r.getError() == null) {
                            uploaResturantiprofile_avatar.setImageBitmap(r.getBitmap());


                        } else {
                            //Handle possible errors
                            //TODO: do what you have to do with r.getError();
                            // Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        //TODO: do what you have to...
                    }
                }).setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        //TODO: do what you have to if user clicked cancel
                    }
                }).show(getActivity().getSupportFragmentManager());

            }
        });

        uploadcookerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertcookerprofile = true;

                PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {

                        try {
                            File COOKERIMAGEfile = new Compressor(getActivity()).compressToFile(new File(r.getPath()));

                            Picasso.with(getActivity()).load(COOKERIMAGEfile).error(R.drawable.ellipse)
                                    // .resize(100,100)
                                    .into(uploadcookerimage_avatar);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Picasso.with(getActivity()).load(r.getUri()).error(R.drawable.ellipse)
                                    // .resize(100,100)
                                    .into(uploadcookerimage_avatar);

                        }

                        //  uploadAvatar();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadcookimagee();
                            }
                        }, 1000);


                        if (r.getError() == null) {
                            //If you want the Uri.
                            //Mandatory to refresh image from Uri.
                            //getImageView().setImageURI(null);

                            //Setting the real returned image.
                            //getImageView().setImageURI(r.getUri());

                            //If you want the Bitmap.
                            licen_avatar.setImageBitmap(r.getBitmap());

                            //Image path
                            //r.getPath();
                        } else {
                            //Handle possible errors
                            //TODO: do what you have to do with r.getError();
                            Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        //TODO: do what you have to...
                    }
                }).setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        //TODO: do what you have to if user clicked cancel
                    }
                }).show(getActivity().getSupportFragmentManager());

            }
        });
        inserlicen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertlicenseclicked = true;
                PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {

                        try {
                            File inserlicenfile = new Compressor(getContext()).compressToFile(new File(r.getPath()));

                            Picasso.with(getActivity()).load(inserlicenfile).error(R.drawable.ellipse)
                                    // .resize(100,100)
                                    .into(licen_avatar);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Picasso.with(getActivity()).load(r.getUri()).error(R.drawable.ellipse)
                                    // .resize(100,100)
                                    .into(licen_avatar);

                        }

                        //  uploadAvatar();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadAvatar();
                            }
                        }, 1000);


                        if (r.getError() == null) {
                            //If you want the Uri.
                            //Mandatory to refresh image from Uri.
                            //getImageView().setImageURI(null);

                            //Setting the real returned image.
                            //getImageView().setImageURI(r.getUri());

                            //If you want the Bitmap.
                            licen_avatar.setImageBitmap(r.getBitmap());

                            //Image path
                            //r.getPath();
                        } else {
                            //Handle possible errors
                            //TODO: do what you have to do with r.getError();
                            Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        //TODO: do what you have to...
                    }
                }).setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        //TODO: do what you have to if user clicked cancel
                    }
                }).show(getActivity().getSupportFragmentManager());
            }
        });

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertmenuprofile = true;
                PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {

                        try {
                            File file = new Compressor(getContext()).compressToFile(new File(r.getPath()));

                            Picasso.with(getActivity()).load(file).error(R.drawable.ellipse)
                                    // .resize(100,100)
                                    .into(mAvatar);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Picasso.with(getActivity()).load(r.getUri()).error(R.drawable.ellipse)
                                    // .resize(100,100)
                                    .into(mAvatar);
                        }


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadmenu();
                            }
                        }, 1000);


                        if (r.getError() == null) {
                            mAvatar.setImageBitmap(r.getBitmap());

                            //Image path
                            //r.getPath();
                        } else {
                            //Handle possible errors
                            //TODO: do what you have to do with r.getError();
                            Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        //TODO: do what you have to...
                    }
                }).setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        //TODO: do what you have to if user clicked cancel
                    }
                }).show(getActivity().getSupportFragmentManager());

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (prefs != null) {

                    if (typnumer.matches("cooker")) {



                        if(mEdname.getText().toString().matches("")){
                            Toast.makeText(getActivity(), getResources().getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                        }
                        else if(mEdmobileNumber.getText().toString().matches("")){
                            Toast.makeText(getActivity(), getResources().getString(R.string.enter_mobile), Toast.LENGTH_SHORT).show();
                        }

                        else if(mEddesc.getText().toString().matches("")){
                            Toast.makeText(getActivity(), getResources().getString(R.string.enter_desc), Toast.LENGTH_SHORT).show();
                        }
                        else{






                        if (mSwitch1.isChecked()) {

                            is_activaited_value = 1;
                            getNotification(Constants.switch_cooker_notification, tokenfromlogin);
                        } else {
                            is_activaited_value = 0;
                        }


                        if (!insertcookerprofile) {

                            uploadcookimageename = imagecookernameforupdate.getText().toString();
                        }
                        updatecookerprofiledata(Constants.update_cooker_profile, tokenfromlogin, mEdname.getText().toString(),
                                mEdmobileNumber.getText().toString(),country_id_1, seletedcity_id, mRegion.getText().toString(),
                                is_activaited_value, uploadcookimageename, mEddesc.getText().toString(),type_id);}


                        // updatecookerprofiledata(Constants.update_cooker_profile, tokenfromlogin, mEdname.getText().toString(), mEdmobileNumber.getText().toString(), country_codee, 1,"kkk",1,"jjjj","jjj");

                    } else if (typnumer.matches("restaurant")) {


                        if(mEdname.getText().toString().matches("")){
                            Toast.makeText(getActivity(), getResources().getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                        }
                        else if(mEdmobileNumber.getText().toString().matches("")){
                            Toast.makeText(getActivity(), getResources().getString(R.string.enter_mobile), Toast.LENGTH_SHORT).show();
                        }

                        else if(mEddesc.getText().toString().matches("")){
                            Toast.makeText(getActivity(), getResources().getString(R.string.enter_desc), Toast.LENGTH_SHORT).show();
                        }

                        else {


                        if (mSwitch1.isChecked()) {

                            is_activaited_value = 1;
                            getNotification(Constants.switch_restaurant_notification, tokenfromlogin);

                        } else {
                            is_activaited_value = 0;
                        }



                        /*for (int i=0; i<menuPhotos.size(); i++) {
                            Toast.makeText(getActivity(), ""+menuPhotos.get(i), Toast.LENGTH_SHORT).show();
                            menu_item=menuPhotos.get(i).toString();
                            i++;

                        }*/


                        if (!insertlicenseclicked) {
                            liscen_avatar = imagerlicensemeforupdate.getText().toString();

                        }
                        if (!insertresturantprofile) {
                            uploadprofileresturanrimageename = imageresturantnameforupdate.getText().toString();

                        }

                        if (!insertmenuprofile) {
                            image_menu_item = imagermenunameforupdate.getText().toString();


                        } else {


                            resturantmenuAdapter.notifyDataSetChanged();
                            // list.clear();

                            for (MenuResturantModel card : menuimage) {


                                String image_menu_ite = card.getImage_name();
                                if (image_menu_ite != null) {

                                    list.add(image_menu_ite);
                                    HashSet hs = new HashSet();

                                    hs.addAll(list); // demoArrayList= name of arrayList from which u want to remove duplicates

                                    list.clear();
                                    list.addAll(hs);
                                    list.removeAll(Collections.singleton(null));


                                }

                            }


                            image_menu_item = TextUtils.join(",", list);
                        }


                        updateResturant(Constants.update_restaurant_profile, tokenfromlogin, mEdname.getText().toString(),
                                mEdmobileNumber.getText().toString(), country_id_1, seletedcity_id, mRegion.getText().toString(),
                                is_activaited_value, uploadprofileresturanrimageename, mEddesc.getText().toString(), list, liscen_avatar);}
                     /*   updateResturant(Constants.update_restaurant_profile,tokenfromlogin,"gggggggg","05932325417",1,1,
                                "22",1,"hghg","hghg","kk","lllgg");*/


                    } else if (typnumer.matches("user")) {
                        if(mEdname.getText().toString().matches("")){
                            Toast.makeText(getActivity(), getResources().getString(R.string.filldate), Toast.LENGTH_SHORT).show();
                        }
                        if(mEdmobileNumber.getText().toString().matches("")){
                            Toast.makeText(getActivity(), getResources().getString(R.string.filldate), Toast.LENGTH_SHORT).show();
                        }


                        else {

                            updateuserprofile(Constants.update_user_profile, tokenfromlogin, mEdname.getText().toString(), mEdmobileNumber.getText().toString(), country_id_1);

                        }
                    }

                }


            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // do something after selected item here
                datawithfalg.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                datawithfalg.get(0).getId();


            }
        });







        mCountryname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                country_id_1 = data.get(position).getId();
                country_codee = data.get(position).getCode();
                // Toast.makeText(getActivity(), ""+data.get(position).getId(), Toast.LENGTH_SHORT).show();

              getCities(country_id_1);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getCities(0);




            }
        });




     mCityname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              seletedcity_id = city.get(position).getId();
                // Toast.makeText(getActivity(), ""+data.get(position).getId(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                seletedcity_id = city.get(0).getId();



            }
        });
        return view;
    }


    public void UpdateResruranttt(final String link, final String access_token, final String name, final String mobile, final int country_id, final int city_id, final String region, final int is_notifications_activated, final String avatar, final String description, final String menu, final String municipal_license) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(getActivity(), "my res" + response, Toast.LENGTH_SHORT).show();


                try {

                    boolean status = response.getBoolean("status");
                    String message = response.getString("message");
                    Toast.makeText(getActivity(), "تم تعديل بيانات الحساب بنجاح " , Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //JSONArray taskarray=response.getJSONArray("task");
                //for (int i = 0; i < taskarray.length(); i++) {


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
                map.put("name", name);
                map.put("mobile", mobile);
                map.put("country_id", country_id + "");
                map.put("city_id", city_id + "");
                map.put("region", region);
                map.put("is_notifications_activated", is_notifications_activated + "");
                map.put("avatar", avatar);
                map.put("description", description);
                map.put("menu", menu);
                map.put("municipal_license", municipal_license);

                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    public void getprofilResturant(final String link, final String access_token) {
        showDialog();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray menuarray = jsonObject.getJSONArray("menu");
                    country_id=jsonObject.getInt("country_id");
                    // Toast.makeText(getActivity(), "" + jsonObject, Toast.LENGTH_SHORT).show();
                    String name = jsonObject.getString("name");
                    String mobile = jsonObject.getString("mobile");
                    String description = jsonObject.getString("description");
                    String avatar = jsonObject.getString("avatar");
                    String region= jsonObject.getString("region");
                    municipalLicense = jsonObject.getString("municipal_license");
                    String municipal_license_url = jsonObject.getString("municipal_license_url");
                    if(name.isEmpty()||name==null){
                        mEdname.setText("");
                    }
                    else{
                        mEdname.setText(name);}
                    mEdmobileNumber.setText(mobile);

                    if(description.isEmpty()||description==null){
                        mEddesc.setText("");
                    }
                    else{
                        mEddesc.setText(description);
                    }


                    if(region.isEmpty()||region.matches("")){
                        mRegion.setText("");
                    }
                    else{
                        mRegion.setText(region);
                    }

                    int cityId = jsonObject.getInt("city_id");
                    boolean is_notifications_activated = jsonObject.getBoolean("is_notifications_activated");
                    String countryName = jsonObject.getString("country_name");
                    city_name = jsonObject.getString("city_name");


                    if(countryName!=null &&arrayAdapter!=null&countrylist.size()>0){
                         spinnerPosition = arrayAdapter.getPosition(countryName);
                        mCountryname.setSelection(spinnerPosition);


                    }
                    if(country_id>0){
                        getCitieswithsslect(country_id,city_name);




                    }

                    imageresturantnameforupdate.setText(avatar);
                    imagerlicensemeforupdate.setText(municipalLicense);


                    Picasso.with(getContext()).load(municipal_license_url)
                            // .resize(100,100)
                            .error(R.drawable.ellipse)

                            .into(licen_avatar);


                    Picasso.with(getContext()).load("https://livecook.co/image/" + avatar)
                            // .resize(100,100)
                            .error(R.drawable.ellipse)

                            .into(uploaResturantiprofile_avatar);


                    //  Toast.makeText(getContext(), "length"+menuarray.length(), Toast.LENGTH_SHORT).show();

                    if (menuarray.getString(0).isEmpty()) {

                        menuimage.clear();


                    } else {


                        for (int i = 0; i < menuarray.length(); i++) {
                            menuResturantModel = new MenuResturantModel();
                            menuResturantModel.setResturantmenu("https://livecook.co/image/" + menuarray.get(i).toString());
                            imagermenunameforupdate.setText(menuarray.get(i).toString());
                            menuimage.add(menuResturantModel);
                        }


                        resturantmenuAdapter = new ResturantmenuAdapter(menuimage, getActivity());

                        menurecycler.setAdapter(resturantmenuAdapter);
                        resturantmenuAdapter.notifyDataSetChanged();


                    }


                    if (is_notifications_activated) {
                        mSwitch1.setChecked(true);
                    } else {
                        mSwitch1.setChecked(false);
                    }
                    hideDialog();


                } catch (JSONException e) {
                    e.printStackTrace();
                    hideDialog();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("Authorization", "Bearer" + "   " + access_token);


                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getContext(), data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), imageUri)) {
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
                    File file = new Compressor(getContext()).compressToFile(new File(uri.getPath()));

                    Picasso.with(getActivity()).load(file).error(R.drawable.ellipse)
                            // .resize(100,100)
                            .into(mAvatar);

                    // Picasso.get().load(file).into(mAvatar);
                    //Picasso
                    // Toast.makeText(getActivity(), "Compressed", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(getActivity(), "Failed Compress", Toast.LENGTH_SHORT).show();
                    //Picasso.get().load(uri).into(mAvatar);
                    Picasso.with(getActivity()).load(uri).error(R.drawable.ellipse)
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
                // Toast.makeText(getActivity(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            // Toast.makeText(getActivity(), "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAllowFlipping(false).setActivityTitle("Crop Image").setCropMenuCropButtonIcon(R.drawable.ic_check).setAllowRotation(true).setInitialCropWindowPaddingRatio(0).setFixAspectRatio(true).setAspectRatio(1, 1).setOutputCompressQuality(80).setOutputCompressFormat(Bitmap.CompressFormat.JPEG).setMultiTouchEnabled(true).start(getActivity());
    }

    /**
     * Upload image selected using volley
     */


    private void uploadResturantimageprofile() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("يتم  الان تحميل الصورة الرجاء الانتظار ...");
        progressDialog.show();

        final String id = "1";
        String url = "https://livecook.co/api/v1/upload/image";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
                //Toast.makeText(getActivity(), ""+resultResponse, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(resultResponse);
                    uploadprofileresturanrimageename = obj.getString("file_name");

                    //Toast.makeText(getActivity(), ""+obj.getString("file_name"), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), ""+obj.getBoolean("status"), Toast.LENGTH_SHORT).show();
                    boolean status = obj.getBoolean("status");

                    if (obj.getBoolean("status")) {
                        uploadprofileresturanrimageename = obj.getString("file_name");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON Error: " + e);
                    //showUploadSnackBar();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, "Volley Error: " + error);
                //showUploadSnackBar();
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
                params.put("image", new DataPart("img_" + id + ".jpg", AppHelper.getFileDataFromDrawable(getContext(), uploaResturantiprofile_avatar.getDrawable()), "image/jpg"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(multipartRequest);
    }

    private void uploadAvatar() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("يتم  الان تحميل الصورة الرجاء الانتظار ...");
        progressDialog.show();

        final String id = "1";
        String url = "https://livecook.co/api/v1/upload/image";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
                //Toast.makeText(getActivity(), ""+resultResponse, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(resultResponse);
                    liscen_avatar = obj.getString("file_name");

                    //menuPhotos.add(file_name);

                    //Toast.makeText(getActivity(), ""+obj.getString("file_name"), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), ""+obj.getString("status"), Toast.LENGTH_SHORT).show();
                    if (!obj.getBoolean("status")) {
                        String file_name = obj.getString("file_name");
                        // mSessionManager.setUrl(file_name);
                        // Picasso.get().load(avatar).placeholder(R.drawable.lissa).into(mAvatar);
                        // Toast.makeText(getActivity(), "Avatar Changed", Toast.LENGTH_SHORT).show();
                    } else {

                        liscen_avatar = obj.getString("file_name");


                        // menuPhotos.add(file_name);

                        Log.d(TAG, "Response: " + resultResponse);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON Error: " + e);
                    //showUploadSnackBar();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, "Volley Error: " + error);
                //showUploadSnackBar();
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
                params.put("image", new DataPart("img_" + id + ".jpg", AppHelper.getFileDataFromDrawable(getContext(), licen_avatar.getDrawable()), "image/jpg"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(multipartRequest);
    }

    private void uploadcookimagee() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("يتم  الان تحميل الصورة الرجاء الانتظار ...");
        progressDialog.show();

        final String id = "1";
        String url = "https://livecook.co/api/v1/upload/image";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
                //Toast.makeText(getActivity(), ""+resultResponse, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(resultResponse);
                    uploadcookimageename = obj.getString("file_name");

                    //menuPhotos.add(file_name);

                    //  Toast.makeText(getActivity(), ""+obj.getString("file_name"), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), ""+obj.getString("status"), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response: " + uploadcookimageename);

                    if (!obj.getBoolean("status")) {
                        String file_name = obj.getString("file_name");
                        // mSessionManager.setUrl(file_name);
                        // Picasso.get().load(avatar).placeholder(R.drawable.lissa).into(mAvatar);
                        // Toast.makeText(getActivity(), "Avatar Changed", Toast.LENGTH_SHORT).show();
                    } else {

                        uploadcookimageename = obj.getString("file_name");

                        Log.d(TAG, "Response: " + uploadcookimageename);


                        // menuPhotos.add(file_name);

                        Log.d(TAG, "Response: " + resultResponse);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON Error: " + e);
                    //showUploadSnackBar();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, "Volley Error: " + error);
                //showUploadSnackBar();
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
                params.put("image", new DataPart("img_" + id + ".jpg", AppHelper.getFileDataFromDrawable(getContext(), uploadcookerimage_avatar.getDrawable()), "image/jpg"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(multipartRequest);
    }

    private void uploadmenu() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("يتم  الان تحميل الصورة الرجاء الانتظار ...");
        progressDialog.show();

        final String id = "1";
        String url = "https://livecook.co/api/v1/upload/image";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
                // Toast.makeText(getActivity(), ""+resultResponse, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(resultResponse);
                    file_name = obj.getString("file_name");

                    //   Toast.makeText(getActivity(), ""+obj.getString("file_name"), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getActivity(), ""+obj.getBoolean("status"), Toast.LENGTH_SHORT).show();
                    boolean status = obj.getBoolean("status");

                    if (obj.getBoolean("status")) {
                        file_name = obj.getString("file_name");
                        //  menuPhotos.add(file_name);

                        if (file_name != null) {
                            menuResturantModel = new MenuResturantModel("https://livecook.co/image/" + file_name, file_name);
                            menuimage.add(menuResturantModel);
                            //list.add(file_name);

                            resturantmenuAdapter = new ResturantmenuAdapter(menuimage, getActivity());

                            menurecycler.setAdapter(resturantmenuAdapter);
                            resturantmenuAdapter.notifyDataSetChanged();


                        }










                       /* StringBuilder sb = new StringBuilder();

                        for (MenuResturantModel item : menuimage) {
                            sb.append(item.toString());
                            sb.append(",");
                        }

                         image_menu_item = sb.toString();*/



                        /*for(String item:menuPhotos){
                            menu_item=item;
                            Toast.makeText(getActivity(), menu_item+",", Toast.LENGTH_SHORT).show();


                        }*/


                    }
                    /*if (!obj.getBoolean("status")) {
                       file_name = obj.getString("file_name").toString();
                        Toast.makeText(getActivity(), "file"+file_name, Toast.LENGTH_SHORT).show();
                        // mSessionManager.setUrl(file_name);
                        // Picasso.get().load(avatar).placeholder(R.drawable.lissa).into(mAvatar);
                        Toast.makeText(getActivity(), "Avatar Changed", Toast.LENGTH_SHORT).show();
                        menuPhotos.add(file_name);
                        Toast.makeText(getActivity(), "mmm"+menuPhotos, Toast.LENGTH_SHORT).show();


                    } else {

                        menuPhotos.add(file_name);
                        Toast.makeText(getActivity(), "mmm"+menuPhotos, Toast.LENGTH_SHORT).show();


                        Log.d(TAG, "Response: " + resultResponse);
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON Error: " + e);
                    //showUploadSnackBar();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, "Volley Error: " + error);
                //showUploadSnackBar();
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
                params.put("image", new DataPart("img_" + id + ".jpg", AppHelper.getFileDataFromDrawable(getContext(), mAvatar.getDrawable()), "image/jpg"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(multipartRequest);
    }


    /**
     * SnackBar to retry in case of network issues
     */


    public void getCountries() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://livecook.co/api/v1/constant/countries", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String nationality = jsonObject.getString("nationality");
                        String sort_name = jsonObject.getString("sort_name");
                        String code = jsonObject.getString("code");
                        datum = new Datum();
                        datum.setId(id);
                        datum.setName(name);
                        datum.setCode(code);
                        datum.setSortName(sort_name);
                        datum.setNationality(nationality);
                        data.add(datum);
                        countrylist.add(name);


                    }
                    arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countrylist);
                    mCountryname.setAdapter(arrayAdapter);


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
        }) {
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


    public void getNotification(final String link, final String acces_token) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    String status = response.getString("status");
                    // Toast.makeText(getContext(), "notification"+status, Toast.LENGTH_SHORT).show();


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + acces_token);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + acces_token);
                return headers;
            }
        };

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    public void getCities(final int country_id) {
        citylist.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.cities_url + country_id, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        Datum cities = new Datum();
                        cities.setId(id);
                        cities.setName(name);
                        city.add(cities);
                        citylist.add(name);


                    }
                    cityadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, citylist);

                    mCityname.setAdapter(cityadapter);
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
        }) {
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


    public void getCitieswithsslect(final int country_id,String cityname) {
        citylist.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.cities_url + country_id, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        Datum cities = new Datum();
                        cities.setId(id);
                        cities.setName(name);
                        city.add(cities);
                        citylist.add(cities.getName());


                    }
                    cityadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, citylist);

                    mCityname.setAdapter(cityadapter);
                    cityadapter.notifyDataSetChanged();

                    if(city_name!=null &&cityadapter!=null){
                       // cityspinnerPosition = citylist.indexOf(city_name);

                        //int spinnerPosition = cityadapter.getPosition(city_name);
                         cityspinnerPosition = cityadapter.getPosition(city_name);
                        // Toast.makeText(getActivity(), "spinnerPosition"+spinnerPosition, Toast.LENGTH_SHORT).show();
                        mCityname.setSelection(cityspinnerPosition);





                    }












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
        }) {
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


    public void getprofiledata1(final String link, final String access_token) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    JSONObject jsonObject = register_response.getJSONObject("data");
                    //  Toast.makeText(getActivity(), "" + register_response, Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(getActivity(), "" +  jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
                    String name = jsonObject.getString("name");
                    int id = jsonObject.getInt("id");
                    int status_id = jsonObject.getInt("status_id");
                    country_id = jsonObject.getInt("country_id");
                    String region = jsonObject.getString("region");
                    String mobile = jsonObject.getString("mobile");
                    String description = jsonObject.getString("description");
                    String fcm_token = jsonObject.getString("fcm_token");
                    int rating = jsonObject.getInt("rating");
                    String avatar = jsonObject.getString("avatar");
                    int is_notifications_activated = jsonObject.getInt("is_notifications_activated");
                    Profile.DataBean dataBean = new Profile.DataBean();
                    //Toast.makeText(getActivity(), "" + name, Toast.LENGTH_SHORT).show();
                    dataBean.setId(id);
                    dataBean.setStatus_id(status_id);
                    dataBean.setCountry_id(country_id);
                    dataBean.setMobile(mobile);
                    mEdname.setText(name);
                    mEdmobileNumber.setText(mobile);

                    //mCountryname.setSelection(arrayAdapter.getPosition(""+country_id));
                    if(name.isEmpty()||name==null){
                        mEdname.setText("");
                    }
                    else{
                        mEdname.setText(name);}
                    mEdmobileNumber.setText(mobile);

                    if(description.isEmpty()||description==null){
                        mEddesc.setText("");
                    }
                    else{
                        mEddesc.setText(description);
                    }


                    if(region.isEmpty()||region.matches("")){
                        mRegion.setText("");
                    }
                    else{
                        mRegion.setText(region);
                    }


                    // Toast.makeText(getActivity(), "" + name, Toast.LENGTH_SHORT).show();
                    if (is_notifications_activated == 1) {
                        mSwitch1.setChecked(true);

                    } else {
                        mSwitch1.setChecked(false);
                    }


                } catch (JSONException e1) {
                    e1.printStackTrace();
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
                return headers;


            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }

    public void getuserprofile(final String link, final String access_token) {
        showDialog();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {

                    JSONObject jsonObject = response.getJSONObject("data");
                    //  Toast.makeText(getActivity(), "" + jsonObject, Toast.LENGTH_SHORT).show();
                    String name = jsonObject.getString("name");
                    String mobile = jsonObject.getString("mobile");
                    country_id=jsonObject.getInt("country_id");
                    JSONObject country=jsonObject.getJSONObject("country");

                    if(name.isEmpty()||name==null){
                        mEdname.setText("");
                    }
                    else{
                        mEdname.setText(name);}
                    mEdmobileNumber.setText(mobile);
                    int id = jsonObject.getInt("id");
                    int status_id = jsonObject.getInt("status_id");
                    String fcm_token = jsonObject.getString("fcm_token");
                    String code=country.getString("code");
                    String flag=country.getString("flag");
                    String country_name=country.getString("name");
                    int spinnerPosition = arrayAdapter.getPosition(country_name);
                    mCountryname.setSelection(spinnerPosition);
                    getCountrycode(spinnerPosition);




                    hideDialog();


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
                map.put("Authorization", "Bearer" + "   " + access_token);


                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void getprofiledata(final String link, final String access_token) {
        showDialog();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {

                    JSONObject jsonObject = response.getJSONObject("data");
                    String name = jsonObject.getString("name");
                    int country_id=jsonObject.getInt("country_id");
                    int city_id=jsonObject.getInt("city_id");
                    String mobile = jsonObject.getString("mobile");
                    String description = jsonObject.getString("description");
                    String avatar = jsonObject.getString("avatar");
                    String region=jsonObject.getString("region");
                    String country_name=jsonObject.getString("country_name");
                    city_name = jsonObject.getString("city_name");
                    type_id=jsonObject.getInt("type_id");



                    if(country_name!=null &&arrayAdapter!=null&countrylist.size()>0){
                        int spinnerPosition = arrayAdapter.getPosition(country_name);
                        mCountryname.setSelection(spinnerPosition);
                        getCountrycode(spinnerPosition);
                    }



                    if(country_id>0){
                        getCitieswithsslect(country_id,city_name);

                    }


                    if(name.isEmpty()|| name.matches("")){
                        mEdname.setText("");
                    }
                    else{
                        mEdname.setText(name);}
                    mEdmobileNumber.setText(mobile);

                    if(name.isEmpty()||name.matches("")){
                        mEdname.setText("");
                    }
                    else{
                        mEdname.setText(name);}
                    mEdmobileNumber.setText(mobile);

                    if(description.isEmpty()||description.matches("")){
                        mEddesc.setText("");
                    }
                    else{
                        mEddesc.setText(description);
                    }


                    if(region.isEmpty()||region.matches("")){
                        mRegion.setText("");
                    }
                    else{
                        mRegion.setText(region);
                    }


                    int id = jsonObject.getInt("id");
                    int cityId = jsonObject.getInt("city_id");
                    country_id = jsonObject.getInt("country_id");
                    String countryName = jsonObject.getString("country_name");
                    int followers_no = jsonObject.getInt("followers_no");
                    boolean isNotificationsActivated = jsonObject.getBoolean("is_notifications_activated");
                    imagecookernameforupdate.setText(avatar);
                    if(type_id==6){
                        spinnertype.setSelection(0);

                    }
                    else{
                        spinnertype.setSelection(1);



                    }


                  /*  Glide.with(getContext()).
                            load("https://livecook.co/image/" + avatar).into(uploadcookerimage_avatar);

*/


 Picasso.with(getActivity().getApplicationContext())
                            .load("https://livecook.co/image/" + avatar)
                            .error(R.drawable.ellipse)
                            // .resize(100,100)
                            .into((uploadcookerimage_avatar));



                    // Toast.makeText(getActivity(), "" + name, Toast.LENGTH_SHORT).show();
                    if (isNotificationsActivated) {
                        mSwitch1.setChecked(true);
                    } else {
                        mSwitch1.setChecked(false);
                    }
                    hideDialog();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("Authorization", "Bearer" + "   " + access_token);


                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + "  " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void updateuserprofile(final String link, final String access_token, final String name, final String mobile, final int country_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    Toast.makeText(getActivity(), "تم تعديل بيانات الحساب بنجاح " , Toast.LENGTH_SHORT).show();


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
                map.put("name", name);
                map.put("mobile", mobile);
                map.put("country_id", country_id + "");


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

    public void updatecookerprofiledata(final String link, final String access_token, final String name, final String mobile,
                                        final int country_id, final int city_id, final String region,
                                        final int is_notifications_activated, final String avatar, final String description,final int type_id ) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    Toast.makeText(getActivity(), "تم تعديل بيانات الحساب بنجاح " , Toast.LENGTH_SHORT).show();


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
                map.put("name", name);
                map.put("mobile", mobile);
                map.put("country_id", country_id + "");
                map.put("city_id", city_id + "");
                map.put("region", region + "");
                map.put("is_notifications_activated", is_notifications_activated + "");
                map.put("avatar", avatar);
                map.put("description", description);
                map.put("type_id",type_id+"");


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


    public void updateResturant(final String link, final String access_token, final String name,
                                final String mobile, final int country_id, final int city_id,
                                final String region, final int is_notifications_activated,
                                final String avatar, final String description, final List<String> menu, final String municipal_license) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    Toast.makeText(getActivity(), "تم تعديل بيانات الحساب بنجاح ", Toast.LENGTH_SHORT).show();


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
                map.put("name", name + "");
                map.put("mobile", mobile + "");
                map.put("country_id", country_id + "");
                map.put("city_id", city_id + "");
                map.put("region", region + "");
                map.put("is_notifications_activated", is_notifications_activated + "");
                map.put("avatar", avatar + "");
                map.put("description", description + "");
                map.put("menu", TextUtils.join(",", menu));
                map.put("municipal_license", municipal_license + "");


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


    public void getCountrycode(int spinnerPosition) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://livecook.co/api/v1/constant/countries", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String nationality = jsonObject.getString("nationality");
                        String sort_name = jsonObject.getString("sort_name");
                        String code = jsonObject.getString("code");
                        String flag = jsonObject.getString("flag");


                        Datum datum = new Datum();
                        datum.setId(id);
                        datum.setName(name);
                        datum.setCode(code);
                        datum.setSortName(sort_name);
                        datum.setNationality(nationality);
                        datum.setFlag(flag);
                        data.add(datum);
                        countrycodelist.add(datum);


                    }
                    countrycodeadapter = new DatumecountryAdapter(getActivity(), countrycodelist);
                    mCountrycode.setAdapter(countrycodeadapter);
                    if(countrycodelist.size()>0){
                    mCountrycode.setSelection(spinnerPosition);}

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
        }) {
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

    public static String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 1000, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }


    public String getBase64FromFile(String path) {
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        byte[] baat = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(path);
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            baat = baos.toByteArray();
            encodeString = Base64.encodeToString(baat, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encodeString;
    }


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }


    public void uploadimage(final String file) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.upload_image, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    String token = register_response.getString("accessToken");

                    // Toast.makeText(getActivity(), ""+status, Toast.LENGTH_SHORT).show();

                    if (status) {
                        Toast.makeText(getActivity(), "تم تحميل الصورة", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "لم يتم تحميل الصورة" , Toast.LENGTH_SHORT).show();

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
                map.put("file", file);


                return map;

            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }


    private void selectImage() {
        CropImage.startPickImageActivity(getActivity());
        //  uploadAvatar();
    }


    @Override
    public void onPickResult(PickResult pickResult) {

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


    public void getCountrieswithflag() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://livecook.co/api/v1/constant/countries", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String nationality = jsonObject.getString("nationality");
                        String sort_name = jsonObject.getString("sort_name");
                        String code = jsonObject.getString("code");
                        String flag=jsonObject.getString("flag");
                        Allcountries.DataBean datum = new Allcountries.DataBean();
                        datum.setId(id);
                        datum.setName(name);
                        datum.setCode(code);
                        datum.setSort_name(sort_name);
                        datum.setNationality(nationality);
                        datum.setFlag(flag);
                        datawithfalg.add(datum);


                    }
                    spinnerAdapter = new CustomSpinnerAdapter(getActivity(), datawithfalg);

                    spinner.setAdapter(spinnerAdapter);
                    spinnerAdapter.notifyDataSetChanged();

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
    public static void selectSpinnerItemByValue(Spinner spnr, long value) {
        SpinnerAdapter adapter = spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItemId(position) == value) {
                spnr.setSelection(position);
                return;
            }
        }
    }


    @Override
    public void onResume() {

        super.onResume();
    }


}