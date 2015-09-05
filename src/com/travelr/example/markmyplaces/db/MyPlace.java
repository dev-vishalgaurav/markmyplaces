package com.travelr.example.markmyplaces.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class MyPlace  {
    
    public static interface MyPlaceTable {
        public static final String TABLE_NAME = "my_palces";
        public static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +  " ( " + 
                    MyPlaceColumns._ID + " "            + DBConstants.DB_TYPE_PRIMARY_KEY + "," + 
                    MyPlaceColumns.TITLE + " "          + DBConstants.DB_TYPE_TEXT + "," + 
                    MyPlaceColumns.MESSAGE + " "        + DBConstants.DB_TYPE_TEXT + "," + 
                    MyPlaceColumns.ADDRESS + " "        + DBConstants.DB_TYPE_TEXT + "," + 
                    MyPlaceColumns.LATITUDE + " "       + DBConstants.DB_TYPE_REAL + "," + 
                    MyPlaceColumns.LONGITUDE + " "      + DBConstants.DB_TYPE_REAL + "," + 
                    MyPlaceColumns.CREATED_TIME + " "   + DBConstants.DB_TYPE_INTEGER + "," +
                    MyPlaceColumns.FENCE_RADIUS + " "   + DBConstants.DB_TYPE_INTEGER + "," +
                    MyPlaceColumns.IS_ADDED_FENCE + " " + DBConstants.DB_TYPE_INTEGER + " DEFAULT " + DEFAULT_IS_FENCE_ADDED + 
                    ");" ;
        public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME ;
    }
    public static interface MyPlaceColumns extends BaseColumns{
        public static final String ADDRESS = "address";
        public static final String MESSAGE = "message";
        public static final String TITLE = "title";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String IS_ADDED_FENCE = "is_fence";
        public static final String CREATED_TIME = "created_time";
        public static final String FENCE_RADIUS = "radius";
        public static final String[] COLUMN_ALL = {_ID,ADDRESS,MESSAGE,TITLE,LATITUDE,LONGITUDE,IS_ADDED_FENCE,CREATED_TIME,FENCE_RADIUS};

    }
   public static final int RADIUS_DEFAULT = 100 ;
   public static final int IS_FENCE_ADDED = 1 ;   
   public static final int DEFAULT_IS_FENCE_ADDED = 0 ;

   
   private String address ; 
   private long dbId ; 
   private String title;
   private String message ; 
   private double latitude ;
   private double longitude;
   private int isAddedToFence ; 
   private long createdTime ;
   private int radius;
   
   public MyPlace(Cursor dbCursor){
       dbId = dbCursor.getLong(dbCursor.getColumnIndex(MyPlaceColumns._ID));
       title = dbCursor.getString(dbCursor.getColumnIndex(MyPlaceColumns.TITLE));
       message = dbCursor.getString(dbCursor.getColumnIndex(MyPlaceColumns.MESSAGE));
       address = dbCursor.getString(dbCursor.getColumnIndex(MyPlaceColumns.ADDRESS));
       latitude = dbCursor.getDouble(dbCursor.getColumnIndex(MyPlaceColumns.LATITUDE));
       longitude = dbCursor.getDouble(dbCursor.getColumnIndex(MyPlaceColumns.LONGITUDE));
       isAddedToFence = dbCursor.getInt(dbCursor.getColumnIndex(MyPlaceColumns.IS_ADDED_FENCE));
       createdTime = dbCursor.getLong(dbCursor.getColumnIndex(MyPlaceColumns.CREATED_TIME));
       radius = dbCursor.getInt(dbCursor.getColumnIndex(MyPlaceColumns.FENCE_RADIUS));
   }
    public MyPlace(String address, String title, String message, double latitude, double longitude, boolean isAddedToFence, long createdTime,int radius) {
        super();
        this.address = address;
        this.title = title;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isAddedToFence = (isAddedToFence) ? IS_FENCE_ADDED : DEFAULT_IS_FENCE_ADDED;
        this.createdTime = createdTime;
        this.radius = radius ;
    }
    public MyPlace(String address, String title, String message, double latitude, double longitude,long createdTime) {
        this(address,title,message,latitude,longitude,false,createdTime,RADIUS_DEFAULT);
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public boolean isAddedToFence() {
        return isAddedToFence == IS_FENCE_ADDED;
    }
    public void setIsAddedToFence(boolean isAddedToFence) {
        this.isAddedToFence = isAddedToFence ? IS_FENCE_ADDED : DEFAULT_IS_FENCE_ADDED;
    }
    public long getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
    public long getDbId() {
        return dbId;
    }
    public void setDbId(long dbId) {
        this.dbId  = dbId;
    }
    public int getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyPlaceColumns.MESSAGE, message);
        contentValues.put(MyPlaceColumns.ADDRESS, address);
        contentValues.put(MyPlaceColumns.LATITUDE, latitude);
        contentValues.put(MyPlaceColumns.LONGITUDE, longitude);
        contentValues.put(MyPlaceColumns.IS_ADDED_FENCE, isAddedToFence);
        contentValues.put(MyPlaceColumns.FENCE_RADIUS, radius);
        contentValues.put(MyPlaceColumns.CREATED_TIME, createdTime);
        contentValues.put(MyPlaceColumns.TITLE, title);
        return contentValues;
    }
    @Override
    public boolean equals(Object o) {
        
        return this.dbId == ((MyPlace)o).dbId;
    }
    
    public Uri getShareUri(){
        return Uri.parse("geo:"+ getLatitude() + ","+longitude);
    }
}
