package com.example.shiv.cal.Noterr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by apple on 11/11/17.
 */

/*
public class Notes_Adapter extends ArrayAdapter<Note_elements>{
    public Notes_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Note_elements> notes) {
        super(context, resource, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_item,null);
        }

        Note_elements note_elm = getItem(position);

        if (note_elm != null)
        {
            TextView title = (TextView) convertView.findViewById(R.id.item_title);
            TextView datetime = (TextView) convertView.findViewById(R.id.item_datetime);
            TextView tag = (TextView) convertView.findViewById(R.id.item_tag);

            title.setText(note_elm.getTitle());
            datetime.setText(note_elm.getCurrentDateTime(getContext()));
            //tag.setText(note_elm.getContent());



        }
        return convertView;
    }
}
*/

public class Notes_Adapter extends ArrayAdapter<Notes_main> {
    DatabaseHelper dbhelper = new DatabaseHelper(getContext());
    public Notes_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Notes_main> notes_item) {
        super(context, resource, notes_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_item,null);
        }

        Notes_main note_elm = getItem(position);

        if (note_elm != null)
        {
            TextView title = (TextView) convertView.findViewById(R.id.item_title);
            TextView datetime = (TextView) convertView.findViewById(R.id.item_datetime);
            TextView tag = (TextView) convertView.findViewById(R.id.item_tag);

            title.setText(note_elm.getDesc());
            datetime.setText(dbhelper.getDateTime(note_elm.getDate_crtd()));
            tag.setText(note_elm.getTag());



        }
        return convertView;
    }
}