package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.R;

public class CalendarService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onStart(Intent intent, String date, String newtitle, String newdesc){
        Date startTime= null;
        try {
            startTime = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            Intent calendar_intent = new Intent(Intent.ACTION_INSERT);
            calendar_intent.setData(CalendarContract.Events.CONTENT_URI);
            calendar_intent.setType("vnd.android.cursor.item/event")
                    .putExtra(CalendarContract.Events.TITLE, newtitle)
                    .putExtra(CalendarContract.Events.DESCRIPTION, newdesc)
                    .putExtra(CalendarContract.Events.ALL_DAY, true)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.getTime());
            if(calendar_intent.resolveActivity(getPackageManager()) != null){
                startActivity(calendar_intent);
            }else{
                Toast.makeText(this, R.string.errormsg, Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            Toast.makeText(this, R.string.errormsg, Toast.LENGTH_SHORT).show();
        }
    }
}
