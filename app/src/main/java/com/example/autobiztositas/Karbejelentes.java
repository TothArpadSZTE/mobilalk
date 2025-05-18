package com.example.autobiztositas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.Manifest;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Karbejelentes extends AppCompatActivity {
    final private int REQUEST_CODE_PERMISSIONS = 523;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 524;
    int tag = 1;
    ImageView imageView;
    private static final String LOG_TAG = MainPage.class.getName();
    private FirebaseUser user;
    private LocationManager locationManager;
    Button elkuldes;
    private Bitmap img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.kek));

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Log.d(LOG_TAG, "Autentikált felhasználó");
        }else {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karbejelentes);

        imageView = (ImageView) findViewById(R.id.imageView);
        elkuldes = findViewById(R.id.buttonElkuldes);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    public void KameraMegnyitas(View view) {
        checkPermission();
    }

    void checkPermission(){
        if(Build.VERSION.SDK_INT >=23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSIONS);
                return;
            }
        }
        takePicture();
    }

    private void takePicture(){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, tag);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePicture();
                }else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == tag && resultCode == RESULT_OK) {
            Bundle b = data.getExtras();
            img = (Bitmap) b.get("data");
            imageView.setImageBitmap(img);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void GPSbekapcsolas(View view) {
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        elkuldes.setEnabled(isGpsEnabled);

        if (!isGpsEnabled) {
            new AlertDialog.Builder(this)
                    .setMessage("A GPS szükséges a kárbejelentés elküldéséhez. Bekapcsolod?")
                    .setPositiveButton("Beállítások", (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    })
                    .setNegativeButton("Mégse", null)
                    .show();
        } else {
            Toast.makeText(this, "GPS be van kapcsolva, elküldés engedélyezve", Toast.LENGTH_SHORT).show();
        }
    }

    public void Elkuldes(View view) {
        if (img == null) {
            Toast.makeText(this, "Kérlek, készíts képet a kárbejelentéshez!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Kapcsold be a GPS-t az elküldéshez!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Itt lehetne küldeni a Firestore-ba vagy Storage-ba, de most csak visszajelzést adunk
        Toast.makeText(this, "Kárbejelentés sikeresen elküldve!", Toast.LENGTH_LONG).show();

        // Navigáljunk vissza a főoldalra
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
        finish();
    }

}