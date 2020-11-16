package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.security.Provider;
import java.util.Calendar;

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
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(0,2)));
        calendar.set(Calendar.MONTH, Integer.valueOf(date.substring(3,5))-1);
        calendar.set(Calendar.YEAR, Integer.valueOf(date.substring(6,date.length())));
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 59);
        Intent newIntent = new Intent(this, AlarmBroadcastReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(this,Integer.valueOf(taskid), newIntent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()-calendar.getTimeInMillis(), pending);
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
