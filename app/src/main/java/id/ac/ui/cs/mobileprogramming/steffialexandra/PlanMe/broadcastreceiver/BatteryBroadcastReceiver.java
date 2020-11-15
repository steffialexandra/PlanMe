package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Your battery is low, please charge your device!", Toast.LENGTH_LONG).show();
    }
}
