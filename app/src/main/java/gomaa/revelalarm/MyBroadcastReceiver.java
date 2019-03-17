package gomaa.revelalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class MyBroadcastReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";

    public static String NOTIFICATION = "notification";


    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
/*
        Notification noti = new Notification.Builder(context)
                .setContentTitle("Alarm in on")
                .setContentText("You  set up that alarm")
                .setSmallIcon(R.mipmap.ic_launcher).build();*/
        NotificationManager  manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification  = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        manager.notify(id, notification );

        /*notification.flags= Notification.FLAG_AUTO_CANCEL;
        manager.notify(0,notification );*/


        Uri notificationn = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r =  RingtoneManager.getRingtone(context,notificationn);
        r.play();




    }

}
