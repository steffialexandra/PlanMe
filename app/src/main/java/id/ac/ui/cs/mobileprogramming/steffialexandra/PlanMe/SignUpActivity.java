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

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.broadcastreceiver.BatteryBroadcastReceiver;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.DaoAccess;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.UserModel;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;

    private Button btCancel;
    private Button btRegister;

    private DaoAccess daoAccess;

    private ProgressDialog progressDialog;

    BatteryBroadcastReceiver batteryBroadcastReceiver = new BatteryBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering User...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        edtUsername = findViewById(R.id.unameinput);
        edtPassword = findViewById(R.id.passwordinput);

        btCancel = findViewById(R.id.btCancel);
        btRegister = findViewById(R.id.btRegister);

        daoAccess = PlanMeDatabase.getInstance(this).daoAccess();

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {

                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UserModel check = daoAccess.getUserByUsername(edtUsername.getText().toString());
                            if(check==null){
                                UserModel user = new UserModel(edtUsername.getText().toString(), edtPassword.getText().toString());
                                daoAccess.addUser(user);
                                progressDialog.dismiss();
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(SignUpActivity.this, R.string.userexist, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 1000);

                } else {
                    Toast.makeText(SignUpActivity.this, R.string.fillfields, Toast.LENGTH_SHORT).show();
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

        this.unregisterReceiver(batteryBroadcastReceiver);
        super.onPause();
    }


    private boolean isEmpty() {
        if (TextUtils.isEmpty(edtPassword.getText().toString()) ||
                TextUtils.isEmpty(edtUsername.getText().toString())
        ) {
            return true;
        } else {
            return false;
        }
    }
}