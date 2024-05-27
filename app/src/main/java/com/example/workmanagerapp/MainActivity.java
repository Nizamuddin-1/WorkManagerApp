package com.example.workmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.button);

        //Data
        Data data=new Data.Builder()
                .putInt("max_limit",500).build();



        //Constraints

        Constraints constraints=new Constraints
                .Builder()
                .setRequiresCharging(true)
                .build();



        WorkRequest w=new OneTimeWorkRequest
                .Builder(MyWorker.class)
                .setConstraints(constraints)
                .setInputData(data)
                .build();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance(
                        getApplicationContext()).enqueue(w);
            }
        });
        WorkManager.getInstance(getApplicationContext())
                .getWorkInfoByIdLiveData(w.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                          if(workInfo!=null)
                          {
                              Toast.makeText(MainActivity.this,"Status"+workInfo.getState().name(),Toast.LENGTH_LONG).show();

                              if(workInfo.getState().isFinished()){
                                  Data data1 = workInfo.getOutputData();
                                  Toast.makeText(MainActivity.this,""+data1.getString("msg"),Toast.LENGTH_LONG).show();
                              }
                          }
                    }
                });
    }
}