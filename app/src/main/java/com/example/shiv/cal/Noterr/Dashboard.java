package com.example.shiv.cal.Noterr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Dashboard extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button notes = (Button) findViewById(R.id.Notes);


        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dbhelper.dummy_delete_notes();
                notesHome();

            }
        });

    }

    public void notesHome()
    {
        Intent noteshome = new Intent(this,Noteshome.class);
        startActivity(noteshome);
    }

    public void Click(View view){
        Intent i = new Intent(this, Calendar_view.class);
        startActivity(i);
    }
}
