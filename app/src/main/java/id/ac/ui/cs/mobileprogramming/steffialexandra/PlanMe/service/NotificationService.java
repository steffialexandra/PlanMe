package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.NewTask;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.R;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.broadcastreceiver.AlarmBroadcastReceiver;

public class NotificationService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //alarm manager
        String date = (String) intent.getSerializableExtra("date");
        String taskid = (String) intent.getSerializableExtra("taskid");
        Intent notificationIntent = new Intent(this, NewTask.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 55);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        Intent newIntent = new Intent(this, AlarmBroadcastReceiver.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pending = PendingIntent.getBroadcast(this,Integer.valueOf(taskid), newIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Log.v("abc", String.valueOf(calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
        Notification notification = new NotificationCompat.Builder(this, "PlanMe")
                .setContentTitle("PlanMe")
                .setContentText("Background process, check!")
                .setSmallIcon(R.mipmap.app_logo)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .build();
        startForeground(1, notification);
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
