package com.example.autobiztositas;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 37;

    EditText userEmail;
    EditText passwordEt;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.kek));

        userEmail = findViewById(R.id.EmailEditText);
        passwordEt = findViewById(R.id.PwEditTextPassword);

        fAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }

    public void login(View view){

        String userMail = userEmail.getText().toString();
        String password = passwordEt.getText().toString();

        fAuth.signInWithEmailAndPassword(userMail, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                startMainPage();
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    private void startMainPage(){
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
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

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
        //return new RandomAsyncLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}