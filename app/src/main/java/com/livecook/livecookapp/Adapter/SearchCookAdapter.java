package com.livecook.livecookapp.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Activity.CookPageActivity;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Fragment.CookPageFragment;
import com.livecook.livecookapp.Model.AllcookModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.CookModel;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class SearchCookAdapter extends RecyclerView.Adapter<SearchCookAdapter.MovieHolder> implements Filterable {
    // ArrayList<TasksDTO> data;
    ArrayList<AllcookModel.DataBean> data;
    ArrayList<AllcookModel.DataBean> dataFiltered;
    private ContactsAdapterListener listener;


    Activity activity;
    int Cooker_id;
    String tokenfromlogin;
    SharedPreferences prefs;
    boolean mFollowing=true;
    boolean mFavorite=true;
    Boolean isFavorite;

    Boolean isFollowed;
    String typnumer;
    View root;










    public SearchCookAdapter(ArrayList<AllcookModel.DataBean> data, Activity activity,ContactsAdapterListener listener) {
        this.data = data;
        this.activity = activity;
        this.data = data;
        this.dataFiltered = data;
        this.listener = listener;
        ImageView image;

    }

    @Override
    public SearchCookAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        prefs = activity.getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

        typnumer = prefs.getString(Constants.TYPE, "user");

        if (typnumer.matches("cooker")||typnumer.matches("restaurant")) {
            root = LayoutInflater.from(activity).inflate(R.layout.cook_item_cooker_rest,null,false);


        }
        if (typnumer.matches("user")) {
            root = LayoutInflater.from(activity).inflate(R.layout.cook_item,null,false);


        }



            //View root = LayoutInflater.from(activity).inflate(R.layout.cook_item,null,false);
        prefs = activity.getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");


       // Toast.makeText(activity, "token  change"+tokenfromlogin, Toast.LENGTH_SHORT).show();

        return new MovieHolder(root);
    }
    @Override
    public void onBindViewHolder(@NonNull final SearchCookAdapter.MovieHolder movieHolder, final int i) {

        ImageView image=new ImageView(activity);

        movieHolder.cook_name.setText(dataFiltered.get(i).getName());
        movieHolder.cook_desc.setText(dataFiltered.get(i).getType_name());
        movieHolder.countfollow.setText(dataFiltered.get(i).getFollowers_no()+"");
        movieHolder.followingbut.setImageDrawable(activity.getResources().getDrawable(R.drawable.folllowsearch));
        Drawable d2 = activity.getResources().getDrawable(R.drawable.unfolllowsearch);
        Drawable d1 = activity.getResources().getDrawable(R.drawable.folllowsearch);
        final TransitionDrawable followDrawable = new TransitionDrawable(new Drawable[] { d1, d2 });
        final int transitionDuration = activity.getResources().getInteger(android.R.integer.config_shortAnimTime);
        if(dataFiltered.get(i).getRegion().isEmpty() ||dataFiltered.get(i).getRegion().matches("") ){
            movieHolder.cook_address.setText(data.get(i).getCountry_name()+" - " +""+dataFiltered.get(i).getCity_name());

        }
        else{
            movieHolder.cook_address.setText(dataFiltered.get(i).getCountry_name()+" - " +""+dataFiltered.get(i).getCity_name()+" - "+""+dataFiltered.get(i).getRegion());

        }


        String url=dataFiltered.get(i).getAvatar_url();
        Cooker_id=dataFiltered.get(i).getId();
      isFollowed=dataFiltered.get(i).isIs_followed();
      isFavorite=dataFiltered.get(i).isIs_favorite();
      //  Toast.makeText(activity, ""+isFollowed, Toast.LENGTH_SHORT).show();

        prefs = activity.getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");

        // String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        if (prefs != null) {
            typnumer = prefs.getString(Constants.TYPE, "user");


            if (typnumer.matches("cooker")) {
                movieHolder.cookstar.setVisibility(View.GONE);
                movieHolder.followingbut.setVisibility(View.GONE);

            } else if (typnumer.matches("restaurant")) {

                movieHolder.cookstar.setVisibility(View.GONE);
                movieHolder.followingbut.setVisibility(View.GONE);


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

                if (!isFollowed)
                {
                    isFollowed = true;
                    followcooker(tokenfromlogin,dataFiltered.get(i).getId());

                    movieHolder.followingbut.setImageDrawable(activity.getResources().getDrawable(R.drawable.unfolllowsearch));
                    dataFiltered.get(i).setIs_followed(true);

                    //followDrawable.startTransition(transitionDuration);
                }
                else
                {
                    mFollowing = false;
                    unfollowfollowcooker(tokenfromlogin,dataFiltered.get(i).getId());

                    movieHolder.followingbut.setImageDrawable(activity.getResources().getDrawable(R.drawable.folllowsearch));

                    dataFiltered.get(i).setIs_followed(false);

                }


            }
        });


        movieHolder.cookstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isFavorite)
                {
                    isFavorite = true;
                    favoritecooker(tokenfromlogin,dataFiltered.get(i).getId());

                    movieHolder.cookstar.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                    dataFiltered.get(i).setIs_favorite(true);

                    //followDrawable.startTransition(transitionDuration);
                }
                else
                {
                    mFavorite = false;
                    unfavoritecooker(tokenfromlogin,dataFiltered.get(i).getId());

                    movieHolder.cookstar.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_not_favorite_border));

                    dataFiltered.get(i).setIs_favorite(false);

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
            Picasso.with(activity).load(dataFiltered.get(i).getAvatar_url())
                    // .resize(100,100)
                    .error(R.drawable.ellipse)

                    .into((movieHolder.cookimage));


        }

        /*movieHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(activity, CookPageActivity.class);
                intent.putExtra(Constants.cook_name,dataFiltered.get(i).getName());
                intent.putExtra(Constants.cook_address,dataFiltered.get(i).getCity_name());
                intent.putExtra(Constants.cook_desc,dataFiltered.get(i).getRegion());
                intent.putExtra(Constants.cookimage,dataFiltered.get(i).getAvatar_url());
                intent.putExtra(Constants.countfollow,dataFiltered.get(i).getFollowers_no());
                intent.putExtra(Constants.ressturant_id,dataFiltered.get(i).getId());
                intent.putExtra(Constants.cook_counuty_id,dataFiltered.get(i).getCountry_id());
                intent.putExtra(Constants.cook_id,dataFiltered.get(i).getId());
                intent.putExtra(Constants.cook_type_id,dataFiltered.get(i).getType_id());
                intent.putExtra(Constants.cook_desc,dataFiltered.get(i).getDescription());
                intent.putExtra(Constants.cook_county,dataFiltered.get(i).getCountry_name());
                intent.putExtra(Constants.cook_city,dataFiltered.get(i).getCity_name());
                intent.putExtra(Constants.cook_region,dataFiltered.get(i).getRegion());
                intent.putExtra(Constants.cookimage,dataFiltered.get(i).getAvatar_url());
                intent.putExtra(Constants.countfollow,dataFiltered.get(i).getFollowers_no());

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


          //  }
        //});*/



    }


    @Override
    public int getItemCount() {
        return dataFiltered.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataFiltered = data;
                } else {
                    ArrayList<AllcookModel.DataBean> filteredList  = new ArrayList<>();
                    for (AllcookModel.DataBean row : data) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCountry_name().contains(charSequence) ) {
                            filteredList.add(row);
                        }



                    }

                    dataFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataFiltered = (ArrayList<AllcookModel.DataBean>) filterResults.values;
                notifyDataSetChanged();
                if(dataFiltered.isEmpty()){

                    Toast.makeText(activity, "لا يوجد نتائج ", Toast.LENGTH_SHORT).show();

                }
            }
        };
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


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(dataFiltered.get(getAdapterPosition()));

                    Intent intent=new Intent(activity, CookPageActivity.class);
                    intent.putExtra(Constants.cook_name,dataFiltered.get(getAdapterPosition()).getName());
                    intent.putExtra(Constants.cook_address,dataFiltered.get(getAdapterPosition()).getCity_name());
                    intent.putExtra(Constants.cook_desc,dataFiltered.get(getAdapterPosition()).getType_name());
                    intent.putExtra(Constants.cookimage,dataFiltered.get(getAdapterPosition()).getAvatar_url());
                    intent.putExtra(Constants.countfollow,dataFiltered.get(getAdapterPosition()).getFollowers_no());
                    intent.putExtra(Constants.ressturant_id,dataFiltered.get(getAdapterPosition()).getId());
                    intent.putExtra(Constants.cook_counuty_id,dataFiltered.get(getAdapterPosition()).getCountry_id());
                    intent.putExtra(Constants.cook_id,dataFiltered.get(getAdapterPosition()).getId());
                    intent.putExtra(Constants.cook_type_id,dataFiltered.get(getAdapterPosition()).getType_id());
                    intent.putExtra(Constants.cook_desc,dataFiltered.get(getAdapterPosition()).getType_name());
                    intent.putExtra(Constants.cook_county,dataFiltered.get(getAdapterPosition()).getCountry_name());
                    intent.putExtra(Constants.cook_city,dataFiltered.get(getAdapterPosition()).getCity_name());
                    intent.putExtra(Constants.cook_region,dataFiltered.get(getAdapterPosition()).getRegion());
                    intent.putExtra(Constants.cookimage,dataFiltered.get(getAdapterPosition()).getAvatar_url());
                    intent.putExtra(Constants.countfollow,dataFiltered.get(getAdapterPosition()).getFollowers_no());

                    activity.startActivity(intent);



                }
            });
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
                    Toast.makeText(activity, "تمت المتابعة  " + message+""+status, Toast.LENGTH_SHORT).show();


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
                    Toast.makeText(activity, "تمت المتابعة  " + message+""+status, Toast.LENGTH_SHORT).show();


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
                    Toast.makeText(activity, "تمت الاضافة الى المفضلة  " + message+""+status, Toast.LENGTH_SHORT).show();


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
                    Toast.makeText(activity, "تمت ازالة من المفضلة  " + message+""+status, Toast.LENGTH_SHORT).show();


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


    public interface ContactsAdapterListener {
        void onContactSelected(AllcookModel.DataBean contact);
    }


    public void addcooker(AllcookModel.DataBean dataBean) {
        data.add(dataBean);
        notifyDataSetChanged();




    }
}

