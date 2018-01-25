package com.example.rilu_pc.shoppyfy;

/**
 * Created by Rilu-PC on 10-01-2018.
 */

public class Merchant
{
    String imageurl;
    String _name;
    String _location;
    String _business_type;

    public Merchant(String name, String loc, String type)
    {

    }
    public Merchant()
    {}

    public Merchant(String imageurl, String name, String location,String business_type)
    {
        this. imageurl=imageurl;
        this._name = name;
        this._location = location;
        this._business_type= business_type;

    }


    public String getImageurl()
    {
        return this.imageurl;
    }

    public void setImageurl(String imageurl)
    {
        this.imageurl = imageurl;
    }

    public String getName()
    {
        return this._name;
    }

    public void setName(String name)
    {
        this._name = name;
    }

    public String get_location()
    {
        return this._location;
    }

    public void setlocation(String location)
    {
        this._location = location;
    }
    public String get_business_type()
    {
        return this._business_type;
    }

    public void setbusinesstype(String _business_type){
        this._business_type = _business_type;
    }
}

