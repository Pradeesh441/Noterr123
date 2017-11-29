package com.example.shiv.cal.Noterr;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Create_Event extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
int yy,mm,dd,HH,MM;
int yyf,mmf,ddf,HHf,MMf;
int SSf =00;
    TextView Datetimeview,PlaceName, PlaceAddress;
    Button SetDate,SetLocation,Save, Cancel;
    EditText eventName;
    DatabaseHelper dbh;
    private static final int PLACE_PICKER_REQUEST =1;
    public Date dt;

    String datefinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);


        eventName= (EditText)findViewById(R.id.editText);
        SetDate =(Button)findViewById(R.id.button2);
        Datetimeview = (TextView)findViewById(R.id.textView5);
        //PlaceName = (TextView)findViewById(R.id.textView6);
        PlaceAddress = (TextView)findViewById(R.id.textView7);
        SetLocation =(Button)findViewById(R.id.button3);
        Save =(Button)findViewById(R.id.button4);
        Cancel =(Button)findViewById(R.id.button5);
        dbh = new DatabaseHelper(this);

        Calendar c = Calendar.getInstance();
        yy = c.get(Calendar.YEAR);
        mm = c.get(Calendar.MONTH);
        dd = c.get(Calendar.DAY_OF_MONTH);


        SetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dpd = new DatePickerDialog(Create_Event.this,Create_Event.this,yy,mm,dd);
                dpd.show();

            }

        });

        SetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(Create_Event.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id;
                Toast.makeText(getApplicationContext(),"Save State",Toast.LENGTH_SHORT).show();
                Cal_sched firstRecord = new Cal_sched();
                firstRecord.setName(eventName.getText().toString());
                try {
                    firstRecord.setDate_time(dbh.getDateTime(datefinal));
                    dt = dbh.getDateTime(datefinal);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                firstRecord.setVenue(PlaceAddress.getText().toString());
                id = dbh.createCal_shed(firstRecord);
                if (id != 0)
                {
                    Integer event_id = Integer.valueOf((int) id);
                    Long currenttime = System.currentTimeMillis();
                    Long time_diff = dt.getTime() - currenttime;
                    Integer eventTime = Integer.valueOf(String.valueOf(time_diff));

                    /*if(event_id >= 0)
                        scheduleEventNotification(obtainNotification(eventName.getText().toString()),eventTime,event_id);*/
                    scheduleEventNotification(obtainNotification(eventName.getText().toString()),eventTime,event_id);



                }
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Create_Event.this,Calendar_view.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yyf = year;
        mmf = month;
        ddf = dayOfMonth;


        Calendar c = Calendar.getInstance();
        HH= c.get(Calendar.HOUR_OF_DAY);
        MM = c.get(Calendar.MINUTE);
            TimePickerDialog tpd = new TimePickerDialog(Create_Event.this,Create_Event.this,HH,MM, DateFormat.is24HourFormat(this));
            tpd.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        HHf= hourOfDay;
        MMf = minute;

        Datetimeview.setText(yyf+"-"+mmf+"-"+ddf+" "+HHf+":"+MMf+":"+SSf);

        mmf++;
        datefinal =yyf+"-"+mmf+"-"+ddf+" "+HHf+":"+MMf+":"+SSf;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode  == PLACE_PICKER_REQUEST){
            if(resultCode ==RESULT_OK){
                Place place  = PlacePicker.getPlace(Create_Event.this, data);
                //PlaceName.setText(place.getName());
                PlaceAddress.setText((CharSequence) place.getAddress());

            }
        }
    }
    public void scheduleEventNotification(Notification notification,int delay,int event_id)
    {
        Intent notification_intent = new Intent(this,EventschedulePublisher.class);
        notification_intent.putExtra(EventschedulePublisher.ID,event_id);
        notification_intent.putExtra(EventschedulePublisher.NOTIFICATION,notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,event_id,notification_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long notif_time = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,notif_time,pendingIntent);
    }
    private Notification obtainNotification(String content)
    {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Noterr Upcoming Event Notification!");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.cast_ic_notification_small_icon);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        return builder.build();
    }
}
