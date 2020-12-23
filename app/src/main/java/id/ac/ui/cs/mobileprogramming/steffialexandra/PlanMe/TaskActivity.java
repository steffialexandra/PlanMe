package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.UserModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.service.MusicService;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.viewmodel.TaskViewModel;

public class TaskActivity extends AppCompatActivity implements LocationListener {

    private static final int ACCESS_LOCATION_CODE = 100;
    Button btnAddNew, logoutBtn, btnAlarm, btnMusic, btnLoc;
    ArrayList<TaskModel> taskList;
    RecyclerView tasksRecycler;
    TaskAdapter adapter;
    TextView uname;
    LinearLayoutManager linearLayoutManager;
    PlanMeDatabase database;
    UserModel currentUser;
    TaskViewModel taskViewModel;
    LocationManager lm;
    double latitude, longitude;
    boolean granted = false;
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
            builder.setTitle("Your current location")
                    .setMessage("Longitude : " + longitude +"\nLatitude : " + latitude)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
    };

    public native String greetJNI(String name);

    static {
        System.loadLibrary("native-lib");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);
        PlanMeDatabase database = PlanMeDatabase.getInstance(this);
        taskViewModel = new TaskViewModel(database);
        //create notification
        createNotification();
        database = PlanMeDatabase.getInstance(this);
        currentUser = (UserModel) getIntent().getSerializableExtra("User");
        uname = findViewById(R.id.username);
        taskList = taskViewModel.getAllTasksByUsername(currentUser.getUsername());
        uname.setText(greetJNI(currentUser.getUsername()));

        /*database.daoAccess().deleteAll();
        taskList.clear();*/

        if (taskList != null) {
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

        btnMusic = findViewById(R.id.btnMusic);
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    Toast.makeText(TaskActivity.this, "Please connect to WiFi!", Toast.LENGTH_LONG).show();
                } else {
                    Intent myService = new Intent(TaskActivity.this, MusicService.class);
                    myService.setAction("com.example.action.PLAY");
                    startService(myService);
                }
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
                ArrayList<Integer> alarmDays = new ArrayList<Integer>();
                alarmDays.add(Calendar.MONDAY);
                i.putExtra(AlarmClock.EXTRA_DAYS, alarmDays);
                i.putExtra(AlarmClock.EXTRA_HOUR, 8);
                i.putExtra(AlarmClock.EXTRA_MINUTES, 00);
                i.putExtra(AlarmClock.EXTRA_MESSAGE, "Good morning, Monday is here! Let's do your tasks!");
                startActivity(i);
            }
        });

        btnLoc = findViewById(R.id.btnLoc);
        btnLoc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    private void showAlert() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TaskActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_CODE);
        }else{
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,  0, 10, locationListener);
        }
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        }
        return false;
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(TaskActivity.this,new String[]{permission},permissionRequestCode);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_LOCATION_CODE) {

            Log.v("masuk 1", String.valueOf(grantResults[0]));
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(TaskActivity.this,
                        "Access Granted",
                        Toast.LENGTH_SHORT)
                        .show();
                showAlert();
            } else {
                showExplanation("We need permission to read your location", "To get the latitude and longitude of your position, please enable the permission for Location from Settings", Manifest.permission.ACCESS_FINE_LOCATION, requestCode);
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
