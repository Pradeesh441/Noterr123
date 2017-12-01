package com.example.shiv.cal.Noterr;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

public class Week_View_To_dos extends Fragment {


    private ListView todoweeklist;
    // private DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
    private DatabaseHelper dbhelper;
    ArrayList<Todo_main> todo_items;
    Notes_main_date todo_input = new Notes_main_date();
    //Todo_main todo_data = new Todo_main();
    String StartDate, Enddate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.week_view_to_dos, container, false);

        Context context = getActivity();
        dbhelper = new DatabaseHelper(context);

        Bundle bundle=getArguments();
        String Date= (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");

        int HHs=0,MMs=0,SSs=0,HHf=23,MMf=59,SSf=59;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity();

        todoweeklist = (ListView) getActivity().findViewById(R.id.todoweekview);
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

        if (todo_items.isEmpty()) {
            Toast.makeText(getContext(), "No Todo's Created!", Toast.LENGTH_SHORT).show();


        } else {
            //itemsAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, todo_items);
            To_do_Adapter to_do_adapter = new To_do_Adapter(context,android.R.layout.simple_list_item_1,todo_items);
            todoweeklist.setAdapter(to_do_adapter);
           // Toast.makeText(getContext(), "Events for the day", Toast.LENGTH_SHORT).show();
            todoweeklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

