package mydomain.needit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by dimitrke on 07/03/2016.
 */
public class NotificationService extends Service {


    public static final int NOTIFICATION_REQ_ID = 1;
    public static final int NOTIFICATION_RES_ID = 2;
    private static Set<String> uniqueRequest = new TreeSet<>();
    private static Set<String> uniqueResponse = new TreeSet<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //  Toast.makeText(this,"Started",Toast.LENGTH_LONG).show();

        final Handler h = new Handler();
        final int delay = 10000; //milliseconds

        h.postDelayed(new Runnable() {
            public void run() {
                 InMemoryDB.update();
                notifyUserWithRequest(InMemoryDB.getRequests());
                notifyUserWithResponse(InMemoryDB.getResponses());
                h.postDelayed(this, delay);
            }
        }, delay);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //       Toast.makeText(this,"Stop",Toast.LENGTH_LONG).show();

    }

    public void notifyUserWithRequest(List<Request> requestList) {

        for (Request req : requestList) {
             String uniqueReq =req.getUserLocation().getUserID()+req.getRequest();
            if (UserDetailsProvider.getUserID() != req.getUserLocation().getUserID() && (!uniqueRequest.contains(uniqueReq))) {
                uniqueRequest.add(uniqueReq);
                Intent intent = new Intent();
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                Intent intentAccept = new Intent(getApplicationContext(), AcceptActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", req.getUserLocation().getUserID());
                intentAccept.putExtras(bundle);

                PendingIntent pendingIntentAccept = PendingIntent.getActivity(this, 0, intentAccept, 0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                builder.setSmallIcon(R.drawable.people_logo);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                builder.addAction(R.drawable.reject, "Reject", pendingIntent);
                builder.addAction(R.drawable.ok, "Accept", pendingIntentAccept);

                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.people_logo));
                builder.setContentTitle("Need It- Request for help");
                builder.setContentText("Someone need your help ,he need "+req.getRequest());
                NotificationManager notificationManager = (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_REQ_ID, builder.build());
            }
        }

    }

    public void notifyUserWithResponse(List<Response> responseList) {

        for (Response res : responseList) {
            String uniqueRes =res.getUserLocation().getUserID()+res.getResponse();
            if (UserDetailsProvider.getUserID() != res.getUserLocation().getUserID() && ((!uniqueResponse.contains(uniqueRes)))) {
                uniqueResponse.add(uniqueRes);
                Intent intentAccept = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("userId", res.getUserLocation().getUserID());
                intentAccept.putExtras(bundle);
                intentAccept.setClass(this, UserDetailsActivity.class);
                PendingIntent pendingIntentAccept = PendingIntent.getActivity(this, 0, intentAccept, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

                builder.setSmallIcon(R.drawable.people_logo);

                builder.setAutoCancel(true);
                builder.addAction(R.drawable.ok, "OK", pendingIntentAccept);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.people_logo));
                builder.setContentTitle("Need It- Response for help");
                builder.setContentText("You get Help :)");
                NotificationManager notificationManager = (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_RES_ID, builder.build());
            }
        }
    }

}
