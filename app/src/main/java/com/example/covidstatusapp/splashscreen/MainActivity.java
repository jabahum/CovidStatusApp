package com.example.covidstatusapp.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.activities.DashBoardActivity;
import com.example.covidstatusapp.utils.FontUtils;

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
        },3000);

        FontUtils.getFontUtils(MainActivity.this).setTextViewBoldFont(pageTitle);
        FontUtils.getFontUtils(MainActivity.this).setTextViewLightFont(pageSubTitle);
    }



}
