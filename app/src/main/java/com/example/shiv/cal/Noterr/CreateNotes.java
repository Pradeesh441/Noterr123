package com.example.shiv.cal.Noterr;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateNotes extends AppCompatActivity {

    //private EditText Title;
    private EditText Content;
    public MenuItem Attach;

    private String Note_File;
    //private Note_elements loaded_notes;
    private Notes_cont_stored cont_obtned;
    public String notes_title;
    public String notes_tag;
    public String notes_bg_color;
    public static long id_notes;
    public static DatabaseHelper dbh;
    public String file_name;
    public static final String F_Exn = ".txt";
    public static int MY_PERMISSION_TO_READ = 12;
    public int check_permission;
    public Uri cameraimgUri;
    public static int item_selected;
    public  static final int gallery = 0;
    public static final int audio = 1;
    public static final int camera = 2;
    public File camera_file;
    String attach_file_ary [] = new String[20];
    String attach_ftype_ary [] = new String[20];
    int attach_file_cntr = 0;
    int attach_ftype_cntr = 0;
    private ListView attachmentlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        /////
        check_permission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        /*if(check_permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_TO_READ);
        }else
        {
            attachAudio();
        }*/
        /////
        attachmentlist = (ListView) findViewById(R.id.attachmentslist);




         // to go to Notes home screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbh = new DatabaseHelper(this);

        //Title = (EditText) findViewById(R.id.title);
        Content = (EditText) findViewById(R.id.content);

        Intent createnotes = getIntent();
        notes_title = createnotes.getStringExtra("title");
        notes_tag = createnotes.getStringExtra("tag");
        notes_bg_color = createnotes.getStringExtra("color");
        id_notes = createnotes.getLongExtra("ID",0);
        //Note_File = getIntent().getStringExtra("NOTE FILE");
        Note_File = createnotes.getStringExtra("NOTE FILE");

        if(Note_File != null && !Note_File.isEmpty())
        {
            //loaded_notes = NotesManager.getNoteByName(this,Note_File);
            cont_obtned = obtainSelectedNote(this,Note_File);
            if (cont_obtned != null)
            {
                //Title.setText(loaded_notes.getTitle());
                Content.setText(cont_obtned.getContent());
            }

        }

        //Toast.makeText(this,"Title"+notes_title,Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,"Tag"+notes_tag,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"Bg color"+notes_bg_color,Toast.LENGTH_SHORT).show();
        if (notes_bg_color.equalsIgnoreCase("Yellow"))
            Content.setBackgroundColor(Color.YELLOW);
        else if (notes_bg_color.equalsIgnoreCase("Blue"))
            Content.setBackgroundColor(Color.BLUE);
        else if (notes_bg_color.equalsIgnoreCase("Green"))
            Content.setBackgroundColor(Color.GREEN);

    }
/////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSION_TO_READ)
            if((grantResults.length>0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
            {
                switch (item_selected)
                {
                    case gallery:
                        attachGalleryimage();
                        break;
                    case audio:
                        attachAudio();
                        break;
                    case camera:
                        attachCameraimage();
                        break;

                }
            }
    }
    //////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_create_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
         ///trace

        //changed
        if(item.getItemId() == R.id.save)
            saveNote();
        else if(item.getItemId() == R.id.delete)
            delete_Note();
        else if(item.getItemId() == R.id.picture)
            //attachGalleryimage();
        {
            if(check_permission != PackageManager.PERMISSION_GRANTED)
            {
                item_selected = gallery;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_TO_READ);
            }else
            {
                attachGalleryimage();
            }
        }
        else if(item.getItemId() == R.id.audio)
        {
            //Toast.makeText(this,"Attach an audio",Toast.LENGTH_SHORT).show();
            //attachAudio();
            if(check_permission != PackageManager.PERMISSION_GRANTED)
            {
                item_selected = audio;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_TO_READ);
            }else
            {
                attachAudio();
            }
        }

        else if(item.getItemId() == R.id.prev_attachments) {
           // Toast.makeText(this, "Attach an location", Toast.LENGTH_SHORT).show();
            //if(id_notes != 0)
            //obtainAttachments(id_notes);
            if(id_notes != 0)
            {
                Intent noteattach = new Intent(getApplicationContext(),Notes_Attachments.class);
                noteattach.putExtra("ID",id_notes);
                startActivity(noteattach);
            }


        }
        else if(item.getItemId() == R.id.camera)
            //attachCameraimage();
        {
            if(check_permission != PackageManager.PERMISSION_GRANTED)
            {
                item_selected = camera;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_TO_READ);
            }else
            {
                attachCameraimage();
            }

        }
        else if (item.getItemId() == android.R.id.home)
            finish();




        return true;
    }

    private void attachCameraimage() {
        /*Intent camera_intnt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri cameraImageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"Noterr_"+
                                       String.valueOf(System.currentTimeMillis())+".png"));
        String camera_imageLoc = cameraImageUri.getPath();
        Toast.makeText(this,"Image loc:"+camera_imageLoc,Toast.LENGTH_SHORT).show();
        //camera_intnt.putExtra(MediaStore.EXTRA_OUTPUT,cameraImageUri);
        startActivityForResult(camera_intnt,2);*/

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Camera Image location");
        cameraimgUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        ////////
        Intent camera_intnt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //camera_intnt.putExtra(MediaStore.EXTRA_OUTPUT,cameraimgUri);
        //camera_file = new File(getApplicationContext().getExternalCacheDir(),String.valueOf(dbh.getCurrentdatetime()) + ".jpg");
        //cameraimgUri = Uri.fromFile(camera_file);
        camera_intnt.putExtra(MediaStore.EXTRA_OUTPUT,cameraimgUri);
        startActivityForResult(camera_intnt,camera);
        //Toast.makeText(this,"Temporarily unavailable",Toast.LENGTH_SHORT).show();
        /////

    }

    private void attachGalleryimage() {
        Intent img_intnt = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(img_intnt,gallery);

    }
    private void attachAudio()
    {
        //checkPermission();
        Intent audio_intnt = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(audio_intnt,audio);

    }

    /*private void checkPermission() {
        int check_permission = ContextCompat.checkSelfPermission(getCallingActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(check_permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getCallingActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SyncStateContract.Constants.WRITE_EXTERNAL_STORAGE);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK && data != null)
        {
            Uri imageSelected = data.getData();
            String[] img_filePathcol = {MediaStore.Images.Media.DATA};
            Cursor cur = getContentResolver().query(imageSelected,img_filePathcol,null,null,null);
            cur.moveToFirst();
            int colIndex = cur.getColumnIndex(img_filePathcol[0]);
            String picturePath = cur.getString(colIndex);
            Toast.makeText(this,"Path: "+picturePath,Toast.LENGTH_SHORT).show();
            cur.close();

            attach_file_ary[attach_file_cntr] = picturePath;
            attach_file_cntr = attach_file_cntr + 1;
            attach_ftype_ary[attach_ftype_cntr] = "Image";
            attach_ftype_cntr = attach_ftype_cntr + 1;

            //storeAttachments("Image",picturePath);

        }
        //Not Completed ********
        //if(requestCode == 1 && resultCode == RESULT_OK && data != null)
        if(requestCode == 1 )
        {

            Uri audioSelected = data.getData();
            String[] audio_filePathcol = {MediaStore.Audio.Media.DATA};
            Cursor cur = getContentResolver().query(audioSelected,audio_filePathcol,null,null,null);
            cur.moveToFirst();
            int colIndex = cur.getColumnIndex(audio_filePathcol[0]);
            String audioPath = cur.getString(colIndex);
            Toast.makeText(this,"Path: "+audioPath,Toast.LENGTH_SHORT).show();
            cur.close();
            attach_file_ary[attach_file_cntr] = audioPath;
            attach_file_cntr = attach_file_cntr + 1;
            attach_ftype_ary[attach_ftype_cntr] = "Audio";
            attach_ftype_cntr = attach_ftype_cntr + 1;
            //storeAttachments("Audio",audioPath);

        }
        //Not Completed ********
        //if(requestCode == 2 && resultCode == RESULT_OK && data != null)
        if(requestCode == 2 )
        {
            //Uri imageSelected = data.getData();
            String[] img_filePathcol = {MediaStore.Images.Media.DATA};

            //Cursor cur = getContentResolver().query(imageSelected,img_filePathcol,null,null,null);

            Cursor cur = getContentResolver().query(cameraimgUri,img_filePathcol,null,null,null);
            int colIndex = cur.getColumnIndex(MediaStore.Images.Media.DATA);
            cur.moveToLast();
            String picturePath = cur.getString(colIndex);
            Toast.makeText(this,"Path: "+picturePath,Toast.LENGTH_SHORT).show();
            cur.close();
            attach_file_ary[attach_file_cntr] = picturePath;
            attach_file_cntr = attach_file_cntr + 1;
            attach_ftype_ary[attach_ftype_cntr] = "Image";
            attach_ftype_cntr = attach_ftype_cntr + 1;

            //storeAttachments("Image",picturePath);

        }
    }



    private void saveNote()
    {
        String save_success;
        String noteFileName = null;
        //Note_elements note_elm;
        Notes_cont_stored notes_cont_stored;

        /*if(Title.getText().toString().trim().isEmpty() || Content.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Cannot save! Title or content is empty.",Toast.LENGTH_SHORT).show();
            return;
        }*/
        if(cont_obtned == null) {
            //note_elm = new Note_elements(System.currentTimeMillis(), Title.getText().toString(), Content.getText().toString());
            notes_cont_stored = new Notes_cont_stored(Content.getText().toString());


        }
        else
        {
            //note_elm = new Note_elements(loaded_notes.getDatetime(), Title.getText().toString(), Content.getText().toString());
            notes_cont_stored = new Notes_cont_stored(Content.getText().toString());
            noteFileName = Note_File;

        }

        //String result[] = NotesManager.save(this,note_elm);
        //String result[] = NotesManager.save(this,notes_cont_stored,noteFileName);
        String result[] = save(this,notes_cont_stored,noteFileName);


        /*if(NotesManager.save(this,note_elm))
        {
            //changed message in toast
            Toast.makeText(this,Title.getText()+ " notes saved!",Toast.LENGTH_SHORT).show();
            //file_name = note_elm.getNotes_file_saved();
            //Toast.makeText(this, " File " + file_name,Toast.LENGTH_SHORT).show();
            saveNotestoDB();

        }
        else
        {
            //chnaged message in toast
            Toast.makeText(this,"Sorry,notes was not saved. Free up some space!",Toast.LENGTH_SHORT).show();
        }*/

        if (result[0] == "S")
        {
            //Toast.makeText(this,Title.getText()+ " notes saved!",Toast.LENGTH_SHORT).show();
            Toast.makeText(this,notes_title+ " notes saved!",Toast.LENGTH_SHORT).show();
            saveNotestoDB(result[1]);

        } else if (result[0] == "U") {
            Toast.makeText(this, "Notes updated !", Toast.LENGTH_SHORT).show();

            Notes_content insertRecordContent = new Notes_content();
            long content_id = 0;
            int ary_length = attach_file_cntr - 1;

            for(int i = 0;i<=ary_length;i++)
            {
                insertRecordContent.setID(id_notes);
                insertRecordContent.setCont_type(attach_ftype_ary[i]);
                insertRecordContent.setContent(attach_file_ary[i]);

                content_id = dbh.createNotes_content(insertRecordContent);

            }
            if (content_id != 0)
                Toast.makeText(this, "Sorry,notes was updated!", Toast.LENGTH_SHORT).show();


        } else
            {
            Toast.makeText(this, "Sorry,notes was not saved!", Toast.LENGTH_SHORT).show();

        }



        finish();// go back to the last activity

    }
    private void delete_Note() {
              /////trace

        Notes_main notes_main = new Notes_main();
        if(cont_obtned == null)
            finish();
        else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Delete Notes")
                    .setMessage("Do you really want to delete '"+notes_title+"' ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //NotesManager.deleteNote(getApplicationContext(), Note_File);
                            deleteNote(getApplicationContext(), Note_File);
                            Toast.makeText(getApplicationContext(),"Notes '" +notes_title+"' deleted",Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    }).setCancelable(false);
            dialog.show();

            /*notes_main.setID(id);

            dbh.deleteNotes_main(notes_main);*/

        }
    }
    private void saveNotestoDB(String file)
    {
        long id;

        if(file == " ")
        {
            Toast.makeText(this,"File was not saved properly",Toast.LENGTH_SHORT).show();
            return;

        }


        Notes_main insertRecordMain = new Notes_main();
        insertRecordMain.setDesc(notes_title);
        insertRecordMain.setTag(notes_tag);
        insertRecordMain.setBg_color(notes_bg_color);

        id = dbh.createNotes_main(insertRecordMain);

        //int notes_id = (int) id;
        long notes_id = id;

        /*ArrayList<Notes_main> nts_mn = new ArrayList<Notes_main>();
        nts_mn = dbh.dummy(id);

        Toast.makeText(this,"Desc"+nts_mn.get(0).getDesc(),Toast.LENGTH_SHORT).show();*/


        //ArrayList<Notes_main> output3 = dbh.RetrieveNotes_main();


        Notes_content insertRecordContent = new Notes_content();
        insertRecordContent.setID(notes_id);
        insertRecordContent.setCont_type("Text");
        insertRecordContent.setContent(file);

        long content_id = dbh.createNotes_content(insertRecordContent);

        if(content_id != 0)
        {
            Toast.makeText(this,"File "+file+" saved successfully",Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,"Content id "+content_id+"",Toast.LENGTH_SHORT).show();

        }
        else
            Toast.makeText(this,"Sorry Note "+file+" not successfull",Toast.LENGTH_SHORT).show();

        int ary_length = attach_file_cntr - 1;

        for(int i = 0;i<=ary_length;i++)
        {
            insertRecordContent.setID(notes_id);
            insertRecordContent.setCont_type(attach_ftype_ary[i]);
            insertRecordContent.setContent(attach_file_ary[i]);

            content_id = dbh.createNotes_content(insertRecordContent);

        }




        //ArrayList<Notes_content> output4 = dbh.RetrieveNotes_content(insertRecordContent);
    }
    /*public void storeAttachments(String type, String file)
    {
        Notes_content insertRecordContent = new Notes_content();
        insertRecordContent.setID(id);
        insertRecordContent.setCont_type(type);
        insertRecordContent.setContent(file);

        long content_id = dbh.createNotes_content(insertRecordContent);

        if(content_id != 0)
        {
            Toast.makeText(this,"Attachment successfull",Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,"Content id "+content_id+"",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Sorry, Attachment not successfull.Try again!",Toast.LENGTH_SHORT).show();



    }*/
    public static String[] save(Context context, Notes_cont_stored note,String existing_file)
    {
        String success = "S";
        String failure = "F";
        String update = "U";
        String result;
        String no_file = " ";
        String file_name;


        //String file_name = String.valueOf(note.getDatetime()) +F_Exn;
        dbh = new DatabaseHelper(context);
        if (existing_file == null) {
            file_name = dbh.getCurrentdatetime() + F_Exn;
            result = success;
        }
        else {
            file_name = existing_file;
            result = update;
        }


        FileOutputStream file_out;
        ObjectOutputStream obj_out;

        try{
            file_out = context.openFileOutput(file_name, context.MODE_PRIVATE);
            obj_out = new ObjectOutputStream(file_out);
            obj_out.writeObject(note);
            obj_out.close();
            file_out.close();
            //note.setNotes_file_saved(file_name);


        }catch (IOException e)
        {
            e.printStackTrace();
            // return false;
            return new String[] {failure,no_file};

        }


        //return true;
        return new String[] {result,file_name};

    }
    public static Notes_cont_stored obtainSelectedNote(Context context, String file_name)
    {
        File file = new File(context.getFilesDir(),file_name);
        //Note_elements note_elm;
        Notes_cont_stored notes_content;

        if(file.exists())
        {
            FileInputStream file_inp;
            ObjectInputStream obj_inp;

            try
            {
                file_inp = context.openFileInput(file_name);
                obj_inp = new ObjectInputStream(file_inp);

                //note_elm = (Note_elements) obj_inp.readObject();
                notes_content = (Notes_cont_stored) obj_inp.readObject();

                file_inp.close();
                obj_inp.close();

            } catch(IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
                return null;
            }
            //return note_elm;
            return notes_content;

        }

        return  null;

    }

    public static void deleteNote(Context context, String file_name) {

        Notes_main notes_main = new Notes_main();

        File f_dir = context.getFilesDir();
        File file = new File(f_dir,file_name);
        if(file.exists())
            file.delete();
        notes_main.setID(id_notes);

        dbh.deleteNotes_main(notes_main);
    }


    /*public void obtainAttachments(long id_notes)
    {
        Notes_content notes_content = new Notes_content();
        notes_content.setID(id_notes);

        ArrayList<Notes_content> notes_items = dbh.RetrieveNotes_content(notes_content);

        if( notes_items == null)
        {
            Toast.makeText(this,"No notes available.Please create a new note!",Toast.LENGTH_SHORT).show();
            return;

        }
        else
        {
            Notes_attachment_list_adapter notesattachadapter = new Notes_attachment_list_adapter(this, R.layout.activity_notes_attachments,notes_items);
            attachmentlist.setAdapter(notesattachadapter);

            attachmentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String item_location = ((Notes_content)attachmentlist.getItemAtPosition(i)).getContent();

                    open_media(item_location);
                    return;

                }
            });





        }
    }
    public void open_media(String media_location)
    {
        Toast.makeText(this,"Item location "+media_location,Toast.LENGTH_SHORT).show();
        return;

    }*/

}
