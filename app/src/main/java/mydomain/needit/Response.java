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
}
