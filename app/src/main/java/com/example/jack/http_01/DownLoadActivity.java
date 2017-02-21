package com.example.jack.http_01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DownLoadActivity extends Activity {
    private Button button;
    private TextView textView;
    private int count = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            int result = msg.what;
            count += result;
            if (count==3){
                textView.setText("downLoad Success!");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        DownLoad downLoad = new DownLoad(handler);
                        downLoad.downLoadFile("http://10.239.93.159:8080/web/timg.jpg");
                    }
                }.start();
            }
        });

    }
}
