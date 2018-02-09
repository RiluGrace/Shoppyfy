package com.example.rilu_pc.shoppyfy;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.*;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.fusedbulblib.GetCurrentLocation;
import com.fusedbulblib.interfaces.DialogClickListener;
import com.fusedbulblib.interfaces.GpsOnListener;
import com.mindorks.placeholderview.PlaceHolderView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;



public class HomeActivity extends AppCompatActivity implements GpsOnListener
{

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList<String> permissions = new ArrayList();
    GetCurrentLocation getCurrentLocation;
    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;

    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;
    final DatabaseHandler db = new DatabaseHandler(this);
    private final static int ALL_PERMISSIONS_RESULT = 101;

     Double longitude=0.0,latitude=0.0;
    LocationTrack locationTrack;
    EditText et_name,et_type;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        et_name=findViewById(R.id.et_name);
        et_type=findViewById(R.id.et_type);
        image = findViewById(R.id.image);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getCurrentLocation=new GetCurrentLocation(HomeActivity.this);
        try {
            getCurrentLocation.getContinuousLocation(true);
            getCurrentLocation.getCurrentLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Picasso.with(this)
                .load("https://static.highsnobiety.com/wp-content/uploads/2017/07/12094906/balenciaga-expensive-shopping-bag-00.jpg")
                .placeholder(R.drawable.placeholder)   // optional
               // .error(R.drawable.error)      // optional
                .resize(400,400)                     // optional
                .into(image);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDrawer.openDrawer(Gravity.LEFT);
            }
        });

        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat(" KK:mm:ss a");
        Date = simpledateformat.format(calander.getTime());

        db.addTime(new Time(Date));
        List<Time> contacts = db.getAllTime();
        for (Time cn : contacts)
        {
            String log = "Time: " + cn.get_time();
            // Writing Contacts to log
            Log.d("time: ", log);
        }



        mDrawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView)findViewById(R.id.drawerView);
        mGalleryView = (PlaceHolderView)findViewById(R.id.galleryView);
        setupDrawer();


        //final DatabaseHandler db = new DatabaseHandler(this);
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        else
        {


        }


        Button btn = (Button) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {

                String image="https://waspwebsite-wpengine.netdna-ssl.com/wp-content/uploads/2014/04/1436436153_Login-1024x1024.png";
                String name=et_name.getText().toString();
                String loc=getAddress(longitude,latitude);
                String type=et_type.getText().toString();
                Toast.makeText(getApplicationContext(), "saved " +"name:" + name + "\nlocation:" + loc + "\nbusiness type:" + type , Toast.LENGTH_SHORT).show();

                db.addMerchant(new Merchant(image,name,loc,type));

                // Reading all contacts
                Log.d("Reading: ", "Reading all contacts..");
                List<Merchant> contacts = db.getAllMerchant();

                for (Merchant cn : contacts)
                {
                    String log = "Name: " + cn.getName() + " ,Location: " + cn.get_location()+ ",  Businesstype: " + cn.get_business_type();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }


               locationTrack = new LocationTrack(HomeActivity.this);


                if (locationTrack.canGetLocation())
                {


//                    double longitude = locationTrack.getLongitude();
//                    double latitude = locationTrack.getLatitude();

                   // Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
                }
                else {

                    locationTrack.showSettingsAlert();
                }

            }
        });
        Log.d("Insert: ", "Inserting ..");




    }


    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;

                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    protected void onDestroy() {
        super.onDestroy();
        if(locationTrack!=null) {
            locationTrack.stopListener();
        }
    }
    private void setupDrawer(){
        mDrawerView
                .addView(new DrawerHeader(HomeActivity.this))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_ARTICLES))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_NEW_ARTICLES))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOCATION_HISTROY))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGIN_HISTORY));


        ActionBarDrawerToggle  drawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open_drawer, R.string.close_drawer)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
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
    public void gpsLocationFetched(android.location.Location location) {
        if (location != null) {

            longitude = location.getLongitude();
            latitude = location.getLatitude();
            Log.d("longitude", Double.toString(longitude));
            Log.d("latitude", Double.toString(latitude));
            db.addLocation(new Location(Date,Double.toString(longitude),Double.toString(latitude)));

            List<Location> contact = db.getAllLocation();
            for (Location cn : contact)
            {
                String log = "Time: " + cn.get_time() + " ,Longitude: " + cn.get_lon()  + " ,Latitude: " + cn.get_lat();

                Log.d("location: ", log);
            }


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

    public String getAddress(double lon, double lat)
    {
        Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
       String add=" ";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            Address obj = addresses.get(0);
            add = obj.getLocality();
            Log.v("place", "Place :" + add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }

    public void details_now(View r)
    {

        Intent i = new Intent(HomeActivity.this, DetailActivity.class);
        startActivity(i);
    }
    public void location(View r)
    {

        Intent i = new Intent(HomeActivity.this, CurrentLocationActivity.class);
        startActivity(i);
    }


}




