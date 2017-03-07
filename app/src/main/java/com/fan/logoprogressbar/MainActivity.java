package com.fan.logoprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private LogoProgressBar webtoonProgressBar;
    private LogoProgressBar webtoonProgressBar1;
    private LogoProgressBar webtoonProgressBar2;
    private Button startBtn;
    private int  progress = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress  = progress +10;
            webtoonProgressBar.setProgress(progress);
            webtoonProgressBar1.setProgress(progress);
            webtoonProgressBar2.setProgress(progress);
            if (progress <100){
                handler.sendEmptyMessageDelayed(0,500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webtoonProgressBar = (LogoProgressBar)findViewById(R.id.webtoon);
        webtoonProgressBar1 = (LogoProgressBar)findViewById(R.id.webtoon1);
        webtoonProgressBar2 = (LogoProgressBar)findViewById(R.id.webtoon2);
        startBtn = (Button) findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = 0;
                webtoonProgressBar.setProgress(0);
                webtoonProgressBar1.setProgress(0);
                webtoonProgressBar2.setProgress(0);
                handler.sendEmptyMessageDelayed(0,500);
            }
        });
    }
}
