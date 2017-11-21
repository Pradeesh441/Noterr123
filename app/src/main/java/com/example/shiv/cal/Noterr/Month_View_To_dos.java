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

public class Month_View_To_dos extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.month_view_to_dos, container, false);
        Bundle bundle=getArguments();
        String Date= (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");
        int m = Integer.parseInt(Month);

        TextView month =(TextView) rootView.findViewById(R.id.Monthtext);

        switch(m){
            case 1:
                month.setText("January");
                break;
            case 2:
                month.setText("Febraury");
                break;
            case 3:
                month.setText("March");
                break;
            case 4:
                month.setText("April");
                break;
            case 5:
                month.setText("May");
                break;
            case 6:
                month.setText("June");
                break;
            case 7:
                month.setText("July");
                break;
            case 8:
                month.setText("August");
                break;
            case 9:
                month.setText("September");
                break;
            case 10:
                month.setText("October");
                break;
            case 11:
                month.setText("November");
                break;
            case 12:
                month.setText("December");
                break;
        }
        return rootView;
    }
}

