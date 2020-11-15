package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.broadcastreceiver.AlarmBroadcastReceiver;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;

public class NewTask extends AppCompatActivity {

        EditText newtitle, newdesc;
        DatePicker newdate;
        CheckBox newpriority;
        LinearLayoutManager linearLayoutManager;
        TextView tasktitle, desc, taskdate, priority;
        Button saveButton, cancelButton;
        TaskDatabase database;
        RecyclerView tasks;
        TaskAdapter adapter;
        ArrayList<TaskModel> taskList;
        Integer number = new Random().nextInt();
        String taskid = Integer.toString(number);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.new_task_layout);
            //create notification
            createNotification();

            tasktitle = findViewById(R.id.tasktitle);
            desc = findViewById(R.id.desc);
            taskdate = findViewById(R.id.taskdate);
            priority = findViewById(R.id.priority);
            newtitle = findViewById(R.id.newtitle);
            newdesc = findViewById(R.id.newdesc);
            newdate = findViewById(R.id.newdate);
            newdate.setMinDate(System.currentTimeMillis() - 1000);
            newpriority = findViewById(R.id.newpriority);
            saveButton = (Button) findViewById(R.id.saveButton);
            database = TaskDatabase.getInstance(this);
            cancelButton = (Button) findViewById(R.id.cancelButton);
            taskList = new ArrayList<TaskModel>();
            String date = newdate.toString();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( TextUtils.isEmpty(newtitle.getText()) || TextUtils.isEmpty(newdesc.getText())){
                        Toast.makeText(getApplicationContext(),"Please fill all of the fields!",Toast.LENGTH_SHORT).show(); }
                    else{
                        //creating plan
                        TaskModel newTask = new TaskModel();
                        newTask.setDesc(newdesc.getText().toString());
                        newTask.setTitle(newtitle.getText().toString());
                        String date = getDateFromDatePicker(newdate);
                        newTask.setTaskdate(date);
                        newTask.setPriority(newpriority.isChecked());
                        database.daoAccess().insertTask(newTask);
                        adapter = new TaskAdapter(NewTask.this, taskList);
                        taskList.clear();
                        taskList.addAll(database.daoAccess().getAll());
                        adapter.notifyDataSetChanged();
                        startActivity(new Intent(NewTask.this, MainActivity.class));
                        Toast.makeText(getApplicationContext(),"Plan Created!",Toast.LENGTH_SHORT).show();
                        //alarm manager
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(0,2)));
                        calendar.set(Calendar.MONTH, Integer.valueOf(date.substring(3,5)));
                        calendar.set(Calendar.YEAR, Integer.valueOf(date.substring(6,date.length()-1)));
                        calendar.set(Calendar.HOUR_OF_DAY, 12);
                        calendar.set(Calendar.MINUTE, 00);
                        Intent intent = new Intent(NewTask.this, AlarmBroadcastReceiver.class);
                        PendingIntent pending = PendingIntent.getBroadcast(NewTask.this,Integer.valueOf(taskid), intent,0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);

                        //using calendar provider
                        Date startTime= null;
                        try {
                            startTime = new SimpleDateFormat("dd-MM-yyyy").parse(date);
                            Intent calendar_intent = new Intent(Intent.ACTION_INSERT);
                            calendar_intent.setData(CalendarContract.Events.CONTENT_URI);
                            calendar_intent.setType("vnd.android.cursor.item/event")
                                    .putExtra(CalendarContract.Events.TITLE, newtitle.getText().toString())
                                    .putExtra(CalendarContract.Events.DESCRIPTION, newdesc.getText().toString())
                                    .putExtra(CalendarContract.Events.ALL_DAY, true)
                                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.getTime());
                            if(intent.resolveActivity(getPackageManager()) != null){
                                startActivity(intent);
                            }else{
                                Toast.makeText(NewTask.this, R.string.errormsg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (ParseException e) {
                            Toast.makeText(NewTask.this, R.string.errormsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(NewTask.this, MainActivity.class));
                }
            });

    }

    public static String getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Format f = new SimpleDateFormat("dd-MM-yyyy");
        return f.format(calendar.getTime());
    }

    private void createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Plan Me";
            String description = "Reminder for PlanMe";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("PlanMe", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
