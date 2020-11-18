package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.UserModel;

public class TaskActivity extends AppCompatActivity {

    Button btnAddNew, logoutBtn, btnAlarm;
    ArrayList<TaskModel> taskList;
    RecyclerView tasksRecycler;
    TaskAdapter adapter;
    TextView uname;
    LinearLayoutManager linearLayoutManager;
    PlanMeDatabase database;
    UserModel currentUser;
    BatteryBroadcastReceiver batteryBroadcastReceiver = new BatteryBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);

        //create notification
        createNotification();
        database = PlanMeDatabase.getInstance(this);
        currentUser = (UserModel) getIntent().getSerializableExtra("User");
        uname = findViewById(R.id.username);
        taskList = (ArrayList<TaskModel>) database.daoAccess().loadAllByUsername(currentUser.getUsername());
        uname.setText("Welcome, " + currentUser.getUsername());
        /*database.daoAccess().deleteAll();
        taskList.clear();*/

        if(taskList != null){
            tasksRecycler = findViewById(R.id.tasklist);
            linearLayoutManager = new LinearLayoutManager(this);
            tasksRecycler.setLayoutManager(linearLayoutManager);
            adapter = new TaskAdapter(TaskActivity.this, taskList, currentUser);
            tasksRecycler.setAdapter(adapter);
        }
        btnAddNew = findViewById(R.id.btnAddNew);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaskActivity.this, NewTask.class);
                i.putExtra("User", currentUser);
                startActivity(i);
            }
        });

        logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaskActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                TaskActivity.this.finish();
            }
        });

        btnAlarm = findViewById(R.id.btAlarm);
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memakai AlarmClock provider
                Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                ArrayList<Integer> alarmDays= new ArrayList<Integer>();
                alarmDays.add(Calendar.MONDAY);
                i.putExtra(AlarmClock.EXTRA_DAYS, alarmDays);
                i.putExtra(AlarmClock.EXTRA_HOUR,8);
                i.putExtra(AlarmClock.EXTRA_MINUTES, 00);
                i.putExtra(AlarmClock.EXTRA_MESSAGE, "Good morning, Monday is here! Let's do your tasks!");
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(batteryBroadcastReceiver, filter);
    }

    @Override
    protected void onPause() {

        // Unregister reciever if activity is not in front
        this.unregisterReceiver(batteryBroadcastReceiver);
        super.onPause();
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