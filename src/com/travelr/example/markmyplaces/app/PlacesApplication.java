package com.travelr.example.markmyplaces.app;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.travelr.example.markmyplaces.db.MarkMyPlacesDBHelper;

public class PlacesApplication extends Application {
    public static final String TAG = "PlacesApplication";
    private MarkMyPlacesDBHelper mDbHelper = null;
    public static final int MAX_GEOFENCES = 3 ;
    @Override
    public void onCreate() {
        super.onCreate();
        mDbHelper = MarkMyPlacesDBHelper.getInstance(getApplicationContext());
    }
    
    public static MarkMyPlacesDBHelper getDatabase(Context context){
       return ((PlacesApplication)context.getApplicationContext()).mDbHelper ;
    }
    public static void showGenericToast(Context context,String message){
        Toast.makeText(context,message , Toast.LENGTH_SHORT).show();
    }
   
    
}
