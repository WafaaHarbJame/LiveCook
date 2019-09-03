package com.livecook.livecookapp.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.MenuResturantModel;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ResturantmenuAdapterView extends RecyclerView.Adapter<ResturantmenuAdapterView.MovieHolder> {
    // ArrayList<TasksDTO> data;
    ArrayList<MenuResturantModel> data;
    Activity activity;
    IClickListener iClickListener;
    String typnumer;
    SharedPreferences prefs;









    public   interface IClickListener{
        void onItemClick(int position, MenuResturantModel menuResturantModel);
    }

    public ResturantmenuAdapterView(ArrayList<MenuResturantModel> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ResturantmenuAdapterView.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View root = LayoutInflater.from(activity).inflate(R.layout.menu_item,null,false);

        prefs = activity.getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);



        return new MovieHolder(root);
    }
    @Override
    public void onBindViewHolder(@NonNull ResturantmenuAdapterView.MovieHolder movieHolder, final int i) {



        String url=data.get(i).getResturantmenu();
        movieHolder.deletemenu.setVisibility(View.GONE);
        movieHolder.deletemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(data.get(i));
                notifyDataSetChanged();
            }
        });

        movieHolder.resturantimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                final AlertDialog dialog = builder.create();
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.dialog_big_image, null);
                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.show();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.bigImageView);
                        Bitmap icon = BitmapFactory.decodeResource(activity.getResources(),
                                R.drawable.ic_menu_camera);
                        float imageWidthInPX = (float)image.getWidth();

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                                Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                        image.setLayoutParams(layoutParams);


                    }
                });

*/



          Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.dialog_big_image);
                ImageView resturantimage = dialog.findViewById(R.id.bigImageView);
                ImageView imageexitgame=dialog.findViewById(R.id.imageexitgame);
                dialog.setCancelable(true);
                Picasso.with(activity)
                        .load(data.get(i).getResturantmenu())
                        .error(R.drawable.returant_image)
                        // .resize(100,100)
                        .into((resturantimage));

                imageexitgame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();









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
                    .error(R.drawable.returant_image)
                    // .resize(100,100)
                    .into((movieHolder.resturantimage));
        }
        else {
            Picasso.with(activity).load(data.get(i).getResturantmenu())
                    // .resize(100,100)
                    .error(R.drawable.returant_image)

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


