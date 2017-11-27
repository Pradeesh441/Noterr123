package com.example.shiv.cal.Noterr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_view);

        TextView name = (TextView)findViewById(R.id.name);
        TextView datetime = (TextView)findViewById(R.id.textView8);
        TextView address = (TextView)findViewById(R.id.textView9);

        Intent i = getIntent();
        String Name = i.getStringExtra("Name");
        String Datetime = i.getStringExtra("Date/Time");
        String Address = i.getStringExtra("Address");
         name.setText(Name);
         datetime.setText(Datetime);
         address.setText(Address);
    }
}
