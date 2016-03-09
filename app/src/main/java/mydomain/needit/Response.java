package mydomain.needit;

/**
 * Created by Michal on 08/03/2016.
 */
public class Response {
    UserLocation userLocation;

    String response;

    public UserLocation getUserLocation() {
        return userLocation;
    }

    public String getResponse() {
        return response;
    }

    public Response(UserLocation userLocation, String response) {

        this.userLocation = userLocation;
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Response) {
            Response r = (Response) o;
            return r.getUserLocation().getUserID().equals(this.getUserLocation().getUserID()) && r.getUserLocation().getLocation().equals(this.getUserLocation().getLocation()) && r.getResponse().equals(this.getResponse());
        }
        return false;
    }

}
