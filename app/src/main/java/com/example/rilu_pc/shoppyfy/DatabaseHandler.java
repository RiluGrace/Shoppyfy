package com.example.rilu_pc.shoppyfy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rilu-PC on 10-01-2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper
    {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Database";
        private static final String TABLE_MERCHANT = "merchant";
        private static final String TABLE_TIME = "time";
        private static final String TABLE_LOCATION = "loc";
        private static final String KEY_TIME ="time";
        private static final String KEY_IMAGE="image";
        private static final String KEY_NAME = "name";
        private static final String KEY_LATITUDE= "lat";
        private static final String KEY_LONDITUDE= "lon";
        private static final String KEY_LOCATION = "location";
        private static final String KEY_BUSSINESS_TYPE = "business_type";

        public DatabaseHandler(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            //3rd argument to be passed is CursorFactory instance
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_MERCHANT_TABLE = "CREATE TABLE " + TABLE_MERCHANT + "("+ KEY_IMAGE + " TEXT," + KEY_NAME + " TEXT,"+ KEY_LOCATION + " TEXT," + KEY_BUSSINESS_TYPE+ " TEXT" + ")";
            db.execSQL(CREATE_MERCHANT_TABLE);
            String CREATE_TIME_TABLE = "CREATE TABLE " + TABLE_TIME + "("  + KEY_TIME + " TEXT" +")";
            db.execSQL(CREATE_TIME_TABLE);
            String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATION + "("  + KEY_TIME + " TEXT ," + KEY_LONDITUDE + " TEXT ," + KEY_LATITUDE + " TEXT" + ")";
            db.execSQL(CREATE_LOCATION_TABLE);
        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MERCHANT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
            // Create tables again
            onCreate(db);
        }

        // code to add the new merchant
        void addMerchant(Merchant merchant) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_IMAGE,merchant.getImageurl());
            values.put(KEY_NAME, merchant.getName()); // Merchant Name
            values.put(KEY_LOCATION, merchant.get_location()); // merchant loc
            values.put(KEY_BUSSINESS_TYPE, merchant.get_business_type()); // merchant type

            // Inserting Row
            db.insert(TABLE_MERCHANT, null, values);
            //2nd argument is String containing nullColumnHack
            db.close(); // Closing database connection
        }

        //code to add new time
        //storedData in DB

        void addTime(com.example.rilu_pc.shoppyfy.Time time)
        {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TIME, time.get_time());

            db.insert(TABLE_TIME, null, values);
            //2nd argument is String containing nullColumnHack
            db.close(); // Closing
        }

        void addLocation(Location location) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TIME, location.get_time());
            values.put(KEY_LONDITUDE,location.get_lon());
            values.put(KEY_LATITUDE,location.get_lat());

            // Inserting Row
            db.insert(TABLE_LOCATION, null, values);
            //2nd argument is String containing nullColumnHack
            db.close(); // Closing database connection
        }
        // code to get the single merchant
        Merchant getMerchant(String name) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_MERCHANT, new String[] { KEY_IMAGE,KEY_NAME, KEY_BUSSINESS_TYPE,KEY_LOCATION }, KEY_NAME + "=?",
                    new String[] { name }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

           Merchant merchant = new Merchant( cursor.getString(0),cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
           // return merchant
           return merchant;
        }

        // code to get single time
        Time getTime(String time) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_TIME, new String[] { KEY_TIME }, KEY_TIME + "=?",
                    new String[] { time }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            Time time1 = new Time(cursor.getInt(0));
            // return time..
            return time1;
        }
        // code to get the single merchant
        Location getlon(String loc) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_LOCATION, new String[] { KEY_TIME,KEY_LONDITUDE,KEY_LATITUDE }, KEY_TIME + "=?",
                    new String[] { loc }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            Location l = new Location( cursor.getString(0), cursor.getString(1),cursor.getString(2));
            return l;
        }
        // code to get all merchants in a list view
        public List<Merchant> getAllMerchant() {
            List<Merchant> merchantList = new ArrayList<Merchant>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_MERCHANT;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Merchant merchant = new Merchant();
                    merchant.setImageurl(cursor.getString(0));
                    merchant.setName(cursor.getString(1));
                    merchant.setlocation(cursor.getString(2));
                    merchant.setbusinesstype(cursor.getString(3));
                    // Adding merchant to list
                    merchantList.add(merchant);
                } while (cursor.moveToNext());
            }

            // return merchant list
            return merchantList;
        }
        // code to get all location in a list view
        public List<Location> getAllLocation() {
            List<Location> locationList = new ArrayList<Location>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Location location = new Location();
                    location.setTime(cursor.getString(0));
                    location.setlon(cursor.getString(1));
                    location.setlat(cursor.getString(2));

                    // Adding location to list
                    locationList.add(location);
                } while (cursor.moveToNext());
            }

            // return merchant list
            return locationList;
        }

        // code to get all time in a list view

        // package time used

        public List<com.example.rilu_pc.shoppyfy.Time> getAllTime() {
            List<com.example.rilu_pc.shoppyfy.Time> TimeList =new ArrayList<com.example.rilu_pc.shoppyfy.Time>();
          //  List<Time> TimeList = new ArrayList<Time>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_TIME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    com.example.rilu_pc.shoppyfy.Time time=new com.example.rilu_pc.shoppyfy.Time();
                    time.set_time(cursor.getString(0));

                    // Adding time to list
                    TimeList.add(time);
                } while (cursor.moveToNext());
            }

            // return timelist
            return TimeList;
        }

        // code to update the single merchant
        public int updateMerchant(Merchant merchant)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_IMAGE,merchant.getImageurl());
            values.put(KEY_NAME, merchant.getName());
            values.put(KEY_LOCATION, merchant.get_location());
            values.put(KEY_BUSSINESS_TYPE, merchant.get_business_type());

            // updating row
            return db.update(TABLE_MERCHANT, values, KEY_NAME + " = ?",
                    new String[] { String.valueOf(merchant.getName()) });
        }
        public int updateLocation(Location location)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TIME, location.get_time());
            values.put(KEY_LONDITUDE,location.get_lon());
            values.put(KEY_LATITUDE,location.get_lat());

            // updating row
            return db.update(TABLE_LOCATION, values, KEY_TIME + " = ?",
                    new String[] { String.valueOf(location.get_time()) });
        }

        // code to update the single time
        public int updateTime(Time time)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TIME, time.getTime());

            // updating row
            return db.update(TABLE_TIME, values, KEY_TIME + " = ?",
                    new String[] { String.valueOf(time.getTime()) });
        }

        // Deleting single merchant
        public void deleteMerchant(Merchant merchant)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_MERCHANT, KEY_NAME + " = ?",
                    new String[] { String.valueOf(merchant.getName()) });
            db.close();
        }
        // Deleting single location
        public void deleteLocation(Location location)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_LOCATION, KEY_TIME + " = ?",
                    new String[] { String.valueOf(location.get_time()) });
            db.close();
        }


        // Deleting single time
        public void deleteTime(Time time) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_TIME, KEY_TIME + " = ?",
                    new String[] { String.valueOf(time.getTime()) });
            db.close();
        }
        // Getting Count
        public int getMerchantCount()
        {
            String countQuery = "SELECT  * FROM " + TABLE_MERCHANT;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }


        public int getLocationCount()
        {
            String countQuery = "SELECT  * FROM " + TABLE_LOCATION;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }

        // Getting Count
        public int getTimeCount()
        {
            String countQuery = "SELECT  * FROM " + TABLE_TIME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }

    }

