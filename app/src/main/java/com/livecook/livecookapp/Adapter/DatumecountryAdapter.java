package com.livecook.livecookapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.livecook.livecookapp.Model.Allcountries;
import com.livecook.livecookapp.Model.Datum;
import com.livecook.livecookapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DatumecountryAdapter extends ArrayAdapter<Datum> {
    Activity activity;
    Context context;

    private ArrayList<Datum> data;
    public DatumecountryAdapter(Context context, ArrayList<Datum> data) {
        super(context, 0, data);
        this.data = data;
        this.context=context;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Datum country = data.get(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_country, parent, false);
            convertView.setTag(ViewHolder.createViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder)convertView.getTag();
        holder.textCountry.setText(country.getCode());
        if(context!=null){

        Picasso.with(context)
                .load(data.get(position).getFlag())
                .error(R.drawable.ellipse)
                // .resize(100,100)
                .into((holder.imgFlag));}

        return convertView;
    }
    @Override
    public int getCount( ) {
        return data.size();
    }

    private static class ViewHolder {
        public ImageView imgFlag;
        public TextView textCountry;

        public static ViewHolder createViewHolder(View view) {
            ViewHolder holder = new ViewHolder();
            holder.imgFlag = view.findViewById(R.id.imgFlag);
            holder.textCountry = view.findViewById(R.id.textCountry);
            return holder;
        }
    }
}
