package com.example.wbudyapp.functions;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.ContextCompat;


public class GpsTracker extends Activity implements LocationListener {

    Context context;

    public GpsTracker(Context context) {
        super();
        this.context = context;
    }

    public Location getLocation(){
        if (ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            Log.e("fist","error");
            return null;
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,10,this);
                return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }else{
                Toast.makeText(context.getApplicationContext(),"Unable to connect with GPS",Toast.LENGTH_SHORT).show();
                Log.e("sec","errpr");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public double getDistanceToTUL(Location l){
        double distance=0;
        if( l == null){
            Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
        }
        else {
            distance = calcDistance(l.getLatitude(),l.getLongitude(),51.74720764160156,19.453283309936523);
        }
        return distance;
    }


    public double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    public double calcDistance(double lat1, double lon1, double lat2, double lon2) {
        int radius = 6371;
        double dLat = degreesToRadians(lat2-lat1);
        double dLon = degreesToRadians(lon2-lon1);
        lat1 = degreesToRadians(lat1);
        lat2 = degreesToRadians(lat2);
        double temp = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double temp2 = 2 * Math.atan2(Math.sqrt(temp), Math.sqrt(1-temp));
        return radius * temp2;
    }

    @Override
    public void onLocationChanged(Location location) {
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}