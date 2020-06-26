package com.example.covidstatusapp.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.covidstatusapp.dashboard.DashBoardActivity;
import com.example.covidstatusapp.R;
import com.example.covidstatusapp.utils.FontUtils;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    TextView pageTitle,pageSubTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pageTitle = findViewById(R.id.title);
        pageSubTitle = findViewById(R.id.subTitle);

        handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent=new Intent(MainActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        },3000);

        FontUtils.getFontUtils(MainActivity.this).setTextViewBoldFont(pageTitle);
        FontUtils.getFontUtils(MainActivity.this).setTextViewRegularFont(pageSubTitle);
    }



}
