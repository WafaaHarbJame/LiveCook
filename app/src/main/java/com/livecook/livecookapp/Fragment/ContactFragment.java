package com.livecook.livecookapp.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livecook.livecookapp.Activity.ClientRegisterActivity;
import com.livecook.livecookapp.Api.MyApplication;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    private EditText mEdname;
    private EditText memail;
    private EditText mSubjexttext;
    private EditText mEddesc;
    private Button mSendbut;
    ProgressDialog progressDialog;
    View lyt_failed;
    Button btn_failed_retry;
    private TextView mEdnametv;
    private TextView mEmailtv;
    private TextView mSubjexttexttv;
    private TextView mEddesctv;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        mEdname = view.findViewById(R.id.edname);
        memail = view.findViewById(R.id.email);
        mSubjexttext = view.findViewById(R.id.subjexttext);
        mEddesc = view.findViewById(R.id.eddesc);
        mSendbut = view.findViewById(R.id.sendbut);
        progressDialog = new ProgressDialog(getActivity());
        lyt_failed = view.findViewById(R.id.lyt_failed_home);
        btn_failed_retry = lyt_failed.findViewById(R.id.failed_retry);
        mEdnametv = view.findViewById(R.id.ednametv);
        mEmailtv = view.findViewById(R.id.emailtv);
        mSubjexttexttv = view.findViewById(R.id.subjexttexttv);
        mEddesctv = view.findViewById(R.id.eddesctv);

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
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new ContactFragment()).commit();


                    }
                }, 1000);
            }

        });


        mSendbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mEdname.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                }
                else if (mEdname.getText().toString().length()<3) {
                    Toast.makeText(getActivity(), getString(R.string.enter_name_lengh), Toast.LENGTH_SHORT).show();
                }


                else if (memail.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), getString(R.string.enter_email), Toast.LENGTH_SHORT).show();

                } else if (!isEmailValid(memail.getText().toString())) {

                    Toast.makeText(getActivity(), getString(R.string.enter__correct_email), Toast.LENGTH_SHORT).show();

                } else if (mSubjexttext.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), getString(R.string.enter_subject), Toast.LENGTH_SHORT).show();
                } else if (mEddesc.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), getString(R.string.enter_message), Toast.LENGTH_SHORT).show();
                } else {
                    ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {

                        contactus(Constants.contact, mEdname.getText().toString(), memail.getText().toString(), mSubjexttext.getText().toString(), mEddesc.getText().toString());
                    } else {
                        lyt_failed.setVisibility(View.VISIBLE);
                        mEdname.setVisibility(View.GONE);
                        memail.setVisibility(View.GONE);
                        mEddesc.setVisibility(View.GONE);
                        mSubjexttext.setVisibility(View.GONE);
                        mSendbut.setVisibility(View.GONE);
                        mEddesctv.setVisibility(View.GONE);
                        mEdnametv.setVisibility(View.GONE);
                        mEmailtv.setVisibility(View.GONE);
                        mSubjexttexttv.setVisibility(View.GONE);




                    }


                }

            }
        });


        return view;
    }


    public void contactus(final String link, final String name, final String email, final String subject, final String text) {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject register_response = new JSONObject(response);
                    boolean status = register_response.getBoolean("status");
                    String message = register_response.getString("message");
                    Toast.makeText(getActivity(), "تم ارسال البيانات بنجاح " , Toast.LENGTH_SHORT).show();
                    mEdname.setText("");
                    memail.setText("");
                    mSubjexttext.setText("");
                    mEddesc.setText("");
                    hideDialog();


                } catch (JSONException e) {
                    e.printStackTrace();
                    hideDialog();
                }

                hideDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("name", name);
                map.put("subject", subject);
                map.put("email", email);
                map.put("text", text);


                return map;

            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("name", name);
                headers.put("subject", subject);
                headers.put("email", email);
                headers.put("text", text);
                return headers;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }


    public void showDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.senddata));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            contactus(Constants.contact, mEdname.getText().toString(), memail.getText().toString(), mSubjexttext.getText().toString(), mEddesc.getText().toString());


        } else {
            lyt_failed.setVisibility(View.VISIBLE);
            hideDialog();
        }

    }



}
