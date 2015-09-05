package com.travelr.example.markmyplaces.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.travelr.example.markmyplaces.db.MyPlace.MyPlaceColumns;

public final class MarkMyPlacesDBHelper extends SQLiteOpenHelper {
    
    private static MarkMyPlacesDBHelper mAppDBHelper ;
    /**
     * 
     * Constructor is made private to support singleton 
     */
    private MarkMyPlacesDBHelper(Context context) {
        super(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
    }
 
    /**
     * method to get helper instance
     * @param context - application context preferable
     * @return
     */
    public static MarkMyPlacesDBHelper getInstance(Context context) {
        if (mAppDBHelper == null) {
            synchronized (MarkMyPlacesDBHelper.class) {
                if (mAppDBHelper == null) {
                    mAppDBHelper = new MarkMyPlacesDBHelper(context);
                }
            }
        }
        return mAppDBHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        createTables(db);
        
    }

    private void createTables(SQLiteDatabase db) {
       db.execSQL(MyPlace.MyPlaceTable.CREATE_QUERY);
        
    }
    private void dropTables(SQLiteDatabase db) {
        db.execSQL(MyPlace.MyPlaceTable.DROP_QUERY);
         
     }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        createTables(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        dropTables(db);
        createTables(db);
    }

    protected long insert(String tableName, ContentValues values ){
        SQLiteDatabase writableDB = getWritableDatabase();
        long rowId = -1 ;
        synchronized (writableDB) {
            rowId  = writableDB.insert(tableName, null,values);
        }
        return rowId ;
    }
    protected Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupby,String having,String orderBy){
        SQLiteDatabase redableDB = getReadableDatabase();
        Cursor resultCursor = null ;
        synchronized (redableDB) {
            resultCursor = redableDB.query(table, columns, selection, selectionArgs, groupby, having, orderBy);
        }
        return resultCursor ;
    }
    protected int update(String tableName, ContentValues values, String whereClause, String[] whereArgs){
        SQLiteDatabase writableDB = getWritableDatabase();
        int updatedRows = 0 ;
        synchronized (writableDB) {
            updatedRows = writableDB.update(tableName, values, whereClause, whereArgs);
        }
        return updatedRows ;
    }
    public long insertMyPlace(MyPlace newMyPlace){
        return insert(MyPlace.MyPlaceTable.TABLE_NAME, newMyPlace.toContentValues());
    }
    public boolean updateMyPlace(long rowId, MyPlace newMyPlace){
        String whereClause = MyPlace.MyPlaceColumns._ID + " = ?" ;
        String[] whereArgs = {String.valueOf(rowId)};
        return update(MyPlace.MyPlaceTable.TABLE_NAME, newMyPlace.toContentValues(),whereClause,whereArgs) > 0;
    }
    public Cursor queryMyPlacesAll(boolean isDateLatest){
        String orderBy = MyPlace.MyPlaceColumns.CREATED_TIME + (isDateLatest ?  " DESC" : " ASC" );
        return query(MyPlace.MyPlaceTable.TABLE_NAME, null, null, null, null, null, orderBy);
    }
    public Cursor queryGeoFencedPlacesAll(boolean isDateLatest){
        String selection = MyPlaceColumns.IS_ADDED_FENCE +  " = ? " ; 
        String[] selectionArgs = {String.valueOf(MyPlace.IS_FENCE_ADDED)};
        String orderBy = MyPlace.MyPlaceColumns.CREATED_TIME + (isDateLatest ?  " DESC" : " ASC" );
        return query(MyPlace.MyPlaceTable.TABLE_NAME, null, selection, selectionArgs, null, null, orderBy);
    }
    
    public Cursor queryMyPlace(long rowid){
        String selection = MyPlaceColumns._ID +  " = ? " ; 
        String[] selectionArgs = {String.valueOf(rowid)};
        return query(MyPlace.MyPlaceTable.TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    public MyPlace getPlace(long rowId) {
        MyPlace resultPlace = null;
        Cursor resultQuery = queryMyPlace(rowId);
        if (resultQuery.moveToNext()) {
            resultPlace = new MyPlace(resultQuery);
        }
        return resultPlace;
    }
    public List<MyPlace> getMyPlaces(){
       return getPlaceListFromCursor(queryMyPlacesAll(true));
    }
    public List<MyPlace> getGeoFencedPlaces(){
        return getPlaceListFromCursor(queryGeoFencedPlacesAll(true));
     }
    public List<MyPlace> getPlaceListFromCursor(Cursor placeCursor){
        ArrayList<MyPlace> myPlacesList = new ArrayList<MyPlace>();
        if(placeCursor !=null ){
            while(placeCursor.moveToNext()){
                myPlacesList.add(new MyPlace(placeCursor));
            }
        }
        return myPlacesList;
    }
}
