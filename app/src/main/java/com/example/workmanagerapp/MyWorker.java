package com.example.workmanagerapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //Getting Data from InputData
        Data data=getInputData();
        int countinglimit=data.getInt("max_limit",0);

        for(int i=0;i<countinglimit;i++)
        {
            Log.i("TAGY","Count is "+i);
        }


        //Sending data and Done Notification
        Data dataToSend = new Data.Builder()
                .putString("msg","Task Done Successfully").build();


        return Result.success(dataToSend);
    }
}
