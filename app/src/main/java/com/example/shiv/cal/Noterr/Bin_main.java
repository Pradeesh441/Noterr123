package com.example.shiv.cal.Noterr;

import java.util.Date;

/**
 * Created by apple on 05/11/17.
 */

public class Bin_main {
    private Integer ID;
    private Integer Notes_ID;
    private Date Date_crtd;
    private String Desc;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getNotes_ID() {
        return Notes_ID;
    }

    public void setNotes_ID(Integer notes_ID) {
        Notes_ID = notes_ID;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Date getDate_crtd() {
        return Date_crtd;
    }

    public void setDate_crtd(Date date_crtd) {
        Date_crtd = date_crtd;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getBg_color() {
        return Bg_color;
    }

    public void setBg_color(String bg_color) {
        Bg_color = bg_color;
    }

    public Date getDate_deltd() {
        return Date_deltd;
    }

    public void setDate_deltd(Date date_deltd) {
        Date_deltd = date_deltd;
    }

    private String Tag;
    private String Bg_color;
    private Date Date_deltd;
}
