package com.assem.tests.iotblue.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.assem.tests.iotblue.Fragments.MapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class MapUtil {

    private static final String MapUtil = MapFragment.class.getSimpleName();

    public void customMarker(Context context, GoogleMap mMap, int drawable, String title, Location location) {
        int height = 80;
        int width = 80;
        BitmapDrawable bitmapDraw = (BitmapDrawable) context.getResources().getDrawable(drawable);
        Bitmap b = bitmapDraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions.title(title).position(new LatLng(location.getLatitude(), location.getLongitude()))
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        mMap.addMarker(mMarkerOptions);
    }

    public void customMarker(Context context, GoogleMap mMap, int drawable, String title, LatLng latLng) {
        int height = 80;
        int width = 80;
        BitmapDrawable bitmapDraw = (BitmapDrawable) context.getResources().getDrawable(drawable);
        Bitmap b = bitmapDraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions.title(title).position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        mMap.addMarker(mMarkerOptions);
    }

    public void animateCamera(GoogleMap mMap, Location location, float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoom));
    }

    public Location getDeviceLocation(final Activity activity) {
        final Location[] deviceLocation = new Location[1];
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(activity));
        try {
            Task location = mFusedLocationClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        deviceLocation[0] = (Location) task.getResult();
                        Log.d("deviceLocation", "Assem hhh deviceLocation : " + deviceLocation[0].toString());
                        // move Cam to user location
                    } else {
                        Toast.makeText(activity, "Can't find location", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (SecurityException e) {
            Toast.makeText(activity, "Exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Exception", "Assem hhh Exception : " + e.toString());
            System.out.println("Exception Assem hhh Exception : " + e.toString());
        }
        return deviceLocation[0];
    }

    public void getUserLocation(Context context) {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(context));
        try {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Exception", "Assem hhh Exception : " + e.toString());
                }
            });
        } catch (SecurityException e) {
            Log.d("Catch", "Assem hhh catch : " + e.toString());
        }
    }
}
