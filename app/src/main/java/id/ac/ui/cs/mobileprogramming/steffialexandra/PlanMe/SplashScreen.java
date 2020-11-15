package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final int welcomeScreenDisplay = 3000; // 3000 = 3 detik
        Thread welcomeThread = new Thread() {

            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);

                } finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        };

        welcomeThread.start();

    }
}