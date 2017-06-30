package com.example.suryasdwivedi.receiver;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class receiver extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    DatabaseReference myref=FirebaseDatabase.getInstance().getReference();
    DatabaseReference myReflat=myref.child("message1");
    DatabaseReference myReflon=myref.child("message2");
    DatabaseReference myReflat2=myref.child("message3");
    DatabaseReference myReflon2=myref.child("message4");
    Double a=0.0;
    Double b=0.0;
    Double c=0.0;
    Double d=0.0;
    Double newa=0.0;
    Double newb=0.0;
    Double newc=0.0;
    Double newd=0.0;
    final List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();
    Marker x;
    Marker x1;
    int p=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
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

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(28.5973744,77.0706503) , 16.0f) );

        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.bus310764_640));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.bus310766_640));


        myReflat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (x != null)
                    x.remove();
                a = newa;
                newa = (Double) dataSnapshot.getValue(Double.class);

                if (newa != 0.0 && newb != 0.0) {
                    x = mMap.addMarker(new MarkerOptions().position(new LatLng(newa, newb)).icon(mImages.get(0)).flat(true));
                    x.setRotation((float) Math.toDegrees(Math.atan((a - newa) / (newb - b))));
                    p++;
                }

                if (p == 1)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newa, newb), 16.0f));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myReflon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (x != null)
                    x.remove();
                b = newb;
                newb = (Double) dataSnapshot.getValue(Double.class);

                if (newa != 0.0 && newb != 0.0) {
                    x = mMap.addMarker(new MarkerOptions().position(new LatLng(newa, newb)).icon(mImages.get(0)).flat(true));
                    x.setRotation((float) Math.toDegrees(Math.atan((a - newa) / (newb - b))));
                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });











        myReflat2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (x1 != null)
                    x1.remove();
                c = newc;
                newc = (Double) dataSnapshot.getValue(Double.class);

                if (newc != 0.0 && newd != 0.0) {
                    x1 = mMap.addMarker(new MarkerOptions().position(new LatLng(newc, newd)).icon(mImages.get(1)).flat(true));
                    x1.setRotation((float) Math.toDegrees(Math.atan((c - newc) / (newd - d))));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myReflon2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(x1!=null)
                x1.remove();
                d=newd;
                newd = (Double) dataSnapshot.getValue(Double.class);

                if (newc != 0.0 && newd != 0.0)
                { x1 = mMap.addMarker(new MarkerOptions().position(new LatLng(newc, newd)).icon(mImages.get(1)).flat(true));
                    x1.setRotation((float) Math.toDegrees(Math.atan((c-newc)/(newd-d))));}

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








    }
}
