package com.example.autobiztositas;

import android.app.AlarmManager;
import android.app.job.JobScheduler;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Kalkulator extends AppCompatActivity {
    private WordViewModel viewModel;

    private static final String LOG_TAG = MainPage.class.getName();
    private FirebaseUser user;

    private NotificationHandler mNotificationHandler;
    private AlarmManager mAlarmManager;
    private JobScheduler mJobScheduler;

    Spinner evjaratSpinner;
    Spinner autotipusaSpinner;
    Spinner hasznalatJellegeSpinner;
    Spinner uzemanyagSpinner;
    Spinner bonusmalusSpinner;
    Button kalkulalasButton;
    TextView kalkulaltTextView;
    EditText rendszam;
    EditText datum;
    EditText futamIdo;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String[] Evjaratok = {
            "1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009",
            "2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025"
    };

    String[] AutoTiusok = {
            "ALFA ROMEO","AUDI","BMW","CHEVROLET","CITROEN","DACIA","DAEWOO","DODGE","FIAT","FORD","HONDA","HYUNDAI","KIA","LAND ROVER","MAZDA","MERCEDES-BENZ",
            "MITSUBISHI","NISSAN","OPEL","PEUGEOT","PORSCHE","RENAULT","SEAT","SKODA","SUBARU","SUZUKI","TESLA","TOYOTA","VOLKSWAGEN"
    };

    String[] HasznalatJellge = {
            "Magán","Céges"
    };

    String[] UzemanyagTipusok = {
            "Benzin","Dízel","Benzin+gáz","Gáz","Hibrid","Tisztán elektromos"
    };

    String[] BonusMalus = {
            "A0","B1","B2","B3","B4","B5","B6","B7","B8","B9","B10","M1","M2","M3","M4"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulator);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.kek));

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Log.d(LOG_TAG, "Autentikált felhasználó");
        }else {
            finish();
        }

        evjaratSpinner = findViewById(R.id.EvjaratSpinner);
        autotipusaSpinner = findViewById(R.id.AutoTipusaSpinner);
        hasznalatJellegeSpinner = findViewById(R.id.HasznalatJellegeSpinner);
        uzemanyagSpinner = findViewById(R.id.UzemanyagSpinner);
        bonusmalusSpinner = findViewById(R.id.BonusmalusSpinner);
        kalkulalasButton = findViewById(R.id.KalkulalasButton);
        kalkulaltTextView = findViewById(R.id.KalkulaltTextView);
        rendszam = findViewById(R.id.RendszamEditText);
        datum = findViewById(R.id.KezdetdatumaEditText);
        futamIdo = findViewById(R.id.FutaimidoEditText);


        ArrayAdapter<String> adapterEvjarat = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Evjaratok
        );

        ArrayAdapter<String> adapterAutotipus = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                AutoTiusok
        );

        ArrayAdapter<String> adapterHasznalatJellege= new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                HasznalatJellge
        );

        ArrayAdapter<String> adapterUzemanyag= new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                UzemanyagTipusok
        );

        ArrayAdapter<String> adapterBonusMalus= new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                BonusMalus
        );

        adapterEvjarat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        evjaratSpinner.setAdapter(adapterEvjarat);
        evjaratSpinner.setSelection(10);

        adapterAutotipus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autotipusaSpinner.setAdapter(adapterAutotipus);
        autotipusaSpinner.setSelection(0);

        adapterHasznalatJellege.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hasznalatJellegeSpinner.setAdapter(adapterHasznalatJellege);
        hasznalatJellegeSpinner.setSelection(0);

        adapterUzemanyag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uzemanyagSpinner.setAdapter(adapterUzemanyag);
        uzemanyagSpinner.setSelection(0);

        adapterBonusMalus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bonusmalusSpinner.setAdapter(adapterBonusMalus);
        bonusmalusSpinner.setSelection(0);

        kalkulalasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kalkulaltTextView.setText("70000 Ft/év");
            }
        });

        preferences = getSharedPreferences("biztositas_adatok", MODE_PRIVATE);
        editor = preferences.edit();

        mNotificationHandler = new NotificationHandler(this);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


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

    public void cancel2(View view) {
        finish();
    }

    public void accept(View view) {
        editor.putString("rendszam", rendszam.getText().toString());
        editor.putString("datum", datum.getText().toString());
        editor.putString("futamIdo", futamIdo.getText().toString());
        editor.putBoolean("uj_biztositas", true);
        editor.apply();
        finish();
    }
}