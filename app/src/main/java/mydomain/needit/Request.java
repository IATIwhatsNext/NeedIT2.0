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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Request) {
            Request r = (Request) o;
            return r.getUserLocation().getUserID().equals(this.getUserLocation().getUserID()) && r.getUserLocation().getLocation().equals(this.getUserLocation().getLocation()) && r.getRequest().equals(this.getRequest());
        }
        return false;
    }
}
