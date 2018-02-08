package com.example.rilu_pc.shoppyfy;

/**
 * Created by babur on 02-02-2018.
 */

public class Location
{

    String _lon;
    String _lat;
    String _time;


    public Location()
    {}

    public Location(String time, String lon,String lat)
    {

        this._time = time;
        this._lon = lon;
        this._lat = lat;
    }

    public String get_time() {return this._time;}

    public void setTime(String time) {this._time = time;}

    public String get_lon()
    {
        return this._lon;
    }

    public void setlon(String lon){this._lon = lon;}

    public String get_lat()
    {
        return this._lat;
    }

    public void setlat(String lat){this._lat = lat;}
}


