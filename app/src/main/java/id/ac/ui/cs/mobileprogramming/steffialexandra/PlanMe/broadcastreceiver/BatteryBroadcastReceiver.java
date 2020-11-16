package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BatteryBroadcastReceiver extends BroadcastReceiver {


    public BatteryBroadcastReceiver(){
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if(intentAction != null){
            if(intentAction.equalsIgnoreCase(Intent.ACTION_BATTERY_LOW)){
                Toast.makeText(context, "Your battery is low, please charge your device!", Toast.LENGTH_LONG).show();
            }
            Log.v("a", "aaaaaaaaaaa");
        }
    }
}
