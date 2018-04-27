package asus.com.example.asus.cardview;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ForAlarm extends AppCompatActivity {
    private Button startAlarmBtn;
    private TimePickerDialog timePickerDialog;
    final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_alarm);

        startAlarmBtn = (Button) findViewById(R.id.startAlarmBtn);

        startAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPickerDialog(false);
            }
        });

    }

    private void openPickerDialog(boolean is24hour) {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                ForAlarm.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24hour);
        timePickerDialog.setTitle("Alarm Ayarla");

        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {

                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet);
        }
    };

    private void setAlarm(Calendar alarmCalender) {


        Toast.makeText(getApplicationContext(), "Alarm Ayarlandı!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalender.getTimeInMillis(), pendingIntent);

    }
}