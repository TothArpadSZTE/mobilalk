package com.example.autobiztositas;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class RandomAsyncLoader extends AsyncTaskLoader<String> {

    public RandomAsyncLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {

        return "";
    }
}
