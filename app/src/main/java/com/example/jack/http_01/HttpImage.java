package com.example.jack.http_01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class HttpImage extends Thread {

    private ImageView imageView;
    private String url;
    private Handler handler;

    public HttpImage(ImageView imageView, String url, Handler handler) {
        this.imageView = imageView;
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            URL httpurl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpurl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            InputStream in = connection.getInputStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(in);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
