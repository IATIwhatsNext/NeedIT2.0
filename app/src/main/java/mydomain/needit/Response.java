package mydomain.needit;

/**
 * Created by Michal on 08/03/2016.
 */
public class Response {
    UserLocation userLocation;

    private final String responseToUser;

    public String getResponseToaccessToken() {
        return responseToaccessToken;
    }

    private final String responseToaccessToken;

    public UserLocation getUserLocation() {
        return userLocation;
    }

    public String getResponseToUser() {
        return responseToUser;
    }

    public Response(UserLocation userLocation, String responseToUser, String responseToaccessToken) {
        this.userLocation = userLocation;
        this.responseToUser = responseToUser;
        this.responseToaccessToken = responseToaccessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Response) {
            Response r = (Response) o;
            return r.getUserLocation().getUserID().equals(this.getUserLocation().getUserID()) && r.getUserLocation().getLocation().equals(this.getUserLocation().getLocation()) && r.getResponseToUser().equals(this.getResponseToUser());
        }
        return false;
    }

}
