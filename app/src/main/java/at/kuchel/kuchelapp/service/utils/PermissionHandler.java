package at.kuchel.kuchelapp.service.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import at.kuchel.kuchelapp.Constants;

public class PermissionHandler extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1800;
    private static final int MY_PERMISSIONS_REQUEST_CALENDAR = 1300;

    public boolean askForPermissionCamera(Activity activity) {
        //start the permission activity task
        Log.i("askForPermissionCamera", "entry");

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Log.i("checkSelfPermission", "not granted to use the camera");
            // Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.i("shouldShowRequest", "Show an explanation");

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                // MY_PERMISSIONS_REQUEST_CAMERA is an app-defined int constant.
                // The callback method gets the result of the request.
                Log.i("requestPermissions", "send back MY_PERMISSIONS_REQUEST_CAMERA");
            }
        } else {
            // Permission has already been granted
            Log.i("askForPermissionCamera", "already granted");
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission has been granted
                    Log.i("PermissionsResult", "granted");
                } else {
                    // permission denied!
                    Log.i("PermissionsResult", "denied");
                }
            }
            case MY_PERMISSIONS_REQUEST_CALENDAR: {}
            default: {}
        }
    }






}
