package com.livecook.livecookapp.Api;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.livecook.livecookapp.Model.Countries;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;


/**
 * Created by hp on 12/09/2017.
 */

public class VolleyRequests<T> extends Observable {
  Activity activity;
  public interface IReceiveData<T> {
    void onDataReceived(T posts);

  }

  IReceiveData iReceiveData;


  public VolleyRequests setIReceiveData(IReceiveData iReceiveData) {
    this.iReceiveData = iReceiveData;
    return this;
  }


  public VolleyRequests setiReceiveData(IReceiveData iReceiveData) {
    this.iReceiveData = iReceiveData;
    return this;
  }


  public void getContacts(){
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
            "https://livecook.co/api/v1/constant/countries", null, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Countries countries = gson.fromJson(response.toString(),Countries.class);
//                Toast.makeText(activity, ""+countries.getData().get(0).getName(), Toast.LENGTH_SHORT).show();

        setChanged();
        notifyObservers(countries);

        if(iReceiveData !=null){
          iReceiveData.onDataReceived(countries);
        }

                /*
                try {
                    JSONArray array = response.getJSONArray(AppConstants.CONTACTS_KEY);
                    for(int i=0;i<array.length();i++){

                      //JSONObject jsonObject =   array.getJSONObject(i);
                      //jsonObject.get

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

      }
    }){
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
      }

      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
      }
    };

    MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
  }







}