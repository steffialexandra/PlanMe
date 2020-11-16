package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.broadcastreceiver.BatteryBroadcastReceiver;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.DaoAccess;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.UserModel;


public class MainActivity extends AppCompatActivity {

    private Button btSignIn;
    private Button btSignUp;
    private EditText edtUname;
    private EditText edtPassword;
    private PlanMeDatabase database;

    private DaoAccess daoAccess;
    private ProgressDialog progressDialog;
    private BatteryBroadcastReceiver batteryBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryBroadcastReceiver = new BatteryBroadcastReceiver();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Checking User...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        database = PlanMeDatabase.getInstance(this);
        daoAccess = database.daoAccess();

        btSignIn = findViewById(R.id.btSignIn);
        btSignUp = findViewById(R.id.btSignUp);

        edtUname = findViewById(R.id.unameinput);
        edtPassword = findViewById(R.id.passwordinput);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UserModel user = daoAccess.getUser(edtUname.getText().toString(), edtPassword.getText().toString());
                            if(user!=null){
                                Intent i = new Intent(MainActivity.this, TaskActivity.class);
                                i.putExtra("User", user);
                                startActivity(i);
                                finish();
                            }else{
                                if(daoAccess.getUserByUsername(edtUname.getText().toString()) != null){
                                    Toast.makeText(MainActivity.this, R.string.wrongpass, Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(MainActivity.this, R.string.nouser, Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    }, 1000);

                }else{
                    Toast.makeText(MainActivity.this, R.string.fillfields, Toast.LENGTH_SHORT).show();
                }
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

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtUname.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }
}
