package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AppBroadcastReceiver extends BroadcastReceiver {
    public AppBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // context.startActivity(); --> nanti buat gabisa dipake pas battery low misalnya
        Toast.makeText(context, "Your battery is low, please charge your device!", Toast.LENGTH_LONG).show();
    }
}

