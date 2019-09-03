package com.livecook.livecookapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.Model.Profile;
import com.livecook.livecookapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswardActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mEdpassward;
    private EditText mEdnewpassward;
    private EditText mNewpassward;
    private Button mChangepasswsard;
    SharedPreferences prefs;
    String typnumer;
    String tokenfromlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passward);


        mToolbar = findViewById(R.id.toolbar);
        mEdpassward = findViewById(R.id.edpassward);
        mEdnewpassward = findViewById(R.id.ednewpassward);
        mNewpassward = findViewById(R.id.newpassward);
        mChangepasswsard = findViewById(R.id.changepasswsard);
        prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        tokenfromlogin = prefs.getString(Constants.access_token1, "default value");
        //Toast.makeText(this, "token  change"+tokenfromlogin, Toast.LENGTH_SHORT).show();

        mChangepasswsard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdpassward.getText().toString().matches("")) {
                    Toast.makeText(ChangePasswardActivity.this, getString(R.string.oldpass), Toast.LENGTH_SHORT).show();
                } else if (mEdnewpassward.getText().toString().matches("")) {
                    Toast.makeText(ChangePasswardActivity.this, getString(R.string.newpass), Toast.LENGTH_SHORT).show();
                } else if (mNewpassward.getText().toString().matches("")) {
                    Toast.makeText(ChangePasswardActivity.this, getString(R.string.newpass), Toast.LENGTH_SHORT).show();
                } else if ((mEdnewpassward.getText().toString()).matches(mNewpassward.getText().toString())) {
                    if (prefs != null) {
                        typnumer = prefs.getString(Constants.TYPE, "user");
                       // Toast.makeText(ChangePasswardActivity.this, "type " + typnumer, Toast.LENGTH_SHORT).show();

                        if (typnumer.matches("cooker")) {
                            typnumer = prefs.getString(Constants.TYPE, "cooker");

                            //Toast.makeText(ChangePasswardActivity.this, "type " + typnumer, Toast.LENGTH_SHORT).show();
                            changepassward(Constants.update_cooker_passward, tokenfromlogin, mEdpassward.getText().toString(), mEdnewpassward.getText().toString(), mNewpassward.getText().toString());
                            Intent gotologin=new Intent(ChangePasswardActivity.this, LogincookActivity.class);
                            startActivity(gotologin);
                            finish();


                        } else if (typnumer.matches("restaurant")) {
                            typnumer = prefs.getString(Constants.TYPE, "restaurant");


                            changepassward(Constants.update_restaurant_passward, tokenfromlogin, mEdpassward.getText().toString(), mEdnewpassward.getText().toString(), mNewpassward.getText().toString());
                            Intent gotologin=new Intent(ChangePasswardActivity.this, LoginResturantActivity.class);
                            startActivity(gotologin);
                            finish();


                        } else if (typnumer.matches("user")) {
                            typnumer = prefs.getString(Constants.TYPE, "user");

                            changepassward(Constants.update_user_passward, tokenfromlogin, mEdpassward.getText().toString(), mEdnewpassward.getText().toString(), mNewpassward.getText().toString());

                            Intent gotologin=new Intent(ChangePasswardActivity.this, LoginActivity.class);
                            startActivity(gotologin);
                            finish();


                        }


                    }
                }
            }
        });



    }


    public void changepassward ( final String link, final String access_token,
                                 final String old_password, final String password, final String password_confirmation){
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    Toast.makeText(ChangePasswardActivity.this, "تم تعديل ;كلمة المرور بنجاح  " , Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("Authorization", "Bearer" + " " + access_token);
                map.put("old_password", old_password);
                map.put("password", password);
                map.put("password_confirmation", password_confirmation);


                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + " " + access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }




    public void changepassward1( final String link, final String access_token,
                                 final String old_password, final String password, final String password_confirmation){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH,
                link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean status=response.getBoolean("status");
                    String message=response.getString("message");
                    Toast.makeText(ChangePasswardActivity.this, "تم تعديل بيانات الحساب بنجاح ", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap();
                map.put("Authorization","Bearer"+ " " +access_token);
                map.put("old_password", old_password);
                map.put("password", password);
                map.put("password_confirmation", password_confirmation);

                return map;

            }

            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization","Bearer"+ " "+access_token);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }


}
