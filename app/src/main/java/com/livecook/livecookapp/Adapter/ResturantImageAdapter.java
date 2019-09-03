package com.livecook.livecookapp.Adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.livecook.livecookapp.Model.ResturantImage;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ResturantImageAdapter extends RecyclerView.Adapter<ResturantImageAdapter.MovieHolder> {
    // ArrayList<TasksDTO> data;
    ArrayList<ResturantImage> data;
    Activity activity;






    public ResturantImageAdapter(ArrayList<ResturantImage> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ResturantImageAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View root = LayoutInflater.from(activity).inflate(R.layout.resturant_item_image,null,false);

        return new MovieHolder(root);
    }
    @Override
    public void onBindViewHolder(@NonNull ResturantImageAdapter.MovieHolder movieHolder, final int i) {



        String url=data.get(i).getResturantimage();


        if(url.matches("") || !url.startsWith("http"))
        {////https://image.flaticon.com/icons/svg/1055/1055672.svg
            Picasso.with(activity)
                    .load("https://storage.googleapis.com/madebygoog/v1/phone/specs/marlin-black-en_US.png")
                    .error(R.drawable.test3)
                    // .resize(100,100)
                    .resize(197,140)

                    .into((movieHolder.resturantimage));
        }
        else {
            Picasso.with(activity).load(data.get(i).getResturantimage())
                    // .resize(100,100)
                    .resize(150,140)
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





        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            resturantimage = itemView.findViewById(R.id.resturantimage);




        }
    }
}


