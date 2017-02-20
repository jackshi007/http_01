package com.example.jack.http_01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;


public class XMLActivity extends Activity {
    private TextView textView;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myxml);
        textView = (TextView) findViewById(R.id.textView);
        String url = "http://10.239.93.159:8080/web/girls.xml";
        XMLThread xmlThread = new XMLThread(url,handler,textView);
        xmlThread.start();
    }
}
