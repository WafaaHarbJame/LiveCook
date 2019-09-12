package com.livecook.livecookapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import com.livecook.livecookapp.Model.Notification;
import com.livecook.livecookapp.Model.NotificationModel;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MovieHolder> {
    // ArrayList<TasksDTO> data;
    ArrayList<NotificationModel.DataBean> data;
    Activity activity;






    public NotificationAdapter(ArrayList<NotificationModel.DataBean> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public NotificationAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View root = LayoutInflater.from(activity).inflate(R.layout.notification_item,null,false);

        return new MovieHolder(root);
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MovieHolder movieHolder, final int i) {

        movieHolder.notificaton_title.setText(data.get(i).getTitle());

        Locale locale = new Locale("ar");

        String dateValue =data.get(i).getCreated_at();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",locale);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        long time = 0;
        try {
            //long time = sdf.parse("2016-01-24T16:00:00.000Z").getTime();
            time = sdf.parse(dateValue).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long now = System.currentTimeMillis();

        CharSequence ago =
                DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);

        movieHolder.time.setText(ago);
        movieHolder.notificatio_descrption.setText(data.get(i).getMessage());




        movieHolder.notificatio_descrption.setText(data.get(i).getMessage()+"");
        String url=data.get(i).getAvatar_url();
        movieHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Picasso.with(activity)
                .load(url)
                .error(R.drawable.ellipse)
                // .resize(100,100)
                .into((movieHolder.notficatio_image));



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {


        public TextView time;
        public CircleImageView notficatio_image;
        public TextView notificaton_title;
        public TextView notificatio_descrption;
        public CardView cardView;




        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            notficatio_image = itemView.findViewById(R.id.notficatio_image);
            notificaton_title = itemView.findViewById(R.id.notificaton_title);
            notificatio_descrption = itemView.findViewById(R.id.notificatio_descrption);
            cardView = itemView.findViewById(R.id.cardview);




        }
    }


    public static String getTimeAgo(long timestamp) {

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        Locale locale = new Locale( "ar" , "SA" ) ;  // Arabic language. Saudi Arabia cultural norms.

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a",locale);
        sdf.setTimeZone(tz);//set time zone.
        String localTime = sdf.format(new Date(timestamp * 1000));
        Date date = new Date();
        try {
            date = sdf.parse(localTime);//get local date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) {
            return null;
        }

        long time = date.getTime();

        Date curDate = currentDate();
        long now = curDate.getTime();
        if (time > now || time <= 0) {
            return null;
        }

        int timeDIM = getTimeDistanceInMinutes(time);

        String timeAgo = null;

        if (timeDIM == 0) {
            timeAgo = "less than a minute";
        } else if (timeDIM == 1) {
            return "1 minute";
        } else if (timeDIM >= 2 && timeDIM <= 44) {
            timeAgo = timeDIM + " minutes";
        } else if (timeDIM >= 45 && timeDIM <= 89) {
            timeAgo = "about an hour";
        } else if (timeDIM >= 90 && timeDIM <= 1439) {
            timeAgo = "about " + (Math.round(timeDIM / 60)) + " hours";
        } else if (timeDIM >= 1440 && timeDIM <= 2519) {
            timeAgo = "1 day";
        } else if (timeDIM >= 2520 && timeDIM <= 43199) {
            timeAgo = (Math.round(timeDIM / 1440)) + " days";
        } else if (timeDIM >= 43200 && timeDIM <= 86399) {
            timeAgo = "about a month";
        } else if (timeDIM >= 86400 && timeDIM <= 525599) {
            timeAgo = (Math.round(timeDIM / 43200)) + " months";
        } else if (timeDIM >= 525600 && timeDIM <= 655199) {
            timeAgo = "about a year";
        } else if (timeDIM >= 655200 && timeDIM <= 914399) {
            timeAgo = "over a year";
        } else if (timeDIM >= 914400 && timeDIM <= 1051199) {
            timeAgo = "almost 2 years";
        } else {
            timeAgo = "about " + (Math.round(timeDIM / 525600)) + " years";
        }

        return timeAgo + " ago";
    }

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

}

