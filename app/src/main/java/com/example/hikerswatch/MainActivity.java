package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    TextView lat;
    TextView lon;
    TextView alt;
    TextView adr;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )== PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lat=(TextView)findViewById(R.id.textView10);
        lon=(TextView)findViewById(R.id.textView11);
        alt=(TextView)findViewById(R.id.textView12);
        adr=(TextView)findViewById(R.id.textView14);
        locationManager=(LocationManager)this.getSystemService(LOCATION_SERVICE);
        locationListener= new LocationListener( ) {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("LOCATION",location.toString());
                Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> lista= geo.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    lat.setText(String.format("LATITUDE:%s", location.getLatitude( )));
                    lon.setText(String.format("LOTITUDE:%s", location.getLongitude( )));
                    alt.setText(String.format("ALTITUDE:%s", location.getAltitude( )));
                    if(lista!=null && lista.size()>0){
                        String address="";

                        if(lista.get(0).getThoroughfare()!=null){
                            address+=lista.get(0).getThoroughfare()+"\n";
                        }
                        if(lista.get(0).getLocality()!=null){
                            address+=lista.get(0).getLocality()+"\n";

                        }
                        if(lista.get(0).getPostalCode()!=null){
                            address+=lista.get(0).getPostalCode();
                        }
                        adr.setText(address);

                    }
                } catch (IOException e) {
                    e.printStackTrace( );
                }

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
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},1 );
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,locationListener);
        }
    }
    }

