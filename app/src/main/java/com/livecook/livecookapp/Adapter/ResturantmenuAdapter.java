package com.livecook.livecookapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.MenuResturantModel;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ResturantmenuAdapter extends RecyclerView.Adapter<ResturantmenuAdapter.MovieHolder> {
    // ArrayList<TasksDTO> data;
    ArrayList<MenuResturantModel> data;
    Activity activity;
    IClickListener iClickListener;
    String typnumer;
    SharedPreferences prefs;









    public   interface IClickListener{
        void onItemClick(int position,MenuResturantModel menuResturantModel);
    }

    public ResturantmenuAdapter(ArrayList<MenuResturantModel> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ResturantmenuAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View root = LayoutInflater.from(activity).inflate(R.layout.menu_item,null,false);

        prefs = activity.getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);



        return new MovieHolder(root);
    }
    @Override
    public void onBindViewHolder(@NonNull ResturantmenuAdapter.MovieHolder movieHolder, final int i) {



        String url=data.get(i).getResturantmenu();
        movieHolder.deletemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                data.remove(data.get(i));
                notifyDataSetChanged();

            }
        });





        movieHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iClickListener !=null){
                    iClickListener.onItemClick(i,data.get(i));
                }
            }
        });



        if(url.matches("") || !url.startsWith("http"))
        {////https://image.flaticon.com/icons/svg/1055/1055672.svg
            Picasso.with(activity)
                    .load(url)
                    .error(R.drawable.test3)
                    .resize(197,140)
                    .into((movieHolder.resturantimage));
        }
        else {
            Picasso.with(activity).load(data.get(i).getResturantmenu())
                    .resize(197,140)
                    .error(R.drawable.test3)

                    .into((movieHolder.resturantimage));


        }



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {


        public ImageView resturantimage;
        public  ImageView deletemenu;





        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            resturantimage = itemView.findViewById(R.id.resturantimage);
            deletemenu = itemView.findViewById(R.id.deletemenu);




        }
    }



    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }
}


