package com.example.jack.http_01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

public class JsonActivity extends Activity {


    private ListView listView;
    private JsonAdapter adapter;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new JsonAdapter(this);

        String url = "http://10.239.93.159:8080/web/JsonServerServlet";
        new HttpJson(url,listView,adapter,handler,this).start();
    }
}
