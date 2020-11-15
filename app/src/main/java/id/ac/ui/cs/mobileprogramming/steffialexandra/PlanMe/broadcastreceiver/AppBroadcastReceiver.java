package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.R;

public class AppBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "PlanMe")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Reminder")
                .setContentText("You have task(s) due today!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
        // context.startActivity(); --> nanti buat gabisa dipake pas battery low misalnya
//        Toast.makeText(context, "Your battery is low, please charge your device!", Toast.LENGTH_LONG).show();
    }
}

