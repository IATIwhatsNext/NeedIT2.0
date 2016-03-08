package mydomain.needit;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Michal on 08/03/2016.
 */
public class UserLocation {
    private String userID;

    public String getUserID() {
        return userID;
    }

    public LatLng getLocation() {
        return location;
    }

    private LatLng location;

    public UserLocation(String userID, LatLng location) {
        this.userID = userID;
        this.location = location;
    }
}