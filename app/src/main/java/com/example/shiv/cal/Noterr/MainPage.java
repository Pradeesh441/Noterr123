package com.example.shiv.cal.Noterr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiv.cal.EventsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/*created by chaitanya on 2017-11-14*/

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView eventsdaylist;
    private ListView notesdaylist;
    // private DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
    private DatabaseHelper dbhelper;
    ArrayList<Cal_sched> event_items;
    Cal_shed_retrival cal_events = new Cal_shed_retrival();
    ArrayList<Notes_main> notes_items;
    Notes_main_date notes_input = new Notes_main_date();
    String StartDate, Enddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int HHs = 00, MMs = 0, SSs = 0, HHf = 23, MMf = 59, SSf = 59;

        TextView set = (TextView) findViewById(R.id.Noterr);

        dbhelper = new DatabaseHelper(getApplicationContext());
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR, HHs);
        c.set(Calendar.MINUTE, MMs);
        c.set(Calendar.SECOND, SSs);
        StartDate = dt.format(c.getTime());       //Date for retrival


        c.set(Calendar.HOUR, HHf);
        c.set(Calendar.MINUTE, MMf);
        c.set(Calendar.SECOND, SSf);
        Enddate = dt.format(c.getTime());     //Date for retrival


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // Toast.makeText(getApplicationContext(),StartDate+Enddate,Toast.LENGTH_SHORT).show();

        eventsdaylist = (ListView) findViewById(R.id.listView2);
        notesdaylist = (ListView) findViewById(R.id.listView);
        try {
            cal_events.setStart_Datetime(dbhelper.getDateTime(StartDate));
            notes_input.setStart_date(dbhelper.getDateTime(StartDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            cal_events.setEnd_Datetime(dbhelper.getDateTime(Enddate));
            notes_input.setEnd_date(dbhelper.getDateTime(Enddate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        notes_items = dbhelper.RetrieveNotes_main_date(notes_input);
        event_items = dbhelper.RetrieveCal_sched(cal_events);


        if (event_items == null) {
            Toast.makeText(getBaseContext(), "No Events available.Please create a new event!", Toast.LENGTH_SHORT).show();


        } else {
            EventsAdapter eventsadapter = new EventsAdapter(getBaseContext(), R.layout.event_items, event_items);
            eventsdaylist.setAdapter(eventsadapter);
            Toast.makeText(getBaseContext(), "Events for the day", Toast.LENGTH_SHORT).show();
            eventsdaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    String name = ((Cal_sched) eventsdaylist.getItemAtPosition(i)).getName();
                    String datetime = dbhelper.getDateTime(((Cal_sched) eventsdaylist.getItemAtPosition(i)).getDate_time());
                    String address = ((Cal_sched) eventsdaylist.getItemAtPosition(i)).getVenue();

                    Intent intent = new Intent(getApplicationContext(), EventsView.class);
                    intent.putExtra("Name", name);
                    intent.putExtra("Date/Time", datetime);
                    intent.putExtra("Address", address);
                    startActivity(intent);
                }
            });


        }


        if (notes_items == null) {
            Toast.makeText(getBaseContext(), "No notes available.Please create a new note!", Toast.LENGTH_SHORT).show();


        } else {
            Notes_Adapter notesadapter = new Notes_Adapter(getBaseContext(), R.layout.notes_item, notes_items);
            notesdaylist.setAdapter(notesadapter);
            Toast.makeText(getBaseContext(), "Notes for the day", Toast.LENGTH_SHORT).show();
            notesdaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    long identity = ((Notes_main)notesdaylist.getItemAtPosition(i)).getID();
                    String title = ((Notes_main)notesdaylist.getItemAtPosition(i)).getDesc();
                    String tag = ((Notes_main)notesdaylist.getItemAtPosition(i)).getTag();
                    String bg_color = ((Notes_main)notesdaylist.getItemAtPosition(i)).getBg_color();

                    Notes_content nts_cont = new Notes_content();
                    nts_cont.setID(identity);

                    ArrayList<Notes_content> notes_cont = dbhelper.RetrieveNotes_content(nts_cont);

                    String file = notes_cont.get(0).getContent();

                    // On selecting a note, call the next page where the corresponding content will be displayed. To open a next page with corresponding
                    // details , the actual details of the selected notes are sent to the page
                    Intent vwnote = new Intent(getApplicationContext(),CreateNotes.class);
                    vwnote.putExtra("ID",identity);
                    vwnote.putExtra("title",title);
                    vwnote.putExtra("tag",tag);
                    vwnote.putExtra("color",bg_color);
                    vwnote.putExtra("NOTE FILE",file);
                    startActivity(vwnote);
                }
            });


        }






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calander) {
            // Handle the  action
            Intent i = new Intent(this, Calendar_view.class);
            startActivity(i);




        } else if (id == R.id.nav_todo) {
            Intent i = new Intent(getApplicationContext(), Todo_eminent.class);
            startActivity(i);

        } else if (id == R.id.nav_notes) {
            Intent noteshome = new Intent(this,Noteshome.class);
            startActivity(noteshome);

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_info) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
