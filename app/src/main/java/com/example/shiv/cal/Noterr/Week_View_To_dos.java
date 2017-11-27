package com.example.shiv.cal.Noterr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Shiv on 2017-11-08.
 */

public class Week_View_To_dos extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.week_view_to_dos, container, false);
        Bundle bundle=getArguments();
        String Date= (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");

        int HHs=0,MMs=0,SSs=0,HHf=23,MMf=59,SSf=59;
        String StartDate, Enddate;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TextView datetext =(TextView) rootView.findViewById(R.id.Weektext);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, Integer.parseInt(Date));
        c.set(Calendar.MONTH,Integer.parseInt(Month));
        c.set(Calendar.YEAR, Integer.parseInt(Year));
        c.set(Calendar.HOUR,HHs);
        c.set(Calendar.MINUTE,MMs);
        c.set(Calendar.SECOND,SSs);
        c.add(Calendar.DATE,-4);
        datetext.append(sdf.format(c.getTime()));
        StartDate =dt.format(c.getTime());  //Date for retrival
        c.add(Calendar.DATE,7);
        c.set(Calendar.HOUR,HHf);
        c.set(Calendar.MINUTE,MMf);
        c.set(Calendar.SECOND,SSf);
        datetext.append(" To "+sdf.format(c.getTime()));
        Enddate =dt.format(c.getTime());    //Date for retrival
        return rootView;
    }
}

