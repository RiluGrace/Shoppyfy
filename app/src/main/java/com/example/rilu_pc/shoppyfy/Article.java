package com.example.rilu_pc.shoppyfy;

/**
 * Created by babur on 17-01-2018.
 */

public class Article
{
    int id;
    String header;
    String description;

    public Article()
    {

    }

    public Article( int id, String header, String description)
    {
        super();
        this.id=id;
        this.header = header;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader()


    {
        return header;
    }

    public void setHeader(String header)

    {
        this.header = header;
    }

    public String getDescription()

    {
        return description;
    }

    public void setDescription(String description)


    {
        this.description= description;
    }

}
