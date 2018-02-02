package com.example.rilu_pc.shoppyfy;

import android.app.Activity;
import android.app.Dialog;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fusedbulblib.GetCurrentLocation;
import com.fusedbulblib.interfaces.DialogClickListener;
import com.fusedbulblib.interfaces.GpsOnListener;



public class CurrentLocationActivity extends  AppCompatActivity implements GpsOnListener{

    TextView currentLocationTxt;
    GetCurrentLocation getCurrentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        TextView getLocationTxt=(TextView)findViewById(R.id.getLocationTxt);
        TextView stopTxt=(TextView)findViewById(R.id.stopTxt);

        currentLocationTxt=(TextView)findViewById(R.id.currentLocationTxt);
        getLocationTxt.setTypeface(new FontTypeFace(this).MontserratRegular());
        currentLocationTxt.setTypeface(new FontTypeFace(this).MontserratRegular());
        getCurrentLocation=new GetCurrentLocation(CurrentLocationActivity.this);
        getLocationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation.getContinuousLocation(true);
                getCurrentLocation.getCurrentLocation();
            }
        });

        stopTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CurrentLocationActivity.this,"Location update stop",Toast.LENGTH_SHORT).show();
                getCurrentLocation.stopLocationUpdate();
            }
        });
    }

    @Override
    public void gpsStatus(boolean _status) {
        if (_status==false){
            new CheckGPSDialog(this).showDialog(new DialogClickListener() {
                @Override
                public void positiveListener(Activity context, Dialog dialog) {
                    dialog.dismiss();
                    getCurrentLocation.getCurrentLocation();
                }

                @Override
                public void negativeListener(Activity context, Dialog dialog) {
                    dialog.dismiss();
                }
            });
        }else {
            getCurrentLocation.getCurrentLocation();
        }
    }

    @Override
    public void gpsPermissionDenied(int deviceGpsStatus) {
        if (deviceGpsStatus==1){
            permissionDeniedByUser();
        }else {
            getCurrentLocation.getCurrentLocation();
        }
    }

    @Override
    public void gpsLocationFetched(Location location) {
        if (location != null) {
            currentLocationTxt.setText(location.getLatitude()+", "+location.getLongitude());
            Log.w("locationUpdate",currentLocationTxt.getText().toString());
            // currentLocationTxt.setText(new GetAddress(this).fetchCurrentAddress(location));
        } else {
            Toast.makeText(this, getResources().getString(R.string.unable_find_location), Toast.LENGTH_SHORT).show();
        }
    }

    private void permissionDeniedByUser() {

        new PermissionDeniedDialog(this).showDialog(new DialogClickListener() {
            @Override
            public void positiveListener(Activity context, Dialog dialog) {
                dialog.dismiss();
                getCurrentLocation.getCurrentLocation();
            }

            @Override
            public void negativeListener(Activity context, Dialog dialog) {
                dialog.dismiss();
            }
        });
    }



}
