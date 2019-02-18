package com.example.sutharnil.buggy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class BuggiesOnMap extends AppCompatActivity implements OnMapReadyCallback {

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private static final String TAG = "BuggiesOnMap";
    private static final String Fine_Location = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String Course_Location = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int ERROR_DAILOG = 9001;
    private Boolean mlocationpermission = false;
    private static final int Location_permission = 1234;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buggies_on_map);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.check, null);
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Booking Successful");
        alertDialogBuilder.setMessage("Your request for a \n buggy is Successfully \n assign to buggy in short time... ");
//        alertDialogBuilder.setView(view);
        alertDialogBuilder.setIcon(R.drawable.ic_check_black_24dp);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                alertDialog.cancel();
            }
        }).start();

        if (isServiced()) {
            init();
        }
    }

    private void init() {
        getpermisiion();
    }

    public boolean isServiced() {
        Log.d(TAG, "isServiced: checking google service  version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(BuggiesOnMap.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "is Serviced Ok");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {

            Log.d(TAG, "Error");
            AlertDialog alertDialog1 = (AlertDialog) GoogleApiAvailability.getInstance().getErrorDialog(BuggiesOnMap.this, available, ERROR_DAILOG);
            alertDialog1.dismiss();

        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    private void getpermisiion() {

        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Fine_Location) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Course_Location) == PackageManager.PERMISSION_GRANTED) {

                mlocationpermission = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permission, Location_permission);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mlocationpermission = false;

        switch (requestCode) {
            case Location_permission: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mlocationpermission = false;
                            return;
                        }
                    }
                    mlocationpermission = true;
                    initMap();
                }

            }
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(BuggiesOnMap.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



    }


}
