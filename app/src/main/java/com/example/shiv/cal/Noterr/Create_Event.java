package com.example.shiv.cal.Noterr;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.shiv.cal.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;

public class Create_Event extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
int yy,mm,dd,HH,MM;
int yyf,mmf,ddf,HHf,MMf;
    TextView Datetimeview,PlaceName, PlaceAddress;
    Button SetDate,SetLocation,Save, Cancel;
    private static final int MY_PERMISSION_FINE_LOCATION =101;
    private static final int PLACE_PICKER_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);

        requestpermissions();

        SetDate =(Button)findViewById(R.id.button2);
        Datetimeview = (TextView)findViewById(R.id.textView5);
        PlaceName = (TextView)findViewById(R.id.textView6);
        PlaceAddress = (TextView)findViewById(R.id.textView7);
        SetLocation =(Button)findViewById(R.id.button3);
        Save =(Button)findViewById(R.id.button4);
        Cancel =(Button)findViewById(R.id.button5);


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
                Toast.makeText(getApplicationContext(),"Save State",Toast.LENGTH_SHORT).show();
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
        Datetimeview.setText(ddf+"/"+mmf+"/"+yyf+" "+HHf+":"+MMf);
    }


    private void requestpermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.HONEYCOMB){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case MY_PERMISSION_FINE_LOCATION:
                if(grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"this application requires location permission",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode  == PLACE_PICKER_REQUEST){
            if(resultCode ==RESULT_OK){
                Place place  = PlacePicker.getPlace(Create_Event.this, data);
                PlaceName.setText(place.getName());
                PlaceAddress.setText((CharSequence) place.getAddress());

            }
        }
    }
}
