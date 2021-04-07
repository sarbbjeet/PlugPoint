package uk.ac.tees.a0321466;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,OnCustomCallback {

    private GoogleMap mMap;
    mapPermission mPermission;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location getCurrentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //below lines are used to initialize the mapFragment and connect with map id
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map_layout, mapFragment)
                .commit();
        if (mapFragment != null) {
            //pass context reference or "OnMapReadyCallback function" for callback operation
            mapFragment.getMapAsync(this);
        }


        //click listener to get current Location and enable gps access permissions
        findViewById(R.id.getLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 new mapPermission(MapsActivity.this);
//                 new mapPermission(MapsActivity.this,new OnCustomEventListener() {
//                    public void onEvent() {
//                        //do whatever you want to do when the event is performed.
//                    }
//                 });

             }
        });

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            //firstly check, Is gps permissions are enable or not
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //mMap.setMyLocationEnabled(true);
            new getDeviceLocation(MapsActivity.this, mFusedLocationProviderClient, mMap);
          //  mMap.setMyLocationEnabled(true);
        }

    @Override
    public void gpsEnableDone() { //callback function automatically call when gps is enabled
        //Toast.makeText(MapsActivity.this,"rdhkfdkjhfhdkjfh...",Toast.LENGTH_SHORT).show();
    //perform operations when gps enable
    }


    @Override
    public boolean onMyLocationButtonClick() {
        //new getDeviceLocation(MapsActivity.this, mFusedLocationProviderClient, mMap);
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        new getDeviceLocation(MapsActivity.this, mFusedLocationProviderClient, mMap);

    }



//    public interface OnCustomEventListener{
//        public void onEvent();   //method, which can have parameters
//    }
}