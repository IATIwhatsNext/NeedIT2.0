package mydomain.needit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Michal on 08/03/2016.
 */
public class InMemoryDB {
    static List<UserLocation> userLocations = new LinkedList<>();
    static List<Request> requests = new LinkedList<>();
    static List<Response> responses = new LinkedList<>();
    static Map<Type, List<Runnable>> runnableMap = new HashMap<>();

    public static List<UserLocation> getUserLocations() {
        return userLocations;
    }

    public static List<Request> getRequests() {
        return requests;
    }

    public static List<Response> getResponses() {
        return responses;
    }

    public static void setUserLocations(List<UserLocation> userLocations) {
        if (InMemoryDB.userLocations.containsAll(userLocations) && userLocations.containsAll(InMemoryDB.userLocations)) {
            return;
        }
        InMemoryDB.userLocations = userLocations;
        updateRegistered(Type.USERS);
    }

    public static void setRequests(List<Request> requests) {
        if (InMemoryDB.requests.containsAll(requests) && requests.containsAll(InMemoryDB.requests)) {
            return;
        }

        InMemoryDB.requests = requests;
        updateRegistered(Type.REQUESTS);
    }

    public static void setResponses(List<Response> responses) {
        if (InMemoryDB.responses.containsAll(responses) && responses.containsAll(InMemoryDB.responses)) {
            return;
        }
        InMemoryDB.responses = responses;
        updateRegistered(Type.RESPONSES);
    }

    public static void registerUpdate(Type type, Runnable callback) {
        List<Runnable> runnables = runnableMap.get(type);
        if (runnables == null) {
            runnables = new LinkedList<>();
            runnableMap.put(type, runnables);
        }
        runnables.add(callback);
    }

    public static void updateRegistered(Type type) {
        List<Runnable> runnables = runnableMap.get(type);
        if (runnables == null)
            return;
        for (Runnable runnable : runnableMap.get(type)) {
            runnable.run();
        }
    }

    public static void update() {
        new ServerUsersTask().execute();
        new ServerRequestTask().execute();
        new ServerResponderstTask().execute();
        //new ServerPostUserTask().execute(new UserLocation("Michal", UserDetailsProvider.getUserDetailsProvider().getLocation())); //todo:access location etc.
    }

    public enum Type {
        USERS,
        RESPONSES,
        REQUESTS
    }
}
