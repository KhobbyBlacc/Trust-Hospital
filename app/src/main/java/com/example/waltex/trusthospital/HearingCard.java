package com.example.waltex.trusthospital;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class HearingCard extends AppCompatActivity implements View.OnClickListener{

    private int notification = 1;
    Button setBtn, cancelBtn;
    EditText scheduler;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_card);

        setBtn = findViewById(R.id.setBtn);
        cancelBtn = findViewById(R.id.setBtn);


        setBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        timePicker = findViewById(R.id.timePicker);
        scheduler = findViewById(R.id.editTime);

        //set notification & text
        Intent intent = new Intent(HearingCard.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notification);
        intent.putExtra("todo", scheduler.getText().toString());

                //GET BROADCAST(context, requestcode, intent, flags)
        PendingIntent alarmIntent = PendingIntent.getBroadcast(HearingCard.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch (view.getId()){
            case R.id.setBtn:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                //create time
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                //set alarm
                //set(type, milliseconds, intent)
                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);

                Toast.makeText(this,"Done!",Toast.LENGTH_LONG).show();
                break;

            case R.id.cancelBtn:
                alarm.cancel(alarmIntent);
                Toast.makeText(this,"Canceled",Toast.LENGTH_LONG).show();
                break;

                default:
                    break;

        }
    }
}
