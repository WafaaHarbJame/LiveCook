package com.livecook.livecookapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.livecook.livecookapp.Model.MenuResturantModel;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ResturantmenuAdapterforprofile extends RecyclerView.Adapter<ResturantmenuAdapterforprofile.MovieHolder> {
    // ArrayList<TasksDTO> data;
    ArrayList<MenuResturantModel> data;
    Activity activity;






    public ResturantmenuAdapterforprofile(ArrayList<MenuResturantModel> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ResturantmenuAdapterforprofile.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View root = LayoutInflater.from(activity).inflate(R.layout.menu_item,null,false);

        return new MovieHolder(root);
    }
    @Override
    public void onBindViewHolder(@NonNull ResturantmenuAdapterforprofile.MovieHolder movieHolder, final int i) {



        String url=data.get(i).getResturantmenu();
        movieHolder.deletemenu.setVisibility(View.GONE);



        if(url.matches("") || !url.startsWith("http"))
        {////https://image.flaticon.com/icons/svg/1055/1055672.svg
            Picasso.with(activity)
                    .load(url)
                    .error(R.drawable.test4)
                    .resize(150,150)
                    // .resize(100,100)
                    .into((movieHolder.resturantimage));
        }
        else {
            Picasso.with(activity).load(data.get(i).getResturantmenu())
                    .resize(150,150)
                    .error(R.drawable.test4)

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
}


