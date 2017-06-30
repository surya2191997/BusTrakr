package com.example.suryasdwivedi.tracker2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

public class MapsActivity extends FragmentActivity implements LocationListener, View.OnClickListener {


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.


    LocationManager locationManager;
    Location lastKnownLocation;
    String locationProvider;
    double a;
    double b;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    Marker markername;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    String s;
    DatabaseReference myReflat;
    DatabaseReference myReflon;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        logout=(Button)findViewById(R.id.logout);
        logout.setOnClickListener(this);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                3000,   // 3 sec
                5, this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MapsActivity.this, "Welcome "+user.getDisplayName()+ " !",
                            Toast.LENGTH_SHORT).show();//getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                    myReflat=ref.child(user.getUid()).child("latitiude");
                    myReflon=ref.child(user.getUid()).child("longitude");
                } else {

                } //display welcome message
            }
        };



    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }







    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    public void setUpMap() {
       /* LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        String locationProvider = LocationManager.NETWORK_PROVIDER;
        // Or use LocationManager.GPS_PROVIDER
        Location lastKnownLocation;
        lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);*/
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationProvider = LocationManager.NETWORK_PROVIDER;

        a = locationManager.getLastKnownLocation(locationProvider).getLatitude();
        b = locationManager.getLastKnownLocation(locationProvider).getLongitude();


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(a, b), 16.0f));

        markername = mMap.addMarker(new MarkerOptions().position(new LatLng(a, b)).title("Marker"));


    }


    @Override
    public void onLocationChanged(Location location) {
        markername.remove();

        a = locationManager.getLastKnownLocation(locationProvider).getLatitude();
        b = locationManager.getLastKnownLocation(locationProvider).getLongitude();

        markername = mMap.addMarker(new MarkerOptions().position(new LatLng(a,b)).title("Marker"));

        myReflat.setValue(a);
        myReflon.setValue(b);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
      if(v==logout)logout();
    }
}