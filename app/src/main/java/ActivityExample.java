import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fusedbulblib.GetCurrentLocation;
import com.fusedbulblib.interfaces.GpsOnListener;


/**
 * Created by babur on 31-01-2018.
 */

public class ActivityExample extends AppCompatActivity implements GpsOnListener {

    @Override
    public void gpsStatus(boolean _status) {
        if (_status==true){
            new GetCurrentLocation(this).getCurrentLocation();
        }else {
            //GPS is off on user's device.
        }
    }

    @Override
    public void gpsPermissionDenied(int deviceGpsStatus) {
        if (deviceGpsStatus==1){
            //user does not want to switch on GPS of his/her device.
        }

    }

    @Override
    public void gpsLocationFetched(Location location) {
        if (location!=null){
            // you will get user's current location
        }else {
            Toast.makeText(this,"Unable to find location",Toast.LENGTH_SHORT).show();
        }
    }

}