package com.livecook.livecookapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.livecook.livecookapp.Activity.LiveUserctivity;
import com.livecook.livecookapp.Activity.LiveuserActivityy;
import com.livecook.livecookapp.Activity.LoginPageActivity;
import com.livecook.livecookapp.Model.Allresturantcook;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AllResturantCookAdapter extends RecyclerView.Adapter<AllResturantCookAdapter.MovieHolder> {
    // ArrayList<TasksDTO> data;
    ArrayList<Allresturantcook> data;
    Activity activity;
    private Boolean saveLogin=false;
    SharedPreferences prefs;








    public AllResturantCookAdapter(ArrayList<Allresturantcook> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public AllResturantCookAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View root = LayoutInflater.from(activity).inflate(R.layout.allfragment_item,null,false);
        prefs = activity.getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        if(prefs!=null) {
            saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);

        }


        return new MovieHolder(root);
    }
    @Override
    public void onBindViewHolder(@NonNull AllResturantCookAdapter.MovieHolder movieHolder, final int i) {


        movieHolder.countall.setText(data.get(i).getCountall());
        movieHolder.livetitle.setText(data.get(i).getLivetitle());

        movieHolder.allname.setText(data.get(i).getAllname()+"");
        String url=data.get(i).getAllimage();
        movieHolder.allimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent(activity, LiveuserActivityy.class);
                activity.startActivity(sendIntent);
            }
        });
        movieHolder.contactwhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(saveLogin) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    activity.startActivity(Intent.createChooser(sendIntent, ""));
                    activity.startActivity(sendIntent);
                }
                else{

                    Intent intent=new Intent(activity, LoginPageActivity.class);
                    activity.startActivity(intent);
                    activity.finish();

                }


            }
        });
        if(url.matches("") || !url.startsWith("http"))
        {////https://image.flaticon.com/icons/svg/1055/1055672.svg
            Picasso.with(activity)
                    .load(data.get(i).getAllimage())
                    .error(R.drawable.test)
                    // .resize(100,100)
                    .into((movieHolder.allimage));
        }
        else {
            Picasso.with(activity).load(data.get(i).getAllimage())
                    // .resize(100,100)
                    .error(R.drawable.test)

                    .into((movieHolder.allimage));


        }



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {


        public TextView countall;
        public ImageView allimage;
        public TextView livetitle;
        public TextView allname;
        public CardView cardView;
        public ImageView contactwhats;
        public ImageView follow;




        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            countall = itemView.findViewById(R.id.countall);
            allimage = itemView.findViewById(R.id.allimage);
            livetitle = itemView.findViewById(R.id.livetitle);
            allname = itemView.findViewById(R.id.allname);
            cardView = itemView.findViewById(R.id.cardview);
            contactwhats = itemView.findViewById(R.id.contactwhats);
            follow = itemView.findViewById(R.id.follow);




        }
    }
}


