package com.example.autobiztositas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainPage extends AppCompatActivity {
    private static final String LOG_TAG = MainPage.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private NotificationHandler mNotificationHandler;
    private AlarmManager mAlarmManager;
    private JobScheduler mJobScheduler;

    TextView nincsBiztositas;
    RecyclerView biztositasok;
    Button kalkulatorButton;
    Button KarbejelentesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.kek));

        nincsBiztositas = findViewById(R.id.NincsBiztositasTextView);
        biztositasok = findViewById(R.id.BiztositasRecyclerView);
        kalkulatorButton = findViewById(R.id.KalkulatorButton);
        KarbejelentesButton = findViewById(R.id.KarbejelentesButton);

        //TODO: eltüntetni a szöveget hamár van biztositas

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Log.d(LOG_TAG, "Autentikált felhasználó");
        }else {
            finish();
        }

        kalkulatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, Kalkulator.class);
                startActivity(intent);
            }
        });

       KarbejelentesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, Karbejelentes.class);
                startActivity(intent);
            }
        });

        mNotificationHandler = new NotificationHandler(this);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mJobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        setJobScheduler();
        setAlarmManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG,"onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.kalkulator) {
            Log.d(LOG_TAG, "asd");
            Intent intent = new Intent(MainPage.this, Kalkulator.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.logout) {
            Log.d(LOG_TAG, "asd");
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAlarmManager(){
        try {
            long repeatInterval = 10*1000;
            long triggerTime = SystemClock.elapsedRealtime() + 5000;
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            mAlarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime,
                    repeatInterval,
                    pendingIntent);
        }catch (Exception e){
            Log.e(LOG_TAG, "AlarmManager hiba:", e);
        }
    }

    private void setJobScheduler(){
        int networkType = JobInfo.NETWORK_TYPE_UNMETERED;
        int hardDeadLine = 5000;

        ComponentName name = new ComponentName(this, NotificationJobService.class);

        JobInfo.Builder builder = new JobInfo.Builder(0, name)
                .setRequiredNetworkType(networkType)
                .setRequiresCharging(true)
                .setOverrideDeadline(hardDeadLine);

        if (mJobScheduler != null) {
            mJobScheduler.schedule(builder.build());
        }
        //mJobScheduler.cancel(0);
    }

}