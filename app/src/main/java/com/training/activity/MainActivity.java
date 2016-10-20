package com.training.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.training.R;
import com.training.sevice.MyService;

public class MainActivity extends AppCompatActivity {
    MyService myService;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, MyService.class);
        startService(intent);
//        myService = new MyService();
//        myService.onBind(new Intent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
//        myService.onUnbind(null);
    }
}
