package com.example.shiv.cal.Noterr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Shiv on 2017-11-08.
 */

public class Day_View_To_dos extends Fragment {

    private ListView tododaylist;
    // private DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
    private DatabaseHelper dbhelper;
    ArrayList<Todo_main> todo_items;
    Notes_main_date todo_input = new Notes_main_date();
    //Todo_main todo_data = new Todo_main();
    String StartDate, Enddate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_view_to_dos, container, false);

        Context context = getActivity();
        dbhelper = new DatabaseHelper(context);

        Bundle bundle=getArguments();
        String Date= (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int HHs=0,MMs=0,SSs=0,HHf=23,MMf=59,SSf=59;
        int month =Integer.parseInt(Month);
        TextView date =(TextView) rootView.findViewById(R.id.Datetext);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, Integer.parseInt(Date));
        c.set(Calendar.MONTH,Integer.parseInt(Month));
        c.set(Calendar.YEAR, Integer.parseInt(Year));
        c.set(Calendar.HOUR,HHs);
        c.set(Calendar.MINUTE,MMs);
        c.set(Calendar.SECOND,SSs);
        StartDate =dt.format(c.getTime());       //Date for retrival


        c.set(Calendar.HOUR,HHf);
        c.set(Calendar.MINUTE,MMf);
        c.set(Calendar.SECOND,SSf);
        Enddate =dt.format(c.getTime());      //Date for retrival
        month++;
        date.setText(Date+"/"+month+"/"+Year);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity();

        tododaylist = (ListView) getActivity().findViewById(R.id.tododayview);
        try {
            todo_input.setStart_date(dbhelper.getDateTime(StartDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            todo_input.setEnd_date(dbhelper.getDateTime(Enddate));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        todo_items = dbhelper.RetrieveTodo_main_date(todo_input);

        if (todo_items == null) {
            Toast.makeText(getContext(), "No notes available.Please create a new note!", Toast.LENGTH_SHORT).show();


        } else {
            //itemsAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, todo_items);
            To_do_Adapter to_do_adapter = new To_do_Adapter(context,android.R.layout.simple_list_item_1,todo_items);
            tododaylist.setAdapter(to_do_adapter);
            Toast.makeText(getContext(), "Events for the day", Toast.LENGTH_SHORT).show();
            tododaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                    Intent todoview = new Intent(getActivity(),Todo_ContentView.class);
                    todoview.putExtra("theId",String.valueOf(i));

                    startActivity(todoview);

                    return;

                }
            });



            return;
        }

    }
}

