package com.example.shiv.cal.Noterr;

import java.util.Date;

/**
 * Created by apple on 05/11/17.
 */

public class Cal_sched {
    private Integer ID;
    private String Name;
    private Date Date_time;


    public Date getDate_time() {
        return Date_time;
    }

    public void setDate_time(Date date_time) {
        Date_time = date_time;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public String getLoc() {
        return Loc;
    }

    public void setLoc(String loc) {
        Loc = loc;
    }

    public Date getDate_crtd() {
        return Date_crtd;
    }

    public void setDate_crtd(Date date_crtd) {
        Date_crtd = date_crtd;
    }

    private String Venue;
    private String Loc;
    private Date Date_crtd;
}
