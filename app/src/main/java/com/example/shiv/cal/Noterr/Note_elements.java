package com.example.shiv.cal.Noterr;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by apple on 10/11/17.
 */

public class Note_elements implements Serializable {
    private String Title;
    private String Content;
    private long Datetime;




    public Note_elements(long datetime, String title, String content) {
        Datetime = datetime;
        Title = title;
        Content = content;
    }
    public long getDatetime() {
        return Datetime;
    }

    public void setDatetime(long datetime) {
        Datetime = datetime;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


    public String getCurrentDateTime(Context contxt)
    {
        SimpleDateFormat dttmfrmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",contxt.getResources().getConfiguration().locale);
        dttmfrmt.setTimeZone(TimeZone.getDefault());
        return dttmfrmt.format(new Date(Datetime));
    }




}
