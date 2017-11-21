package com.example.shiv.cal.Noterr;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by apple on 10/11/17.
 */

//**************To be removed************************

public class NotesManager {

    public static final String F_Exn = ".txt";
    public static DatabaseHelper dbh;

    //public static boolean save(Context context, Note_elements note)
    //public static String[] save(Context context, Note_elements note)
    /*public static String[] save(Context context, Notes_cont_stored note,String existing_file)
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

    }*/

    //This can be removed, since v r goin to populate the list of notes from the db
    public static ArrayList<Note_elements> Retreive_all_notes(Context context)
    {
        ArrayList<Note_elements> notes = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> notes_list = new ArrayList<>();

        for(String files : filesDir.list())
        {
            if(files.endsWith(F_Exn))
            {
                notes_list.add(files);
            }
        }
        FileInputStream file_inp;
        ObjectInputStream obj_inp;

        for (int i =0;i<notes_list.size();i++)
        {
            try
            {
                file_inp = context.openFileInput(notes_list.get(i));
                obj_inp = new ObjectInputStream(file_inp);

                notes.add((Note_elements) obj_inp.readObject());

                file_inp.close();
                obj_inp.close();

            }catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
                //return null;
            }
        }

        return notes;
    }
    //public static Note_elements getNoteByName(Context context,String file_name)
    /*public static Notes_cont_stored getNoteByName(Context context,String file_name)
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

    }*/

    /*public static void deleteNote(Context context, String file_name) {
        File f_dir = context.getFilesDir();
        File file = new File(f_dir,file_name);
        if(file.exists())
            file.delete();
    }*/
}

