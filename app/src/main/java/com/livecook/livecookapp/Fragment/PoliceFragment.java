package com.livecook.livecookapp.Fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.livecook.livecookapp.Activity.CookPageActivity;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoliceFragment extends Fragment {
    TextView polictext;
    WebView webView;
    ProgressDialog progressDialog;
    View lyt_failed;
    Button btn_failed_retry;




    public PoliceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_police, container, false);

        //polictext=view.findViewById(R.id.polictext);
        // Inflate the layout for this fragment
        webView = view.findViewById(R.id.webView);
        progressDialog = new ProgressDialog(getActivity());

        webView.setBackgroundColor(0);

        lyt_failed = view.findViewById(R.id.lyt_failed_home);
        btn_failed_retry = lyt_failed.findViewById(R.id.failed_retry);
        btn_failed_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt_failed.setVisibility(View.GONE);
                hideDialog();

                displayData();
            }

            public void displayData() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new PoliceFragment()).commit();

                    }
                }, 1000);
            }

        });
        checkInternet();

        return view;
    }


    public void getAboutapp(final String link) {
        showDialog();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject jsonObject = response.getJSONObject("data");
                    String text = jsonObject.getString("text");
                    String title=jsonObject.getString("title");
                    if(text!=null){
                        WebSettings webSettings = webView.getSettings();
                        webSettings.setDefaultTextEncodingName("utf-8");
                        webSettings.setJavaScriptEnabled(true);

                        String dataa = "<html><head></head><body>" +
                                text  + "</body></html>";
                        Log.e("Wafaa", dataa);
                        webView.loadData(dataa, "text/html", null);


                    }

                    hideDialog();




                } catch (JSONException e) {
                    e.printStackTrace();
                    hideDialog();
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


                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    public void showDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.load));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    private void checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getAboutapp(Constants.privacy);


        } else {
            lyt_failed.setVisibility(View.VISIBLE);
            hideDialog();
        }

    }
}
