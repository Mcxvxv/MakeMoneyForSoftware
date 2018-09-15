package com.example.makemoneyforsoftware.ReleaseJobPackage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.makemoneyforsoftware.R;

import java.util.Calendar;

public class JobTimeActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 1;
    static final int TIME_DIALOG_ID = 2;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private Button start;
    private Button end;
    private Button start_time;
    private Button end_time;
    private Button currentButton;
    private Intent intent;
    private Bundle bundle;

    private View.OnClickListener mylistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.start_day:
                    currentButton=start;
                    showDialog(DATE_DIALOG_ID);
                    break;
                case R.id.start_time:
                    currentButton=start_time;
                    showDialog(TIME_DIALOG_ID);
                    break;
                case R.id.end_day:
                    currentButton=end;
                    showDialog(DATE_DIALOG_ID);
                    break;
                case R.id.end_time:
                    currentButton=end_time;
                    showDialog(TIME_DIALOG_ID);
                    break;
                case R.id.time_back:
                    finish();
                    break;
                case R.id.time_save:
                    bundle=new Bundle();
                    bundle.putString("start_day",start.getText().toString());
                    bundle.putString("end_day",end.getText().toString());
                    bundle.putString("start_time",start_time.getText().toString());
                    bundle.putString("end_time",end_time.getText().toString());
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                    break;
            }
        }
    };

    private DatePickerDialog.OnDateSetListener myDatelistener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            JobTimeActivity.this.year=year;
            JobTimeActivity.this.month=month;
            JobTimeActivity.this.day=dayOfMonth;
//            Toast.makeText(JobTimeActivity.this,year+"年"+(month+1)+"月"+dayOfMonth+"日",Toast.LENGTH_SHORT).show();
            currentButton.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
        }
    };

    private TimePickerDialog.OnTimeSetListener myTimelistener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            JobTimeActivity.this.hour=hourOfDay;
            JobTimeActivity.this.minute=minute;
            currentButton.setText(hourOfDay+":"+minute);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_time);
        intent=getIntent();
        start=(Button)findViewById(R.id.start_day);
        end=(Button)findViewById(R.id.end_day);
        start_time=(Button)findViewById(R.id.start_time);
        end_time=(Button)findViewById(R.id.end_time);
        ImageView back=(ImageView)findViewById(R.id.time_back);
        Button save=(Button)findViewById(R.id.time_save);
        final Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        hour=calendar.get(Calendar.HOUR);
        minute=calendar.get(Calendar.MINUTE);
        start.setOnClickListener(mylistener);
        end.setOnClickListener(mylistener);
        start_time.setOnClickListener(mylistener);
        end_time.setOnClickListener(mylistener);
        back.setOnClickListener(mylistener);
        save.setOnClickListener(mylistener);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog datePickerDialog=new DatePickerDialog(JobTimeActivity.this, myDatelistener, year, month, day);
                return datePickerDialog;
            case TIME_DIALOG_ID:
                TimePickerDialog timePickerDialog=new TimePickerDialog(JobTimeActivity.this,myTimelistener,hour,minute,true);
                return timePickerDialog;
        }
        return null;
    }
}
