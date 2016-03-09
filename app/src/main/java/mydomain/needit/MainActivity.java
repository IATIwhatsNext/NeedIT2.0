package mydomain.needit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;


    protected boolean isAvailable = false;
    private String userID = "";

    /**
     * Send a  notification service.
     */
    public void startNotification(View view) {

        Intent intentService = new Intent(this, NotificationService.class);
        startService(intentService);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        String userName = intent.getStringExtra("userName");
        String userLastName = intent.getStringExtra("userLastName");
        this.userID = userName + userLastName;

        InMemoryDB.registerUpdate(InMemoryDB.Type.USERS, new Runnable() {
            @Override
            public void run() {
                drawUsersOnMap(InMemoryDB.getUserLocations(), InMemoryDB.Type.USERS);
            }
        });
        InMemoryDB.registerUpdate(InMemoryDB.Type.REQUESTS, new Runnable() {
            @Override
            public void run() {
                drawUsersOnMap(InMemoryDB.getUserLocations(), InMemoryDB.Type.REQUESTS);
            }
        });
        InMemoryDB.registerUpdate(InMemoryDB.Type.RESPONSES, new Runnable() {
            @Override
            public void run() {
                drawUsersOnMap(InMemoryDB.getUserLocations(), InMemoryDB.Type.RESPONSES);
            }
        });
        Toast.makeText(this, "Hello " + userName + " " + userLastName, Toast.LENGTH_LONG).show();

        Switch availableIndicationSwitch = (Switch) findViewById(R.id.availableIndication);
        availableIndicationSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAvailable = isChecked;
            }
        });

        startNotification(this.getWindow().getDecorView().findViewById(android.R.id.content));

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.getUiSettings().setMapToolbarEnabled(false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        drawUsersOnMap(InMemoryDB.getUserLocations(), InMemoryDB.Type.USERS); //TODO: remove this after server updates the map

        Button helpBtn = (Button) findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpActivity = new Intent(getApplicationContext(), HelpSourcesActivity.class);
                startActivity(helpActivity);
            }
        });

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
            UserDetailsProvider.setmGoogleApiClient(mGoogleApiClient);
        }
    }

    public void drawUsersOnMap(List<UserLocation> userLocations, InMemoryDB.Type type) {
        float hue = BitmapDescriptorFactory.HUE_RED;
        switch (type) {
            case USERS:
                break;
            case RESPONSES:
                break;
            case REQUESTS:
                break;
        }
        for (UserLocation location : userLocations) {
            mMap.addMarker(new MarkerOptions()
                    .position(location.getLocation())
                    .title(location.getUserID())
                    .icon(BitmapDescriptorFactory.defaultMarker(hue)));
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                LatLng myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                Toast.makeText(this, myLocation.toString(), Toast.LENGTH_SHORT).show();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                new ServerPostUserTask().execute(UserDetailsProvider.getUserLocation());
            }
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // We are not connected anymore!
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // We tried to connect but failed!
    }

    //TODO: this is a temp function, should remove this once the server is ready
    public Map<String, LatLng> getUsersLocations() {
        Map<String, LatLng> users = new HashMap<>();

        users.put("Shahar", new LatLng(32.1, 34.85));
        users.put("Michal", new LatLng(32.1, 34.86));
        users.put("Shirly", new LatLng(32.1, 34.87));
        users.put("Katy", new LatLng(32.1, 34.88));

        return users;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intentService = new Intent(this, NotificationService.class);
        stopService(intentService);
    }

    public String getUserID() {
        return userID;
    }
}
