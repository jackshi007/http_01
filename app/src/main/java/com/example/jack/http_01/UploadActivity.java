package com.example.jack.http_01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;


public class UploadActivity extends Activity {

    private Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.239.93.159:8080/web/Upload";
                File file = Environment.getExternalStorageDirectory();
                File fileAbs = new File(file, "timg.jpg");
                String fileName = fileAbs.getAbsolutePath();
                UploadThread thread = new UploadThread(url, fileName);
                thread.start();
            }
        });
    }
}
