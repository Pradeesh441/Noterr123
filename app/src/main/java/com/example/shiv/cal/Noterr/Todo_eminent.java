package com.example.shiv.cal.Noterr;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Todo_eminent extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Todo_main todo_main;
    Todo_content todo_content;
    long  id,id1;
    String prio="";
    private ListView lvItems;
    private ArrayAdapter<String> itemsAdapter;

    private ArrayList<Integer> itemsToInt;
    private ArrayList<Todo_main> output;
    private ArrayList<String> itemsToSorted;
    TextView Notodos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_eminent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        lvItems = (ListView) findViewById(R.id.lvItems);
        Notodos = (TextView)findViewById(R.id.NoTodos);

        itemsToInt=new ArrayList<Integer>();
        itemsToSorted=new ArrayList<String>();

        databaseHelper=new DatabaseHelper(Todo_eminent.this);
        output=databaseHelper.RetrieveTodo_main();

        todo_main=new Todo_main();
        todo_content=new Todo_content();


        for (int aa=0;aa<output.size();aa++){

            itemsToInt.add(output.get(aa).getID());
            Log.e("main",output.get(aa).getDesc());
            Log.e("main",output.get(aa).getID().toString());
        }

        for (int aa=0;aa<output.size();aa++) {
            String aaa=output.get(aa).getPriority();

            if(aaa.equals("High")){
                Log.e("check sorted",aaa+"");
                itemsToSorted.add(output.get(aa).getDesc());
            }

        }
        for (int aa=0;aa<output.size();aa++) {
            String aaa=output.get(aa).getPriority();
            if(aaa.equals("Low")){
                itemsToSorted.add(output.get(aa).getDesc());
            }
        }
        for (int aa=0;aa<itemsToSorted.size();aa++) {
            Log.e("sorted",itemsToSorted.get(aa)+"");
        }

        itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemsToSorted);

        lvItems.setAdapter(itemsAdapter);

        for(int z=0;z<itemsToInt.size();z++){
            Log.e("itemstoint",itemsToInt.get(z).toString());
        }

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(Todo_eminent.this, Todo_ContentView.class);
                Log.e("itemsClick index",i+"");
                Log.e("itemsClick",itemsToInt.get(i).toString());
                intent.putExtra("theId",itemsToInt.get(i).toString());
                startActivity(intent);
            }
        });

        onFloatingButtonClicked();

        removeOnLongPress();
    }

    private void onFloatingButtonClicked(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Todo_eminent.this);

                View myview=getLayoutInflater().inflate(R.layout.todo_displaylayout, null);

                final EditText editTextTitle=(EditText) myview.findViewById(R.id.title);
                final EditText editTextDesc=(EditText) myview.findViewById(R.id.description);

                RadioGroup rg = (RadioGroup) myview.findViewById(R.id.radio_group);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        int buttoncount = radioGroup.getChildCount();
                        for (int x = 0; x < buttoncount; x++) {
                            RadioButton btn = (RadioButton) radioGroup.getChildAt(x);
                            if (btn.getId() == i) {
                                Log.e("selected RadioButton->",btn.getText().toString());
                                prio=btn.getText().toString();
                            }
                        }
                    }});

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(editTextTitle.getText().toString().isEmpty() || editTextDesc.getText().toString().isEmpty() || prio.isEmpty())
                            Toast.makeText(Todo_eminent.this,"Please enter the ToDo Title and the Description and Priority",Toast.LENGTH_SHORT).show();
                        else
                        {

                        todo_main.setDesc(editTextTitle.getText().toString());
                        todo_main.setPriority(prio);
                        todo_content.setContent(editTextDesc.getText().toString());
                        todo_content.setCompleted(0);
                        todo_content.setSeq_no(1);
                        id = databaseHelper.createTODO(todo_main);
                        todo_main.setID((int)id);
                        todo_content.setID(todo_main.getID());
                        //Log.e("todo_main.getID",todo_main.getID()+"");
                        //Log.e("after setng id",todo_content.getID()+"");

                        id1 = databaseHelper.createTODO_content(todo_content);
                        Log.e("db","done");



                        Intent intent = new Intent(getApplicationContext(), Todo_eminent.class);
                        startActivity(intent);
                            Toast.makeText(Todo_eminent.this,"To-do entered successfully ",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("No",null);;
                builder.setView(myview);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

    }

    private void removeOnLongPress(){


        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                AlertDialog.Builder builder= new AlertDialog.Builder(Todo_eminent.this,pos);

                builder.setTitle("Delete your task");
                builder.setMessage("Are you sure");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        itemsToInt.remove(pos);

                        itemsToSorted.remove(pos);
                        Log.e("id to del",output.get(pos).getID()+"");
                        todo_main.setID(output.get(pos).getID());
                        todo_content.setID(output.get(pos).getID());
                        databaseHelper.deleteTODO(todo_main);
                        itemsAdapter.notifyDataSetChanged();


                    }
                }).setNegativeButton("No",null);

                builder.create();
                builder.show();
                return true;

            }

        });
    }

}
