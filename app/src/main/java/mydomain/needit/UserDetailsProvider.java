package mydomain.needit;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

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
        if (userDetailsProvider == null) {
            userDetailsProvider = new UserDetailsProvider();
        }
        return userDetailsProvider;
    }

    public LatLng getLocation() {

        try {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                return new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            }
        } catch (SecurityException e) {
            Log.e("security", "location permission issue");
        }

        return new LatLng(32.1, 34.87);
    }

    public static UserLocation getUserLocation() {
        return new UserLocation(userID, getUserDetailsProvider().getLocation());
    }
}
