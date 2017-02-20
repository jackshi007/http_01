package com.example.jack.http_01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;


public class ImageActivity extends Activity {
    private WebView webView;
    private ImageView imageView;
    private static final String url = "https://www.baidu.com";
    private static final String url_img = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1485172211551&di=84b2722c5b12c553bb38ec2de746afd3&imgtype=0&src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2F1208%2F1445%2Fntk-1445-9697.jpg";
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);
        webView = (WebView) findViewById(R.id.webView);
        imageView = (ImageView) findViewById(R.id.imageView);
        //new HttpThread(webView,url,handler).start();
        new HttpThread(imageView,url_img,handler).start();
    }
}
