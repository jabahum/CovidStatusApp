package com.lyecdevelopers.covidstatus.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.lyecdevelopers.covidstatus.ui.activities.DashBoardActivity;
import com.lyecdevelopers.covidstatusapp.R;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    TextView pageTitle,pageSubTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pageTitle = findViewById(R.id.title);
        pageSubTitle = findViewById(R.id.subTitle);

        handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent=new Intent(MainActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        },2000);

        //FontUtils.getFontUtils(MainActivity.this).setTextViewBoldFont(pageTitle);
        //FontUtils.getFontUtils(MainActivity.this).setTextViewLightFont(pageSubTitle);
    }



}
