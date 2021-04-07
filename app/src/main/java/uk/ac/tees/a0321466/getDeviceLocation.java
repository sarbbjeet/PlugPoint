
/* this class help to get current device location
addMaker and animationCamera  all operation are defined here.
 */
package uk.ac.tees.a0321466;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static uk.ac.tees.a0321466.globalVariables.DEFAULT_ZOOM;

class getDeviceLocation extends AppCompatActivity  {
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location getCurrentLocation;
    GoogleMap mMap;
    Context mActivity;

    public getDeviceLocation(Context _mActivity,FusedLocationProviderClient _mFusedLocationProviderClient, GoogleMap _map) {
        mActivity=_mActivity;
        mFusedLocationProviderClient=_mFusedLocationProviderClient;
        mMap=_map;
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener((Activity) mActivity, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                        getCurrentLocation = task.getResult();
                        if (getCurrentLocation != null) {
                            LatLng defaultLocation= new LatLng(54.5742982466006, -1.2349123090100282);
                            LatLng latLng = new LatLng(54.5742982466006, -1.2349123090100282);
                            LatLng latLng1= new LatLng(getCurrentLocation.getLatitude(),getCurrentLocation.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title("electric car house").icon(BitmapDescriptorFactory.fromResource(R.drawable.electric_img)));
                            mMap.addMarker(new MarkerOptions().position(latLng1).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.car111)));
                            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(defaultLocation,DEFAULT_ZOOM);
                            mMap.animateCamera(location);

                            }
                        }
                    }
                });

            } catch (SecurityException e) {
                Log.e("Exception: %s", e.getMessage(), e);
            }
        }





    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
