package com.livecook.livecookapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Activity.ChangePasswardActivity;
import com.livecook.livecookapp.Activity.CookPageActivity;
import com.livecook.livecookapp.Activity.LoginActivity;
import com.livecook.livecookapp.Activity.LoginResturantActivity;
import com.livecook.livecookapp.Activity.ResturantPageActivity;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Fragment.CookPageFragment;
import com.livecook.livecookapp.Fragment.ResturantPageFragment;
import com.livecook.livecookapp.Model.AllResturanrModel;
import com.livecook.livecookapp.Model.AllcookModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.CookModel;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;




public class CookAdapter extends RecyclerView.Adapter<CookAdapter.MovieHolder> {
    // ArrayList<TasksDTO> data;
    ArrayList<AllcookModel.DataBean> data;
    Activity activity;
    int Cooker_id;
    String tokenfromlogin;
    boolean mFollowing=true;
    boolean mFavorite=true;
    Boolean isFavorite;

    Boolean isFollowed;
    String typnumer;
    private Boolean saveLogin;
    SharedPreferences prefs;

    View root;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;














    public CookAdapter(ArrayList<AllcookModel.DataBean> data, Activity activity) {
        this.data = data;
        this.activity = activity;
        setHasStableIds(true);

    }




    private void lastItemViewDetector(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastPos = layoutManager.findLastVisibleItemPosition();
                    if (!loading && lastPos == getItemCount() - 1 && onLoadMoreListener != null) {
                        if (onLoadMoreListener != null) {
                            int current_page = getItemCount() / Constants.LOAD_MORE;
                            onLoadMoreListener.onLoadMore(current_page);
                        }
                        loading = true;
                    }
                }
            });
        }
    }


    @Override
    public CookAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        prefs = activity.getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

            typnumer = prefs.getString(Constants.TYPE, "user");

        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");
        saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);


        if (typnumer.matches("cooker")||typnumer.matches("restaurant")) {
            root = LayoutInflater.from(activity).inflate(R.layout.cook_item_cooker_rest,null,false);


        }
        if (typnumer.matches("user")) {
            root = LayoutInflater.from(activity).inflate(R.layout.cook_item,null,false);


        }

        // View root = LayoutInflater.from(activity).inflate(R.layout.cook_item,null,false);

        // Toast.makeText(activity, "token  change"+tokenfromlogin, Toast.LENGTH_SHORT).show();

        return new MovieHolder(root);




           // View root = LayoutInflater.from(activity).inflate(R.layout.cook_item,null,false);

        // Toast.makeText(activity, "token  change"+tokenfromlogin, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onBindViewHolder(@NonNull final CookAdapter.MovieHolder movieHolder, final int i) {
        movieHolder.cook_name.setText(data.get(i).getName());
        movieHolder.cook_desc.setText(data.get(i).getType_name());
        movieHolder.countfollow.setText(data.get(i).getFollowers_no()+"");
        movieHolder.followingbut.setImageDrawable(activity.getResources().getDrawable(R.drawable.folllowsearch));
        Drawable d2 = activity.getResources().getDrawable(R.drawable.unfolllowsearch);
        Drawable d1 = activity.getResources().getDrawable(R.drawable.folllowsearch);
        final TransitionDrawable followDrawable = new TransitionDrawable(new Drawable[] { d1, d2 });
        final int transitionDuration = activity.getResources().getInteger(android.R.integer.config_shortAnimTime);

        if(data.get(i).getCity_name().isEmpty() ||data.get(i).getCity_name().matches("") ||data.get(i).getCity_name().matches("غير محدد")  ){
            movieHolder.cook_address.setText(data.get(i).getCountry_name());

        }
         else if(data.get(i).getRegion().isEmpty() ||data.get(i).getRegion().matches("") ||data.get(i).getRegion().matches("غير محدد")  ){
            movieHolder.cook_address.setText(data.get(i).getCountry_name());

        }

         else {
            movieHolder.cook_address.setText(data.get(i).getCountry_name()+" - " +""+data.get(i).getCity_name());

        }



        String url=data.get(i).getAvatar_url();
        Cooker_id=data.get(i).getId();
        isFollowed=data.get(i).isIs_followed();
        isFavorite=data.get(i).isIs_favorite();
        //  Toast.makeText(activity, ""+isFollowed, Toast.LENGTH_SHORT).show();

        prefs = activity.getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");

        // String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        if (prefs != null) {
            typnumer = prefs.getString(Constants.TYPE, "user");


            if (typnumer.matches("cooker")) {
                movieHolder.cookstar.setVisibility(View.GONE);
                movieHolder.followingbut.setVisibility(View.GONE);
                movieHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Intent intent=new Intent(activity, CookPageActivity.class);
                        intent.putExtra(Constants.cook_name,data.get(i).getName());
                        intent.putExtra(Constants.cook_address,data.get(i).getCity_name());
                        intent.putExtra(Constants.cook_desc,data.get(i).getRegion());
                        intent.putExtra(Constants.cookimage,data.get(i).getAvatar_url());
                        intent.putExtra(Constants.countfollow,data.get(i).getFollowers_no());
                        intent.putExtra(Constants.ressturant_id,data.get(i).getId());
                        intent.putExtra(Constants.cook_counuty_id,data.get(i).getCountry_id());
                        intent.putExtra(Constants.cook_id,data.get(i).getId());
                        intent.putExtra(Constants.cook_type_id,data.get(i).getType_id());
                        intent.putExtra(Constants.cook_desc,data.get(i).getDescription());
                        intent.putExtra(Constants.cook_county,data.get(i).getCountry_name());
                        intent.putExtra(Constants.cook_city,data.get(i).getCity_name());
                        intent.putExtra(Constants.cook_region,data.get(i).getRegion());
                        intent.putExtra(Constants.cookimage,data.get(i).getAvatar_url());
                        intent.putExtra(Constants.countfollow,data.get(i).getFollowers_no());

                        activity.startActivity(intent);

                        Bundle args = new Bundle();
                        args.putString(Constants.cook_name,data.get(i).getName());
                        args.putInt(Constants.cook_id,data.get(i).getId());
                        args.putInt(Constants.cook_type_id,data.get(i).getType_id());
                        args.putInt(Constants.cook_counuty_id,data.get(i).getCountry_id());

                        //  Toast.makeText(activity, ""+data.get(i).getId(), Toast.LENGTH_SHORT).show();

                        args.putString(Constants.cook_desc,data.get(i).getDescription());
                        args.putString(Constants.cook_county,data.get(i).getCountry_name());
                        args.putString(Constants.cook_city,data.get(i).getCity_name());
                        args.putString(Constants.cook_region,data.get(i).getRegion());
                        args.putString(Constants.cookimage,data.get(i).getAvatar_url());
                        args.putInt(Constants.countfollow,data.get(i).getFollowers_no());

                        // activity.startActivity(details);
               /* AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new CookPageFragment();
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, myFragment).addToBackStack(null).commit();*/


                    }
                });

            } else if (typnumer.matches("restaurant")) {

                movieHolder.cookstar.setVisibility(View.GONE);
                movieHolder.followingbut.setVisibility(View.GONE);
                movieHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Intent intent=new Intent(activity, CookPageActivity.class);
                        intent.putExtra(Constants.cook_name,data.get(i).getName());
                        intent.putExtra(Constants.cook_address,data.get(i).getCity_name());
                        intent.putExtra(Constants.cook_desc,data.get(i).getRegion());
                        intent.putExtra(Constants.cookimage,data.get(i).getAvatar_url());
                        intent.putExtra(Constants.countfollow,data.get(i).getFollowers_no());
                        intent.putExtra(Constants.ressturant_id,data.get(i).getId());
                        intent.putExtra(Constants.cook_counuty_id,data.get(i).getCountry_id());
                        intent.putExtra(Constants.cook_id,data.get(i).getId());
                        intent.putExtra(Constants.cook_type_id,data.get(i).getType_id());
                        intent.putExtra(Constants.cook_desc,data.get(i).getDescription());
                        intent.putExtra(Constants.cook_county,data.get(i).getCountry_name());
                        intent.putExtra(Constants.cook_city,data.get(i).getCity_name());
                        intent.putExtra(Constants.cook_region,data.get(i).getRegion());
                        intent.putExtra(Constants.cookimage,data.get(i).getAvatar_url());
                        intent.putExtra(Constants.countfollow,data.get(i).getFollowers_no());

                        activity.startActivity(intent);

                        Bundle args = new Bundle();
                        args.putString(Constants.cook_name,data.get(i).getName());
                        args.putInt(Constants.cook_id,data.get(i).getId());
                        args.putInt(Constants.cook_type_id,data.get(i).getType_id());
                        args.putInt(Constants.cook_counuty_id,data.get(i).getCountry_id());


                        args.putString(Constants.cook_desc,data.get(i).getDescription());
                        args.putString(Constants.cook_county,data.get(i).getCountry_name());
                        args.putString(Constants.cook_city,data.get(i).getCity_name());
                        args.putString(Constants.cook_region,data.get(i).getRegion());
                        args.putString(Constants.cookimage,data.get(i).getAvatar_url());
                        args.putInt(Constants.countfollow,data.get(i).getFollowers_no());

                        // activity.startActivity(details);
               /* AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new CookPageFragment();
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, myFragment).addToBackStack(null).commit();*/


                    }
                });


            } else if (typnumer.matches("user")) {

                movieHolder.cookstar.setVisibility(View.VISIBLE);
                movieHolder.followingbut.setVisibility(View.VISIBLE);




            }

        }




        if(isFavorite){
            movieHolder.cookstar.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));

        }
        else
        {
            movieHolder.cookstar.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_not_favorite_border));


        }










        if(isFollowed){
            movieHolder.followingbut.setImageDrawable(activity.getResources().getDrawable(R.drawable.unfolllowsearch));

        }
        else
        {
            movieHolder.followingbut.setImageDrawable(activity.getResources().getDrawable(R.drawable.folllowsearch));


        }

        movieHolder.followingbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (saveLogin) {



                    if (!isFollowed)
                    {
                        isFollowed = true;
                        followcooker(tokenfromlogin,data.get(i).getId());

                        movieHolder.followingbut.setImageDrawable(activity.getResources().getDrawable(R.drawable.unfolllowsearch));
                        data.get(i).setIs_followed(true);

                        //followDrawable.startTransition(transitionDuration);
                    }
                    else
                    {
                        mFollowing = false;
                        unfollowfollowcooker(tokenfromlogin,data.get(i).getId());

                        movieHolder.followingbut.setImageDrawable(activity.getResources().getDrawable(R.drawable.folllowsearch));

                        data.get(i).setIs_followed(false);

                    }

                }
                else {
                    Intent intent=new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);

                }




            }
        });


        movieHolder.cookstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (saveLogin) {

                    if (!isFavorite) {
                        isFavorite = true;
                        favoritecooker(tokenfromlogin, data.get(i).getId());

                        movieHolder.cookstar.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                        data.get(i).setIs_favorite(true);

                        //followDrawable.startTransition(transitionDuration);
                    } else {
                        mFavorite = false;
                        unfavoritecooker(tokenfromlogin, data.get(i).getId());

                        movieHolder.cookstar.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_not_favorite_border));

                        data.get(i).setIs_favorite(false);

                    }


                }

                else
                {
                    Intent intent=new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);

                }

            }





        });

        if(url.matches("") || !url.startsWith("http"))
        {////https://image.flaticon.com/icons/svg/1055/1055672.svg
            Picasso.with(activity)
                    .load("https://storage.googleapis.com/madebygoog/v1/phone/specs/marlin-black-en_US.png")
                    .error(R.drawable.ellipse)
                    // .resize(100,100)
                    .into((movieHolder.cookimage));
        }
        else {
            Picasso.with(activity).load(data.get(i).getAvatar_url())
                    // .resize(100,100)
                    .error(R.drawable.ellipse)

                    .into((movieHolder.cookimage));


        }

        movieHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent=new Intent(activity, CookPageActivity.class);
                intent.putExtra(Constants.cook_name,data.get(i).getName());
                intent.putExtra(Constants.cook_address,data.get(i).getCity_name());
                intent.putExtra(Constants.cook_desc,data.get(i).getRegion());
                intent.putExtra(Constants.cookimage,data.get(i).getAvatar_url());
                intent.putExtra(Constants.countfollow,data.get(i).getFollowers_no());
                intent.putExtra(Constants.ressturant_id,data.get(i).getId());
                intent.putExtra(Constants.cook_counuty_id,data.get(i).getCountry_id());
                intent.putExtra(Constants.cook_id,data.get(i).getId());
                intent.putExtra(Constants.cook_type_id,data.get(i).getType_id());
                intent.putExtra(Constants.cook_desc,data.get(i).getDescription());
                intent.putExtra(Constants.cook_county,data.get(i).getCountry_name());
                intent.putExtra(Constants.cook_city,data.get(i).getCity_name());
                intent.putExtra(Constants.cook_region,data.get(i).getRegion());
                intent.putExtra(Constants.cookimage,data.get(i).getAvatar_url());
                intent.putExtra(Constants.countfollow,data.get(i).getFollowers_no());

                activity.startActivity(intent);

                Bundle args = new Bundle();
                args.putString(Constants.cook_name,data.get(i).getName());
                args.putInt(Constants.cook_id,data.get(i).getId());
                args.putInt(Constants.cook_type_id,data.get(i).getType_id());
                args.putInt(Constants.cook_counuty_id,data.get(i).getCountry_id());

                args.putString(Constants.cook_desc,data.get(i).getDescription());
                args.putString(Constants.cook_county,data.get(i).getCountry_name());
                args.putString(Constants.cook_city,data.get(i).getCity_name());
                args.putString(Constants.cook_region,data.get(i).getRegion());
                args.putString(Constants.cookimage,data.get(i).getAvatar_url());
                args.putInt(Constants.countfollow,data.get(i).getFollowers_no());

                // activity.startActivity(details);
               /* AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new CookPageFragment();
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, myFragment).addToBackStack(null).commit();*/


            }
        });



    }

    @Override
    public long getItemId(int id) {
        return id;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addcooker(AllcookModel.DataBean dataBean) {
            data.add(dataBean);
            notifyDataSetChanged();




    }


    public class MovieHolder extends RecyclerView.ViewHolder {


        public CircleImageView cookimage;
        public TextView cook_name;
        public TextView cook_desc;
        public TextView countfollow;
        public CardView cardView;
        public ImageView followingbut;
        public ImageView cookstar;
        private  TextView  cook_address;




        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            cook_name = itemView.findViewById(R.id.cook_name);
            cookimage = itemView.findViewById(R.id.cookimage);
            cook_desc = itemView.findViewById(R.id.cook_desc);
            countfollow = itemView.findViewById(R.id.countfollow);
            cook_address = itemView.findViewById(R.id.cook_address);
            cardView = itemView.findViewById(R.id.cardview);
            cookstar = itemView.findViewById(R.id.cookstar);
            followingbut = itemView.findViewById(R.id.followingbut);




        }
    }


    public void followcooker (  final String access_token,final int Cooker_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/follow/cooker/"+Cooker_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                   // Toast.makeText(activity, "تمت المتابعة  " , Toast.LENGTH_SHORT).show();


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





    public void unfollowfollowcooker (  final String access_token,final int Cooker_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/favorite/cooker/"+Cooker_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    //Toast.makeText(activity, "تمت المتابعة  " , Toast.LENGTH_SHORT).show();


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


    public void favoritecooker (  final String access_token,final int Cooker_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/favorite/cooker/"+Cooker_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                   //
                    // Toast.makeText(activity, "تمت الاضافة الى المفضلة  " , Toast.LENGTH_SHORT).show();


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

    public void unfavoritecooker (  final String access_token,final int Cooker_id){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "https://livecook.co/api/v1/user/unfavorite/cooker/"+Cooker_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                  //  Toast.makeText(activity, "تمت الازالة من المفضلة  " , Toast.LENGTH_SHORT).show();


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


    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }



    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
    progressBar = v.findViewById(R.id.progressBar);
        }
    }


    public void addcooker(ArrayList<AllcookModel.DataBean> newdata){
        for(AllcookModel.DataBean dataBean:newdata){
            data.add(dataBean);



        }
        notifyDataSetChanged();


    }





}

