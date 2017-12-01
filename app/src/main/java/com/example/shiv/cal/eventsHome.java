package com.example.shiv.cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shiv.cal.Noterr.Cal_sched;
import com.example.shiv.cal.Noterr.DatabaseHelper;
import com.example.shiv.cal.Noterr.EventsView;
import com.example.shiv.cal.Noterr.R;

import java.util.ArrayList;

public class eventsHome extends AppCompatActivity {

    private ListView eventslist;
    private DatabaseHelper dbhelper = new DatabaseHelper(this);
    ArrayList<Cal_sched> event_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_home);

        eventslist = (ListView) findViewById(R.id.eventshomelist);
        event_items = dbhelper.dummycalshed();


        if (event_items == null) {
            Toast.makeText(this, "No notes available.Please create a new note!", Toast.LENGTH_SHORT).show();
            return;

        } else {
            EventsAdapter eventsadapter = new EventsAdapter(this, R.layout.notes_item, event_items);
            eventslist.setAdapter(eventsadapter);

            eventslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    /*String file = ((Note_elements)noteslist.getItemAtPosition(i)).getDatetime() + NotesManager.F_Exn;

                    Intent vwnote = new Intent(getApplicationContext(),CreateNotes.class);
                    vwnote.putExtra("NOTE FILE",file);
                    startActivity(vwnote);*/

                    //int id = ((Notes_main)noteslist.getItemAtPosition(i)).getID();
                    long id = ((Cal_sched)eventslist.getItemAtPosition(i)).getID();
                    String name = ((Cal_sched) eventslist.getItemAtPosition(i)).getName();
                    String datetime = dbhelper.getDateTime(((Cal_sched) eventslist.getItemAtPosition(i)).getDate_time());
                    String address = ((Cal_sched) eventslist.getItemAtPosition(i)).getVenue();


                    Intent intent = new Intent(getApplicationContext(), EventsView.class);
                    //vwnote.putExtra("NOTE FILE",file)
                    intent.putExtra("ID", id);
                    intent.putExtra("Name", name);
                    intent.putExtra("Date/Time", datetime);
                    intent.putExtra("Address", address);
                    startActivity(intent);


                    return;

                }
            });


        }
    }
}
