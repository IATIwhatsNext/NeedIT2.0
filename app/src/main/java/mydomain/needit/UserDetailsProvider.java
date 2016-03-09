package mydomain.needit;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Michal on 08/03/2016.
 */
public class UserDetailsProvider extends Activity {
    private static GoogleApiClient mGoogleApiClient;
    static UserDetailsProvider userDetailsProvider;

    public static void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        UserDetailsProvider.mGoogleApiClient = mGoogleApiClient;
    }

    public static void setUserID(String userID) {
        UserDetailsProvider.userID = userID;
    }

    static String userID = "";

    public static String getUserID() {
        return userID;
    }

    public static UserDetailsProvider getUserDetailsProvider() {
        return userDetailsProvider;
    }

    private UserDetailsProvider() {
    }

    public LatLng getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                return new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            }
            return null;
        }
        return null;
    }

    public static UserLocation getUserLocation(){
        return new UserLocation(userID, getUserDetailsProvider().getLocation());
    }
}
