package mydomain.needit;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Michal on 08/03/2016.
 */
public class UserLocation {
    private String userID;

    public String getAccessToken() {
        return accessToken;
    }

    private String accessToken;

    public String getUserID() {
        return userID;
    }

    public LatLng getLocation() {
        return location;
    }

    private LatLng location;

    public UserLocation(String userID, String accessToken, LatLng location) {
        this.userID = userID;
        this.location = location;
        this.accessToken = accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof UserLocation){
            UserLocation ul = (UserLocation) o;
            return ul.getUserID().equals(this.getUserID()) && ul.getLocation().equals(this.location);
        }
        return false;
    }
}
