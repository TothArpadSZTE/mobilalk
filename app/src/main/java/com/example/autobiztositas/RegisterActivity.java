package com.example.autobiztositas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 37;

    EditText userNameET;
    EditText userEmailET;
    EditText userPasswordET;
    EditText userPasswordAgainET;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.kek));

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);
        if(secret_key != 37){
            finish();
        }

        userNameET = findViewById(R.id.NameRegiEditText);
        userEmailET = findViewById(R.id.EmailEditText);
        userPasswordET = findViewById(R.id.PasswordEditText);
        userPasswordAgainET = findViewById(R.id.PasswordAgainEditText);


        fAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG,"onCreate");
    }

    public void reg(View view) {
        String userName = userNameET.getText().toString();
        String userEmail = userEmailET.getText().toString();
        String userPassword = userPasswordET.getText().toString();
        String userPasswordAgain = userPasswordAgainET.getText().toString();

        if (!userPassword.equals(userPasswordAgain)){
            Log.e(LOG_TAG, "A jelszó nem egyezik.");
            return;
        }


        fAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                startMainPage();
            }
        });
    }

    private void startMainPage(){
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }
    public void cancel(View view) {
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG,"onRestart");
    }


}