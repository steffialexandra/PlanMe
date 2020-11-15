package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.Provider;
import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.broadcastreceiver.AlarmBroadcastReceiver;

public class NotificationService extends Service {

    public void onStart(Intent intent, String date, int taskid){
        //alarm manager
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(0,2)));
        calendar.set(Calendar.MONTH, Integer.valueOf(date.substring(3,5)));
        calendar.set(Calendar.YEAR, Integer.valueOf(date.substring(6,date.length())));
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        Intent newIntent = new Intent(this, AlarmBroadcastReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(this,Integer.valueOf(taskid), newIntent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
