package com.example.shiv.cal.Noterr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Shiv on 2017-11-08.
 */

public class Day_View_To_dos extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_view_to_dos, container, false);

        Bundle bundle=getArguments();
        String Date= (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");

        TextView date =(TextView) rootView.findViewById(R.id.Datetext);

        date.setText(Date+"/"+Month+"/"+Year);

        return rootView;
    }
}

