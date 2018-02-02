package com.example.rilu_pc.shoppyfy;

/**
 * Created by babur on 02-02-2018.
 */

public class Location
{

    String _loc;
    String _time;

    public Location(String _loc)
    {

    }
    public Location()
    {}

    public Location(String time, String loc)
    {

        this._time = time;
        this._loc = loc;


    }



    public String get_time()
    {
        return this._time;
    }

    public void setTime(String time)
    {
        this._time = time;
    }

    public String get_loc()
    {
        return this._loc;
    }

    public void setloc(String loc)
    {
        this._loc = loc;
    }

}


