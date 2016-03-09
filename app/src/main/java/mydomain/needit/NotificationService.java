package mydomain.needit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by dimitrke on 07/03/2016.
 */
public class NotificationService extends Service {

    public static final int NOTIFICATION_ID = 1;

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
                sendNotification();
                InMemoryDB.update();
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

    public void sendNotification() {

        Intent intent = new Intent();

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent intentAccept = new Intent();
        intentAccept.setClass(this, AcceptActivity.class);
        PendingIntent pendingIntentAccept = PendingIntent.getActivity(this, 0, intentAccept, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_stat_notification);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.addAction(R.drawable.reject, "Reject", pendingIntent);
        builder.addAction(R.drawable.ok, "Accept", pendingIntentAccept);
        new ServerPostResponseTask().execute(new Response(UserDetailsProvider.getUserLocation(), "I'm here!"));
        //todo: add call ServerPostResponseTesk.execute()

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        builder.setContentTitle("Need It -Notifications");
        builder.setContentText("Your help is needed!");
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}
