package com.example.jack.http_01;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegistActivity extends Activity {

    private TextView textView_name;
    private TextView textView_age;
    private EditText editText_name;
    private EditText editText_age;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);

        initView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url ="http://10.239.93.159:8080/web/MyServlet";
                String name = editText_name.getText().toString();
                String age = editText_age.getText().toString();
                //new RegistThread(url,name,age).start();
                String url2 = url+"?name="+name+"&age="+age;
                //new HttpClientThread(url2).start();
                new HttpClientThread(url,name,age).start();

            }
        });


    }

    private void initView() {
        textView_name = (TextView) findViewById(R.id.textView);
        textView_age = (TextView) findViewById(R.id.textView2);
        editText_name = (EditText) findViewById(R.id.editText);
        editText_age = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
    }
}
