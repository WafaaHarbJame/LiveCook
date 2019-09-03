package com.livecook.livecookapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.livecook.livecookapp.Activity.ClientRegisterActivity;
import com.livecook.livecookapp.Activity.LoginActivity;
import com.livecook.livecookapp.Activity.LoginResturantActivity;
import com.livecook.livecookapp.Activity.LogincookActivity;
import com.livecook.livecookapp.Activity.RegisterResturantActivity;
import com.livecook.livecookapp.Activity.RegistercookActivity;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.R;

public class RegisterFragment extends Fragment {
    private ImageView registercook;
    private ImageView registerresturant;
    private ImageView registerclient;
     SharedPreferences prefs ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_register, container, false);


        prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);

            registercook = view.findViewById(R.id.registercook);
            registerresturant = view.findViewById(R.id.registerresturant);
            registerclient = view.findViewById(R.id.registerclient);
            registercook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), RegistercookActivity.class);
                    intent.putExtra(Constants.cook_type,"cooker");
                    saveObjectToPreferences(Constants.cook_type,"cooker");
                    SharedPreferences.Editor editor = prefs.edit();
                    intent.putExtra(Constants.TYPE,"cooker");
                    editor.putString(Constants.TYPE,"cooker");
                    editor.apply();
                    getActivity().startActivity(intent);


                    getActivity().finish();


                }
            });
        registerresturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RegisterResturantActivity.class);
                intent.putExtra(Constants.restaurant_type,"restaurant,");
                saveObjectToPreferences(Constants.restaurant_type,"restaurant");
                SharedPreferences.Editor editor = prefs.edit();
                intent.putExtra(Constants.TYPE,"restaurant");

                editor.putString(Constants.TYPE,"restaurant");
                editor.apply();

                getActivity().startActivity(intent);
                getActivity().finish();

            }
        });
        registerclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ClientRegisterActivity.class);
                intent.putExtra(Constants.user_type,"user");
                intent.putExtra(Constants.TYPE,"user");
                saveObjectToPreferences(Constants.user_type,"user");
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Constants.TYPE,"user");
                editor.apply();

                getActivity().startActivity(intent);
                getActivity().finish();


            }
        });

        return view;
    }


    public void saveObjectToPreferences(String key, Object value) {
        final SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String jsonValue = new Gson().toJson(value);
        editor.putString(key, jsonValue);
        editor.apply();
    }


}