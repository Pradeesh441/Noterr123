package com.example.shiv.cal.Noterr;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Notes_Attachments extends AppCompatActivity {

    private ListView attachmentlist;
    public static DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_attachments);

        attachmentlist = (ListView) findViewById(R.id.attachmentslist);
        dbh = new DatabaseHelper(this);

       // opens in pdf mode - https://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed

        /*StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());*/




        Intent notesattach = getIntent();
        long notes_id = notesattach.getLongExtra("ID",0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        Notes_content notes_content = new Notes_content();
        notes_content.setID(notes_id);

        ArrayList<Notes_content> notes_items = dbh.RetrieveNotes_content(notes_content);
        ArrayList<Notes_content> mediafilter = new ArrayList<Notes_content>();
        ////
        for (int i = 0;i<notes_items.size();i++)
        {
            if(!notes_items.get(i).getCont_type().equalsIgnoreCase("Text"))
            {
                mediafilter.add(notes_items.get(i));

            }

        }
        ////

        //if( notes_items == null)
        if( mediafilter == null)
        {
            Toast.makeText(this,"No notes available.Please create a new note!",Toast.LENGTH_SHORT).show();
            return;

        }
        else
        {
            //Notes_attachment_list_adapter notesattachadapter = new Notes_attachment_list_adapter(this, R.layout.activity_notes_attachments,notes_items);
            Notes_attachment_list_adapter notesattachadapter = new Notes_attachment_list_adapter(this, R.layout.activity_notes_attachments,mediafilter);
            attachmentlist.setAdapter(notesattachadapter);

            attachmentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String item_location = ((Notes_content)attachmentlist.getItemAtPosition(i)).getContent();
                    String type = ((Notes_content)attachmentlist.getItemAtPosition(i)).getCont_type();

                    open_media(item_location,type);
                    return;

                }
            });






        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return  true;
    }

    public void open_media(String media_location,String type)
    {
        File file = new File(media_location);
        //Toast.makeText(this,"Item location "+media_location,Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent();
        //intent.setAction(Intent.ACTION_VIEW);
        //intent.setDataAndType(Uri.fromFile(new File(media_location)),"image/*");
        //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //startActivity(intent);
        /*startActivity(new Intent(Intent.ACTION_VIEW,Uri.fromFile(new File(media_location))));*/
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri mediaUri = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID+".provider",file);
        //intent.setDataAndType(Uri.fromFile(new File(media_location)),"image*//*");
        if(type.equalsIgnoreCase("Image"))
            intent.setDataAndType(mediaUri,"image/*");
        else if(type.equalsIgnoreCase("Audio"))
            intent.setDataAndType(mediaUri,"audio/*");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);


        return;

    }
}
                                