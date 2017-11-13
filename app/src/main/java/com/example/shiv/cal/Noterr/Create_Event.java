package com.example.shiv.cal.Noterr;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.shiv.cal.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Create_Event extends AppCompatActivity {
int yy,mm,dd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);


        final Calendar c = Calendar.getInstance();
       // yy = c.get(Calendar.YEAR);
       // mm = c.get(Calendar.MONTH);
       // dd = c.get(Calendar.DAY_OF_MONTH);

        final TextView textView = (TextView)findViewById(R.id.textView2);
        Button Date =(Button)findViewById(R.id.button2);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dpd = new DatePickerDialog(Create_Event.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {

                                yy = year;
                                mm= month;
                                dd= day;

                                //c.set(year, month, day);
                                //String date = new SimpleDateFormat("dd/mm/yyyy").format(c.getTime());
                                textView.setText(dd+"/"+mm+"/"+yy);


                            }
                        }, yy, mm, dd);
               // dpd.getDatePicker().setMinDate(System.currentTimeMillis());

                //Calendar d = Calendar.getInstance();
                //d.add(Calendar.MONTH,1);

                //dpd.getDatePicker().setMaxDate(d.getTimeInMillis());
                dpd.show();


            }

        });


    }
}
