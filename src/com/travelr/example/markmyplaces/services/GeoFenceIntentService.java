
package com.travelr.example.markmyplaces.services;

import java.util.List;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.travelr.example.markmyplaces.R;
import com.travelr.example.markmyplaces.app.PlacesApplication;
import com.travelr.example.markmyplaces.db.MyPlace;
import com.travelr.example.markmyplaces.ui.HomeActivity;

public class GeoFenceIntentService extends IntentService {

    public static final String TAG = "GeoFenceIntentService";

    public GeoFenceIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "GeoFenceIntent Called");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError() ) {
            String errorMessage = "Geocoding error ";
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            String title = (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ) ? getString(R.string.enter_area) : getString(R.string.exit_area);
            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(triggeringGeofences);

            // Send notification and log the transition details.
            sendNotification(title,geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(TAG, "invalid geofence transition type");
        }

    }

  

    private String getGeofenceTransitionDetails(List<Geofence> triggeringGeofences) {
        StringBuilder strings = new StringBuilder();
        for (Geofence geofence : triggeringGeofences) {
            MyPlace place = getGeoFencePlace(geofence);
            if(place!=null){
                strings.append(place.getTitle() + " ") ;
            }
        }
        return strings.toString();
    }

    private MyPlace getGeoFencePlace(Geofence geofence) {
        MyPlace place = null;
        if (geofence.getRequestId() != null) {

            long placeId = Long.parseLong(geofence.getRequestId());
            place = PlacesApplication.getDatabase(getBaseContext()).getPlace(placeId);
        }
        return place;
    }
    
    private void sendNotification(String title, String message){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher).setContentTitle(title)
                .setContentText(message);
        Intent resultIntent = new Intent(this, HomeActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getBaseContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT, null);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

}
