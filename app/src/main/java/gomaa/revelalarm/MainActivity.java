package gomaa.revelalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {


    private NotificationManagerCompat notificationManager;


    EditText editText;
    DatePicker datePicker;
    TimePicker timePicker;
    TextView textView;
    int mHour, mMin, mYear, mMonth, mDay;
    Button setButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);


         datePicker = findViewById(R.id.date_picker);
        timePicker = findViewById(R.id.time_picker);
        textView = findViewById(R.id.timeTextView);

        setButton = findViewById(R.id.Button);
        editText = findViewById(R.id.titleEditText);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                mYear=year;
                mMonth=monthOfYear;
                mDay=dayOfMonth;
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMin = minute;
                //textView.setText(textView.getText().toString() + " " + mHour + " " + mMin);

            }
        });
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer(v);

            }
        });


    }

   /* public void sendOnChannel1(View v) {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }*/


    private void setTimer(View v) {

        String title = editText.getText().toString();
        textView.setText(title);
        editText.setText("");

        //3600000
        ReminderNotification(getNotification(title));
        scheduleNotification(getNotification(title));


    }

    private void ReminderNotification(Notification notification) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_alarm.setTime(date);
        cal_now.setTime(date);

        cal_alarm.set(Calendar.HOUR_OF_DAY, mHour);
        cal_alarm.set(Calendar.MINUTE, mMin);
        cal_alarm.set(Calendar.SECOND, 0);
         cal_alarm.set(Calendar.YEAR,mYear);
         cal_alarm.set(Calendar.MONTH,mMonth);
         cal_alarm.set(Calendar.DAY_OF_MONTH,mDay);

        if (cal_alarm.before(cal_now)) {


            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(MainActivity.this, MyBroadcastReceiver.class);
        i.putExtra(MyBroadcastReceiver.NOTIFICATION_ID, 1);
        i.putExtra(MyBroadcastReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 24444, i, PendingIntent.FLAG_UPDATE_CURRENT);

        // long futureInMillis = SystemClock.elapsedRealtime() + delay;

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
    }



    //// Alarm to getting notification only befor one hour of the main alarm
    private void scheduleNotification(Notification notification) {

        Intent notificationIntent2 = new Intent(MainActivity.this, NotificationPublisher.class);
        notificationIntent2.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent2.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(MainActivity.this, 0, notificationIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
        Date date = new Date();

        Calendar calnoti = Calendar.getInstance();
        calnoti.setTime(date);

        calnoti.set(Calendar.HOUR_OF_DAY, mHour-1);
        calnoti.set(Calendar.MINUTE, mMin);
        calnoti.set(Calendar.SECOND, 0);
        calnoti.set(Calendar.YEAR,mYear);
        calnoti.set(Calendar.MONTH,mMonth);
        calnoti.set(Calendar.DAY_OF_MONTH,mDay);
        //long futureInMillis2 = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager2.set(AlarmManager.RTC_WAKEUP, calnoti.getTimeInMillis(), pendingIntent2);
    }


    private Notification getNotification(String content) {

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Revel Alarm");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_background);

        return builder.build();

    }
   /* private Notification getNotification2(String content, int delay) {

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Revel Alarm");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_background);

        return builder.build();

    }*/


}
