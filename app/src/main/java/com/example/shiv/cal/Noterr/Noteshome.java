package com.example.shiv.cal.Noterr;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Noteshome extends AppCompatActivity {


    private ListView noteslist;
    public String bg_color;
    private DatabaseHelper dbhelper = new DatabaseHelper(this);
    //public String temp_fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteshome);

        noteslist = (ListView) findViewById(R.id.noteslist);


        FloatingActionButton float_create = (FloatingActionButton) findViewById(R.id.notes_create);
        float_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = (LayoutInflater.from(Noteshome.this)).inflate(R.layout.notes_title_create,null);
                final TextView title_hdr = (TextView) view.findViewById(R.id.create_hdr);
                final EditText notes_title = (EditText) view.findViewById(R.id.notes_title);
                final EditText notes_tag = (EditText) view.findViewById(R.id.notes_tag);
                final Spinner colorBGSpinner = (Spinner) view.findViewById(R.id.color_bg_spinner);
                final ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.color_list,android.R.layout.simple_spinner_item);
                spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                colorBGSpinner.setAdapter(spin_adapter);
                colorBGSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        bg_color = colorBGSpinner.getItemAtPosition(i).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                AlertDialog.Builder title_inp_getter = new AlertDialog.Builder(Noteshome.this);
                title_inp_getter.setView(view);
                title_inp_getter.setCancelable(true).setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        create_newnote(notes_title.getText().toString(),notes_tag.getText().toString(),bg_color);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                Dialog dlg = title_inp_getter.create();
                dlg.show();



            }
        });
    }




    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_main_options, menu);
        return true;

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //changed
        if(item.getItemId() == R.id.create_note)
        {
            Intent createnewnote = new Intent(this,CreateNotes.class);
            startActivity(createnewnote);

        }
        return true;
    }

   /******change it totally **********/
    @Override
    protected void onResume() {
        super.onResume();
        noteslist.setAdapter(null);

        /*ArrayList<Note_elements> all_nts = NotesManager.Retreive_all_notes(this);

        if(all_nts == null || all_nts.size() == 0)
        {
            Toast.makeText(this,"No notes available. Create one",Toast.LENGTH_SHORT).show();
            return;
        } else
        {
            Notes_Adapter noteadapter = new Notes_Adapter(this, R.layout.notes_item,all_nts);
            noteslist.setAdapter(noteadapter);

            noteslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String file = ((Note_elements)noteslist.getItemAtPosition(i)).getDatetime() + NotesManager.F_Exn;

                    Intent vwnote = new Intent(getApplicationContext(),CreateNotes.class);
                    vwnote.putExtra("NOTE FILE",file);
                    startActivity(vwnote);
                }
            });

        }*/



        ArrayList<Notes_main> notes_items = dbhelper.RetrieveNotes_main();

        if( notes_items == null)
        {
            Toast.makeText(this,"No notes available.Please create a new note!",Toast.LENGTH_SHORT).show();
            return;

        }
        else
        {
            Notes_Adapter noteadapter = new Notes_Adapter(this, R.layout.notes_item,notes_items);
            noteslist.setAdapter(noteadapter);

            noteslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    /*String file = ((Note_elements)noteslist.getItemAtPosition(i)).getDatetime() + NotesManager.F_Exn;

                    Intent vwnote = new Intent(getApplicationContext(),CreateNotes.class);
                    vwnote.putExtra("NOTE FILE",file);
                    startActivity(vwnote);*/

                    //int id = ((Notes_main)noteslist.getItemAtPosition(i)).getID();
                    long id = ((Notes_main)noteslist.getItemAtPosition(i)).getID();
                    String title = ((Notes_main)noteslist.getItemAtPosition(i)).getDesc();
                    String tag = ((Notes_main)noteslist.getItemAtPosition(i)).getTag();
                    String bg_color = ((Notes_main)noteslist.getItemAtPosition(i)).getBg_color();

                    Notes_content nts_cont = new Notes_content();
                    nts_cont.setID(id);

                    ArrayList<Notes_content> notes_cont = dbhelper.RetrieveNotes_content(nts_cont);

                    String file = notes_cont.get(0).getContent();

                    //temp_fileName = file;

                    //dummyCall();
                    Intent vwnote = new Intent(getApplicationContext(),CreateNotes.class);
                    //vwnote.putExtra("NOTE FILE",file);
                    vwnote.putExtra("ID",id);
                    vwnote.putExtra("title",title);
                    vwnote.putExtra("tag",tag);
                    vwnote.putExtra("color",bg_color);
                    vwnote.putExtra("NOTE FILE",file);
                    startActivity(vwnote);


                    return;

                }
            });





        }



    }

    public void create_newnote(String title,String tag,String color)
    {
        Intent createnewnote = new Intent(this,CreateNotes.class);
        createnewnote.putExtra("title",title);
        createnewnote.putExtra("tag",tag);
        createnewnote.putExtra("color",color);

        startActivity(createnewnote);
    }
    /*public void dummyCall()
    {
        Toast.makeText(this,"File: "+ temp_fileName,Toast.LENGTH_SHORT).show();

    }*/




}
