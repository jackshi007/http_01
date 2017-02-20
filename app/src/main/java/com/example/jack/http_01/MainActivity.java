package com.example.jack.http_01;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button image_button;
    private Button regist_button;
    private Button json_button;
    private Button xml_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_button = (Button) findViewById(R.id.image_button);
        regist_button = (Button) findViewById(R.id.regist_button);
        json_button = (Button) findViewById(R.id.json_button);
        xml_button = (Button) findViewById(R.id.xml_button);

        image_button.setOnClickListener(this);
        regist_button.setOnClickListener(this);
        json_button.setOnClickListener(this);
        xml_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.image_button:
                Intent image_intent = new Intent(MainActivity.this,ImageActivity.class);
                startActivity(image_intent);
                break;
            case R.id.regist_button:
                Intent regist_intent = new Intent(MainActivity.this,RegistActivity.class);
                startActivity(regist_intent);
                break;
            case R.id.json_button:
                Intent json_intent = new Intent(MainActivity.this,JsonActivity.class);
                startActivity(json_intent);
                break;
            case R.id.xml_button:
                Intent xml_intent = new Intent(MainActivity.this,XMLActivity.class);
                startActivity(xml_intent);
                break;
        }
    }
}
