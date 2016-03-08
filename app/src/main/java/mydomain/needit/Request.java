package mydomain.needit;

/**
 * Created by Michal on 08/03/2016.
 */
public class Request {
    UserLocation userLocation;

    public UserLocation getUserLocation() {
        return userLocation;
    }

    public String getRequest() {
        return request;
    }

    public Request(UserLocation userLocation, String request) {

        this.userLocation = userLocation;
        this.request = request;
    }

    String request;
}
