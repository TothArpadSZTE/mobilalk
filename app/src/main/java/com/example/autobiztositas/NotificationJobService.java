package com.example.autobiztositas;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;

public class NotificationJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        new NotificationHandler(getApplicationContext())
                .send("Ne felejts el biztosítást kötni!",2);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
