package com.livecook.livecookapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.livecook.livecookapp.R;

import java.util.List;

import butterknife.internal.Utils;

public class TestPerActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_per);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(TestPerActivity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO

                        ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if(report.areAllPermissionsGranted()) {
                            Toast.makeText(TestPerActivity.this, "yes"+report.areAllPermissionsGranted(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(TestPerActivity.this, "yes all", Toast.LENGTH_SHORT).show();

                        }

                        if(report.isAnyPermissionPermanentlyDenied()){
                            Toast.makeText(TestPerActivity.this, "no denied", Toast.LENGTH_SHORT).show();


                        }


                        /* ... */}



                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();



            }
        });



    }
}
