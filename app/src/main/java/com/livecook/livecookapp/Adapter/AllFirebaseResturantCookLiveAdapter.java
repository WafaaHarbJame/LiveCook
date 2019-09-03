package com.livecook.livecookapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livecook.livecookapp.Activity.CookPageActivity;
import com.livecook.livecookapp.Activity.LiveuserActivityy;
import com.livecook.livecookapp.Activity.LoginActivity;
import com.livecook.livecookapp.Activity.LoginPageActivity;
import com.livecook.livecookapp.Activity.ResturantPageActivity;
import com.livecook.livecookapp.Model.AllFirebaseModel;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class AllFirebaseResturantCookLiveAdapter extends RecyclerView.Adapter<AllFirebaseResturantCookLiveAdapter.MovieHolder> {
    // ArrayList<TasksDTO> data;
    ArrayList<AllFirebaseModel> data;
    Activity activity;
    SharedPreferences prefs;
    String typnumer;
    int firebase_type;
    String firebase_type_str;
    private DatabaseReference mFirebaseDatabase;
    int user_id;
    int cooker_id;
    int resturant_id;
    Boolean isFollowed;
    private Boolean saveLogin=false;
    String firebase_type_1;
    String name;
    String name1;
    String cook_rest_id;
    int watch_counter=0;
    String full_mobile;












    public AllFirebaseResturantCookLiveAdapter(ArrayList<AllFirebaseModel> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public AllFirebaseResturantCookLiveAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View root = LayoutInflater.from(activity).inflate(R.layout.allfragment_item,null,false);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("users");
        prefs = activity.getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        if(prefs!=null) {
            typnumer = prefs.getString(Constants.TYPE, "user");
            saveLogin = prefs.getBoolean(Constants.ISLOGIN, false);
            user_id = prefs.getInt(Constants.user_id, -1);

        }












        return new MovieHolder(root);
    }
    @Override
    public void onBindViewHolder(@NonNull AllFirebaseResturantCookLiveAdapter.MovieHolder movieHolder, final int i) {

        if(typnumer.matches("user")){
            movieHolder.follow.setVisibility(View.VISIBLE);

        }
        else if(typnumer.matches("cooker")) {
            movieHolder.follow.setVisibility(View.GONE);


        }
        else if(typnumer.matches("restaurant")) {
            movieHolder.follow.setVisibility(View.GONE);


        }

        if(data.get(i).isStatus()) {
            movieHolder.countall.setText(data.get(i).getCount()+"");
            movieHolder.livetitle.setText(data.get(i).getTitle());
            movieHolder.allname.setText(data.get(i).getName()+"");
            String url=data.get(i).getWatchPath();
            firebase_type= data.get(i).getType();
            cooker_id=data.get(i).getId();
            resturant_id=data.get(i).getId();
            name=data.get(i).getName();
            name1=movieHolder.allname.getText().toString();
            full_mobile=data.get(i).getFull_mobile();
            movieHolder.type.setText(data.get(i).getType()+"");
        }
       /* else
        {
            movieHolder.allimage.setVisibility(View.GONE);
            movieHolder.allname.setVisibility(View.GONE);
            movieHolder.follow.setVisibility(View.GONE);
            movieHolder.cardView.setVisibility(View.GONE);
            movieHolder.contactwhats.setVisibility(View.GONE);
            movieHolder.countall.setVisibility(View.GONE);
            movieHolder.liii.setVisibility(View.GONE);
            movieHolder.type.setVisibility(View.GONE);
            movieHolder.livetitle.setVisibility(View.GONE);
            movieHolder.itemView.setVisibility(View.GONE);
            movieHolder.itemView.setVisibility(View.GONE);


        }*/






        firebase_type_1=movieHolder.type.getText().toString();


        if (firebase_type_1.matches("6")|| firebase_type_1.matches("7")) {

            mFirebaseDatabase.child(user_id + "").child("following").child("cookers").child(data.get(i).getId() + "").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        movieHolder.follow.setImageDrawable(activity.getResources().getDrawable(R.drawable.following));
                        // mFirebaseDatabase.child(user_id + "").child("following").child("cookers").child(cooker_id + "").child("name").setValue(name1);

                        isFollowed = true;

                    } else {
                      //  mFirebaseDatabase.child(user_id + "").child("following").child("cookers").child(cooker_id + "").child("name").setValue(name1);

                        movieHolder.follow.setImageDrawable(activity.getResources().getDrawable(R.drawable.followtab));
                        isFollowed = false;


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }
        else {



            mFirebaseDatabase.child(user_id + "").child("following").child("restaurants").child(data.get(i).getId() + "").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        movieHolder.follow.setImageDrawable(activity.getResources().getDrawable(R.drawable.following));
                        // mFirebaseDatabase.child(user_id + "").child("following").child("cookers").child(cooker_id + "").child("name").setValue(name1);

                        isFollowed = true;

                    } else {

                        movieHolder.follow.setImageDrawable(activity.getResources().getDrawable(R.drawable.followtab));
                        isFollowed = false;


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }





            movieHolder.follow.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View v) {
                        if (saveLogin) {
                            firebase_type_1=movieHolder.type.getText().toString();
                            //Toast.makeText(activity, ""+firebase_type_1, Toast.LENGTH_SHORT).show();
                            if (firebase_type_1.matches("6")|| firebase_type_1.matches("7")) {
                                firebase_type_str = "cooker";

                                if(isFollowed){
                                    movieHolder.follow.setImageDrawable(activity.getResources().getDrawable(R.drawable.followtab));

                                    mFirebaseDatabase.child(user_id + "").child("following").child("cookers").child(data.get(i).getId() + "")
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            isFollowed=false;

                                        }
                                    });


                                }

                                else{


                                    mFirebaseDatabase.child(user_id + "").child("following").child("cookers").child(data.get(i).getId() + "")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            mFirebaseDatabase.child(user_id + "").child("following").child("cookers").child(data.get(i).getId() + "").child("name").setValue(name1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    isFollowed=true;
                                                    movieHolder.follow.setImageDrawable(activity.getResources().getDrawable(R.drawable.following));

                                                }
                                            });



                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                }



                            }


                            else {

                                if(isFollowed){
                                    movieHolder.follow.setImageDrawable(activity.getResources().getDrawable(R.drawable.followtab));
                                    mFirebaseDatabase.child(user_id + "").child("following").child("restaurants").child(data.get(i).getId() + "")
                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            isFollowed=false;

                                        }
                                    });                                    isFollowed=false;

                                }

                                else{


                                    mFirebaseDatabase.child(user_id + "").child("following").child("restaurants").child(cooker_id + "")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    mFirebaseDatabase.child(user_id + "").child("following").child("restaurants").
                                                            child(data.get(i).getId() + "").child("name").setValue(name1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            isFollowed=true;
                                                            movieHolder.follow.setImageDrawable(activity.getResources().getDrawable(R.drawable.following));

                                                        }
                                                    });



                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });


                                }


                            }


                        }

                        else {

                            Intent intent=new Intent(activity, LoginActivity.class);
                            activity.startActivity(intent);

                        }
                    }

            });


movieHolder.allname.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(data.get(i).getType()==6||data.get(i).getType()==5){

            Intent intent=new Intent(activity, CookPageActivity.class);
            intent.putExtra(Constants.cook_id,data.get(i).getId());
            intent.putExtra(Constants.cook_type_id,data.get(i).getType());

            activity.startActivity(intent);


        }
        else
        {


            Intent intent=new Intent(activity, ResturantPageActivity.class);
            intent.putExtra(Constants.cook_name,data.get(i).getName());
              intent.putExtra(Constants.ressturant_id,data.get(i).getId());
            activity.startActivity(intent);


        }



    }
});




       /*
        if(firebase_type==6 ||firebase_type==7){
                firebase_type_str="cooker";
            }
            else

            {

                firebase_type_str="restaurant";


            }
        */


        /*MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

        mediaMetadataRetriever .setDataSource("https://www.radiantmediaplayer.com/media/bbb-360p.mp4", new HashMap<String, String>());
        Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(3000); //unit in microsecond
        movieHolder.allimage.setImageBitmap(bmFrame);/*/




        movieHolder.allimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_counter = watch_counter + 1;
                Intent intent = new Intent(activity, LiveuserActivityy.class);
                intent.putExtra(Constants.WatchPath, data.get(i).getWatchPath());
                intent.putExtra(Constants.Steam_path, data.get(i).getLivePath());
                intent.putExtra(Constants.firebase_name, data.get(i).getName());
                intent.putExtra(Constants.firebase_title, data.get(i).getTitle());
                intent.putExtra(Constants.firebase_type, firebase_type_str);
                intent.putExtra(Constants.cooker_resturant_firebaseid, data.get(i).getId());
                intent.putExtra(Constants.cooker_resturant_type, data.get(i).getType());
                intent.putExtra(Constants.RecordPath, data.get(i).getRecordPath());
                intent.putExtra(Constants.status, data.get(i).isStatus());


                activity.startActivity(intent);










            }
        });
        movieHolder.contactwhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (saveLogin) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.whatsapp");
                    intent.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", full_mobile)));
                    if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(activity, "Please install whatsApp", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    Intent intent=new Intent(activity, LoginPageActivity.class);
                    activity.startActivity(intent);
                    activity.finish();

                }


            }
        });
        /*Picasso.with(activity)
                    .load(url)
                    .error(R.drawable.flag_sa)
                    // .resize(100,100)
                    .into((movieHolder.allimage));*/




        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .centerCrop()
                .optionalCenterCrop()
                .placeholder(R.drawable.test);


      /*  URL newurl = null;
        try {
            newurl = new URL(data.get(i).getRecordPath());
            Toast.makeText(activity, ""+newurl, Toast.LENGTH_SHORT).show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap mIcon_val = null;
        try {
            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //movieHolder.allimage.setImageBitmap(mIcon_val);
        /*try {
            retriveVideoFrameFromVideo("http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8");
            movieHolder.allimage.setImageDrawable(movieHolder.allimage.getDrawable());
            movieHolder.allimage.setImageBitmap(retriveVideoFrameFromVideo(data.get(i).getLivePath()));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/

        // LoadImageFromWebOperations(data.get(i).getLivePath());
       // movieHolder.allimage.setImageDrawable( LoadImageFromWebOperations(data.get(i).getLivePath()));





      Glide.with(activity)
                .asBitmap()
                .apply(myOptions)
                .load(data.get(i).getLiveImage())
              .error(R.drawable.test)
                .into(movieHolder.allimage);





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
        public TextView type;
        ImageView liii;





        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            countall = itemView.findViewById(R.id.countall);
            allimage = itemView.findViewById(R.id.allimage);
            livetitle = itemView.findViewById(R.id.livetitle);
            allname = itemView.findViewById(R.id.allname);
            cardView = itemView.findViewById(R.id.cardview);
            contactwhats = itemView.findViewById(R.id.contactwhats);
            follow = itemView.findViewById(R.id.follow);
            type = itemView.findViewById(R.id.type);
            liii=itemView.findViewById(R.id.liii);





        }




    }


    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }



    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }


}


