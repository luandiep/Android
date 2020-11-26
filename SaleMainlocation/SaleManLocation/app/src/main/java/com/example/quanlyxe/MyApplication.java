package com.example.quanlyxe;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleObserver;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.quanlyxe.login.LoginActivity;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class MyApplication extends Application implements  LifecycleObserver {
    SharedPreferences sharedPreferences;
    public static final String TAG = MyApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private int numRunningActivities = 0;
    private static MyApplication mInstance;


    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = this;


        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStarted(Activity activity) {
                numRunningActivities++;
                if (numRunningActivities == 1) {

                    Log.d("APPLICATION", "APP IN FOREGROUND");
                }

            }

            @Override
            public void onActivityStopped(Activity activity) {

                numRunningActivities--;
                if (numRunningActivities == 0) {
                    Log.e("", "App is in BACKGROUND");

                }


            }


            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityResumed(Activity activity) {



            }

            @Override
            public void onActivityPaused(Activity activity) {



            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {


            }
        });



    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }













}
